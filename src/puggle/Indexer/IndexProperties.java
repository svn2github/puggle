/*
 * IndexProperties.java
 *
 * Created on 31 March 2007, 2:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.Indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import puggle.Resources.*;

/**
 *
 * @author gvasil
 */
public class IndexProperties {

    private static IndexProperties propertiesControl;
    
    private Properties properties;
    
    private File file;
    
    /**
     * Creates a new instance of IndexProperties
     */
    public IndexProperties(File file) throws OverlappingFileLockException {
        this.properties = new Properties();

        this.file = file;
        
        try {
            properties.load(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            properties.setProperty("document_filetypes", "");
            properties.setProperty("image_filetypes", "");
            properties.setProperty("music_filetypes", "");
            properties.setProperty("misc_filetypes", "");
            properties.setProperty("path", "");
            properties.setProperty("last_indexed", "0");
            properties.setProperty("last_optimized", "0");
            properties.setProperty("store_text", "false");
            properties.setProperty("store_thumbnails", "false");
            properties.setProperty("filesystem_root", "");
            properties.setProperty("portable", "false");
            properties.setProperty("version", Resources.getApplicationVersion());
            
            try {
                (file.getParentFile()).mkdirs();
                file.createNewFile();
                properties.store(
                        new FileOutputStream(file),
                        "Automatically generated. DO NOT EDIT!!!"
                        );
            } catch (IOException e) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public File getFile() {
        return this.file;
    }
    
    protected void finalize() {
        this.flush();
    }
    
    public synchronized void close() {
        this.flush();
        this.file = null;
        this.properties = null;
    }
    
    private synchronized void flush() {
        try {
            properties.store(
                    new FileOutputStream(this.file),
                    "Automatically generated. DO NOT EDIT!!!"
                    );
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized boolean isPortable() {
        return Boolean.valueOf(this.properties.getProperty("portable"));
    }
    
    /**
     * Set whether this index is portable or not.
     */
    public synchronized void setPortable(boolean b) {
        this.properties.setProperty("portable", Boolean.toString(b));
        this.flush();
    }
    
    /**
     * Set the root of the filesystem for this index.
     */
    public synchronized void setFilesystemRoot(String root) {
        this.properties.setProperty("filesystem_root", root);
        this.flush();
    }
    
    /**
     * Get the root of the filesystem of this index.
     */
    public synchronized String getFilesystemRoot() {
        return this.properties.getProperty("filesystem_root");
    }
    
    public synchronized String getVersion() {
        return this.properties.getProperty("version");
    }
    
    public synchronized Set getFiletypesSet() {
        HashSet<String> set = new HashSet<String>();
        
        set.addAll(Arrays.asList(getImageFiletypesArray()));
        set.addAll(Arrays.asList(getDocumentFiletypesArray()));
        set.addAll(Arrays.asList(getMusicFiletypesArray()));
        set.addAll(Arrays.asList(getMiscFiletypesArray()));
        
        return set;
    }
    
    public synchronized String[] getImageFiletypesArray() {
        return this.properties.getProperty("image_filetypes").split(",");
    }
    
    public synchronized String getImageFiletypes() {
        return this.properties.getProperty("image_filetypes");
    }
    
    public synchronized void setImageFiletypes(String str[]) {
        //throw new NullPointerException("Method not implemented.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; ++i) {
            sb.append(str[i] + ",");
        }
        
        setImageFiletypes(sb.substring(0, sb.length() - 1 >= 0 ? sb.length() - 1 : 0));
        
        this.flush();
    }
    
    public synchronized void setDocumentFiletypes(String str[]) {
        //throw new NullPointerException("Method not implemented.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; ++i) {
            sb.append(str[i] + ",");
        }
        
        setDocumentFiletypes(sb.substring(0, sb.length() - 1 >= 0 ? sb.length() - 1 : 0));
        
        this.flush();
    }
    
    /**
     * Set document file types as a String object seperated by commas
     */
    public synchronized void setDocumentFiletypes(String str) {
        this.properties.setProperty("document_filetypes", str);
        this.flush();
    }
    
    /**
     * Set image file types as a String object seperated by commas
     */
    public synchronized void setImageFiletypes(String str) {
        this.properties.setProperty("image_filetypes", str);
        this.flush();
    }
    
    public synchronized void setMiscFiletypes(String str[]) {
        //throw new NullPointerException("Method not implemented.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; ++i) {
            sb.append(str[i] + ",");
        }
        
        setMiscFiletypes(sb.substring(0, sb.length() - 1 >= 0 ? sb.length() - 1 : 0));
        
        this.flush();
    }
    
    public synchronized void setMusicFiletypes(String str[]) {
        //throw new NullPointerException("Method not implemented.");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; ++i) {
            sb.append(str[i] + ",");
        }
        
        setMusicFiletypes(sb.substring(0, sb.length() - 1 >= 0 ? sb.length() - 1 : 0));
        
        this.flush();
    }
    
    /**
     * Set misc file types as a String object seperated by commas
     */
    public synchronized void setMiscFiletypes(String str) {
        this.properties.setProperty("misc_filetypes", str);
        this.flush();
    }
    
    public synchronized void setMusicFiletypes(String str) {
        this.properties.setProperty("music_filetypes", str);
        this.flush();
    }
    
    public synchronized String[] getDocumentFiletypesArray() {
        return this.properties.getProperty("document_filetypes").split(",");
    }
    
    public synchronized String getDocumentFiletypes() {
        return this.properties.getProperty("document_filetypes");
    }
    
    public synchronized String getMiscFiletypes() {
        return this.properties.getProperty("misc_filetypes");
    }

    public synchronized String[] getMiscFiletypesArray() {
        return this.properties.getProperty("misc_filetypes").split(",");
    }
    
    public synchronized String[] getMusicFiletypesArray() {
        return this.properties.getProperty("music_filetypes").split(",");
    }
    
    public synchronized String getMusicFiletypes() {
        return this.properties.getProperty("music_filetypes");
    }

    public synchronized long getLastIndexed() {
        return Long.valueOf(this.properties.getProperty("last_indexed"));
    }
    
    public synchronized Date getLastIndexedDate() {
        long milis = Long.valueOf(this.properties.getProperty("last_indexed"));
        return new Date(milis);
    }
    
    public synchronized String getLastIndexedString() {
        long milis = this.getLastIndexed();
        String lastIndexedString = "";
        if (milis == 0) {
            lastIndexedString = "never";
        }
        else {
            lastIndexedString = new Date(milis).toString();
        }
        
        return lastIndexedString;
    }

    public synchronized void setLastIndexed(long millis) {
        this.properties.setProperty("last_indexed", Long.toString(millis));
        this.flush();
    }
    
    public synchronized long getLastOptimized() {
        return Long.valueOf(this.properties.getProperty("last_optimized"));
    }
    
    public synchronized Date getLastOptimizedDate() {
        long milis = Long.valueOf(this.properties.getProperty("last_optimized"));
        return new Date(milis);
    }

    public synchronized void setLastOptimized(long millis) {
        this.properties.setProperty("last_optimized", Long.toString(millis));
        this.flush();
    }
    
    
    public synchronized String getPath() {
        return this.properties.getProperty("path");
    }
    
    public synchronized void setPath(File file) {
        this.properties.setProperty("path", file.getPath());
        this.flush();
    }
    
    public synchronized void setPath(String file) {
        this.properties.setProperty("path", file);
        this.flush();
    }
    
    /**
     * Set whether document text will be stored in index.
     */
    public synchronized void setStoreText(boolean b) {
        this.properties.setProperty("store_text", Boolean.toString(b));
        this.flush();
    }
    
    /**
     * Get whether document text will be stored in index.
     */
    public synchronized boolean getStoreText() {
        return Boolean.valueOf(this.properties.getProperty("store_text"));
    }
    
    /**
     * Set whether thumbnail for each document will be stored in index.
     */
    public synchronized void setStoreThumbnail(boolean b) {
        this.properties.setProperty("store_thumbnails", Boolean.toString(b));
        this.flush();
    }
    
    /**
     * Get whether thumbnail for each document will be stored in index.
     */
    public synchronized boolean getStoreThumbnail() {
        return Boolean.valueOf(this.properties.getProperty("store_thumbnails"));
    }
    
    public synchronized File[] getDataDirectories() {
        String path = this.properties.getProperty("path");
        
        if (path.equals("") == true) {
            return new File[0];
        }
        
        String[] dataDirs = path.split(File.pathSeparator);
        File[] dataDirsFile = new File[dataDirs.length];
        
        for (int i = 0; i < dataDirs.length; ++i) {
            dataDirsFile[i] = new File(dataDirs[i]);
        }
        
        return dataDirsFile;
    }
}
