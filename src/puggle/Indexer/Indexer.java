/*
 * Indexer.java
 *
 * Created on 2 September 2006, 1:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.Indexer;

import puggle.FileWalker.FileWalker;
import puggle.LexicalAnalyzer.FileHandler;
import puggle.LexicalAnalyzer.FileHandlerException;
import puggle.Util.RelativePath;
import puggle.Resources.Resources;
import puggle.ui.JLogger;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import javax.swing.JProgressBar;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexModifier;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import puggle.Util.Util;

/**
 *
 * @author gvasil
 */
public class Indexer implements Runnable {
    private File IndexDir;
    private File[] DataDir;

    private static final int MAX_FIELD_LENGTH = 102400; //100KB

    /** Indexing process will stop asap when its value is true */
    private volatile boolean Stop;
    /** Indexing process has been successfully stopped */
    private volatile boolean Stopped;
    
    /* Statistics */
    private long TotalBytes;
    private long TotalFiles;
    
    private JProgressBar ProgressBar;
    
    private JLogger Logger;
    
    private IndexModifier Index;
    
    private long Delay = 0; // ms
    
    private IndexProperties indexProperties;

    /** Creates a new instance of Indexer */
    public Indexer(File indexDir, IndexProperties indexProperties) throws IOException {
        this.IndexDir = indexDir;
        
        this.TotalBytes = 0;
        this.TotalFiles = 0;
        
        this.Stopped = true;

        boolean create = false;
        
        this.indexProperties = indexProperties;
        
        if (IndexReader.indexExists(this.IndexDir)) {
            if (IndexReader.isLocked(this.IndexDir.getCanonicalPath())) {
                throw new IOException("Index is locked.");
            }
        } else {
            create = true;
        }
        
        this.Index = new IndexModifier(this.IndexDir, Resources.getAnalyzer(), create);
    }
    
    public Indexer(File indexDir, IndexProperties indexProperties, boolean unlock) throws IOException {
        this.IndexDir = indexDir;
        
        this.TotalBytes = 0;
        this.TotalFiles = 0;
        
        this.Stopped = true;
        
        boolean create = false;
        
        this.indexProperties = indexProperties;

        if (IndexReader.indexExists(this.IndexDir)) {
            if (IndexReader.isLocked(this.IndexDir.getCanonicalPath())) {
                if (unlock == true)
                    IndexReader.unlock(FSDirectory.getDirectory(this.IndexDir.getCanonicalPath()));
                else
                    throw new IOException("Index is locked.");
            }
        } else {
            create = true;
        }
        
        this.Index = new IndexModifier(this.IndexDir, Resources.getAnalyzer(), create);
    }
    
    public Indexer(File[] dataDir, File indexDir, IndexProperties indexProperties, boolean unlock)
            throws IOException {
        this(indexDir, indexProperties, unlock);
        this.setDataDirectories(dataDir);
    }
    
    public Indexer(File[] dataDir, File indexDir, IndexProperties indexProperties, JLogger logger, boolean unlock)
            throws IOException {
        this(dataDir, indexDir, indexProperties, unlock);
        this.Logger = logger;
    }
    
    @Override
    protected void finalize() {
        try { this.Index.close(); }
        catch (IOException ex) { ex.printStackTrace(); }
        catch (IllegalStateException ex) { ex.printStackTrace(); }
        
        this.Index = null;
    }
    
    public synchronized void setDataDirectories(File[] dir) {
        this.DataDir = dir;
    }
    
    public synchronized void setProgressBar(JProgressBar p) {
        this.ProgressBar = p;
    }
    
    public synchronized void setLogger(JLogger logger) {
        this.Logger = logger;
    }
    
    public static boolean indexExists(String directory) {
        return IndexReader.indexExists(directory);
    }

    public static boolean indexExists(File directory) {
        return Indexer.indexExists(directory.getPath());
    }

    /**
     * Removes from this index all documents that have been deleted or modified.
     */
    private synchronized void removeModifiedDocuments() throws IOException {
        IndexReader indexReader = null;
        try {
            indexReader = IndexReader.open(this.IndexDir);
        } catch (IOException e) {
            // index has not been created yet. just return.
            return;
        }

        if (this.Logger != null) {
            this.Logger.write("Removing modified documents...");
        }

        int size = indexReader.maxDoc();

        if (this.ProgressBar != null) {
            this.ProgressBar.setMinimum(0);
            this.ProgressBar.setMaximum(size);
            this.ProgressBar.setValue(0);
        }

        for (int i = 0; i < size && !this.Stop; ++i) {
            if (this.ProgressBar != null) {
                this.ProgressBar.setValue(i);
            }

            if (indexReader.isDeleted(i)) {
                continue;
            }
            
            Document doc = indexReader.document(i);
            String path = doc.get("path");
            
            if (this.indexProperties.isPortable()) {
                path = this.indexProperties.getFilesystemRoot() + path;
            }
            
            File file = new File(path);

            boolean flag = false;

            if (!file.exists()) {
                flag = true;
            } else {
                HashSet<String> FiletypeSet =
                    Resources.getAcceptedFileExtensions(this.indexProperties);

                long l = Long.parseLong(doc.get("last modified"));
                long s = Long.parseLong(doc.get("size"));
                String ext = doc.get("filetype");

                if (FiletypeSet.contains(ext) == false) { flag = true; }
                else if (Util.isParentDirectory(this.DataDir, file) == false) { flag = true; }
                else if (l != file.lastModified() || s != file.length()) { flag = true; }
            }

            if (flag == true) {
                this.Index.deleteDocument(i);
                if (this.Logger != null) {
                    String type = (file.isDirectory()) ? "Directory" : "File";

                    this.Logger.write(type +" '" +file +"' removed. ");
                }
            }
        }

        this.indexProperties.setIndexedDocuments(indexReader.maxDoc());

        indexReader.close();
        this.Index.flush();
        
        if (this.ProgressBar != null) {
            this.ProgressBar.setValue(size);
        }
    }

    private synchronized void indexFile(File file) throws IOException {
        FileHandler handler = new FileHandler(
                this.indexProperties.getStoreText(),
                this.indexProperties.getStoreThumbnail());

        String filename = file.getCanonicalPath();

        Document doc = null;

        File root = null;
        if (this.indexProperties.isPortable() == true) {
            root = new File(this.indexProperties.getFilesystemRoot());
        }

        try {
            doc = handler.getDocument(file, root);
        } catch (FileHandlerException e) {
            if (this.Logger != null) {
                this.Logger.write(e.getMessage());
            }
        }

        if (doc != null) {
            this.TotalBytes += file.length();
            this.TotalFiles += 1;
            
            this.Index.addDocument(doc);

            //Now release everything for GC
            doc = null;
            doc = new Document();

            if (this.Logger != null) {
                String type = null;
                if (file.isDirectory()) {
                    type = "Directory";
                } else {
                    type = "File";
                }

                this.Logger.write(type +" '" +filename +"' indexed.");
            }
        }
    }

    /**
     * Index all documents that are not contained in this index.
     */
    private void indexDocuments() throws IOException {

        this.Index.setMaxFieldLength(Indexer.MAX_FIELD_LENGTH);
        
        IndexReader indexReader = IndexReader.open(this.IndexDir);
        
        if (this.ProgressBar != null) {
            this.ProgressBar.setMinimum(0);
            this.ProgressBar.setMaximum(100);
            this.ProgressBar.setValue(0);
            this.ProgressBar.setIndeterminate(true);
        }
        
        for (int i = 0; i < this.DataDir.length; ++i) {
            FileWalker files = new FileWalker(this.DataDir[i], Resources.getFiletypeFilter(this.indexProperties));

            while (files.hasNext() && !this.Stop) {
                
                File file = files.next();
                
                if (this.Delay > 0) {
                    try { Thread.sleep(Delay); }
                    catch (InterruptedException e) { e.printStackTrace(); }
                }
                
                File root = null;
                if (this.indexProperties.isPortable() == true) {
                    root = new File(this.indexProperties.getFilesystemRoot());
                }

                if (this.indexProperties.isPortable() == true) {
                    String path = RelativePath.getRelativePath(root, file);
                    if (indexReader.termDocs(new Term("path", path)).next()) {
                        continue;
                    }
                } else {
                    if (indexReader.termDocs(new Term("path", file.getCanonicalPath())).next()) {
                        continue;
                    }
                }

                this.indexFile(file);
            }
        }

        this.indexProperties.setIndexedDocuments(indexReader.maxDoc());

        indexReader.close();
        this.Index.flush();
        
        if (this.ProgressBar != null) {
            this.ProgressBar.setMinimum(0);
            this.ProgressBar.setMaximum(100);
            this.ProgressBar.setValue(0);
            this.ProgressBar.setIndeterminate(false);
        }
    }
    
    public synchronized void optimize() throws IOException {
        if (this.Logger != null) {
            this.Logger.write("Optimizing index...");
        }
        
        if (this.ProgressBar != null) {
            this.ProgressBar.setMinimum(0);
            this.ProgressBar.setMaximum(100);
            this.ProgressBar.setValue(0);
            this.ProgressBar.setIndeterminate(true);
        }
        
        this.Index.optimize();
        
        this.indexProperties.setLastOptimized(new Date().getTime());
        
        if (this.ProgressBar != null) {
            this.ProgressBar.setMinimum(0);
            this.ProgressBar.setMaximum(100);
            this.ProgressBar.setValue(0);
            this.ProgressBar.setIndeterminate(false);
        }
        
        if (this.Logger != null) {
            this.Logger.write("Optimize finished.");
        }
    }
    
    public void run() {
        try {
            this.Stop = false;
            this.Stopped = false;
            
            if (this.Logger != null) {
                this.Logger.write("Starting indexing...");
            }
            
            long start = new Date().getTime();
            
            if (!this.Stop) {
                this.removeModifiedDocuments();
            }
            if (!this.Stop) {
                this.indexDocuments();
            }
            
            if (!this.Stop) {
                this.indexProperties.setLastIndexed(new Date().getTime());
            }
            
            long end = new Date().getTime();

            long secs = (end - start) / 1000;
            long mins = secs / 60;
            secs -= mins * 60;
            
            if (this.Logger != null) {
                this.Logger.write("Indexing finished. "
                        + this.TotalFiles + " files (" +this.TotalBytes
                        + " bytes) indexed in " + mins + "m:" + secs + "s");
            }
            
            this.Stopped = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void setDelay(long milis) throws IllegalArgumentException {
        if (milis < 0) {
            throw new IllegalArgumentException("Negative value");
        }
        
        this.Delay = milis;
    }
    
    public synchronized long getDelay() {
        return this.Delay;
    }
    
    public void stop() {
        this.Stop = true;
        
        while (this.Stopped == false) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void close() {
        this.stop();
        if (this.Index != null) {
            this.finalize();
        }
    }
}
