/*
 * Util.java
 *
 * Created on 10 September 2006, 7:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.Util;

import org.apache.lucene.search.highlight.SimpleHTMLEncoder;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import puggle.LexicalAnalyzer.CombinedAnalyzer;
import puggle.LexicalAnalyzer.FileHandler;
import puggle.LexicalAnalyzer.FileHandlerException;
import puggle.Resources.Resources;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Random;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;

/**
 *
 * @author gvasil
 */
public class Util {
    
    /**
     * Deletes all files and subdirectories under dir.
     * Returns true if all deletions were successful.
     * If a deletion fails, the method stops attempting to delete and
     * returns false.
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    }

    public static File createTempDir() {
        final String baseTempPath = System.getProperty("java.io.tmpdir");

        Random rand = new Random();
        int randomInt = 1 + rand.nextInt();

        File tempDir = new File(baseTempPath + File.separator + "tempDir" + randomInt);
        while (tempDir.exists() == true) {
            randomInt++;
            tempDir = new File(baseTempPath + File.separator + "tempDir" + randomInt);
        }

        tempDir.mkdir();

        return tempDir;
    }

    public static String getFragment(Document doc, Query query) {
        File file = new File(doc.get("path"));
        String tmp;
        String fragment = "";
        
        if (file.isDirectory() == true) {
            String lastModified = DateFormat.getDateInstance().format(
                    new Time(Long.valueOf(doc.get("last modified"))));
            
            File[] files = file.listFiles();
            int filesNumber = files.length;
            int foldersNumber = 0;
            
            for (int i = 0; i < filesNumber; ++i) {
                if (files[i].isDirectory()) {
                    filesNumber--;
                    foldersNumber++;
                }
            }
            
            fragment += "<font face=\"arial,sans-serif\" size=\"4\" color=\"#96b1cb\">";
            fragment += "Contains " + filesNumber + " files, " +foldersNumber + " folders. ";
            fragment += "Last Modified: " + lastModified;
            fragment += "</font>";
        }
        else if (doc.get("filetype").equals("mp3")) {
            tmp = doc.get("artist");
            fragment += "<font face=\"arial,sans-serif\" size=\"4\" color=\"#96b1cb\">";
            if (tmp != null && tmp.trim().compareTo("") != 0) {
                fragment += "Artist: " +tmp;
                fragment += "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
            } else {
                fragment += "Artist: N/A";
                fragment += "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
            }
            
            tmp = doc.get("album");
            if (tmp != null && tmp.trim().compareTo("") != 0) {
                fragment += " Album: " +tmp +"<br />";
            } else {
                fragment += " Album: N/A<br />";
            }
            
            tmp = doc.get("year");
            if (tmp != null && tmp.trim().compareTo("") != 0) {
                fragment += "Year:</B> " +tmp;
            } else {
                fragment += "Year: N/A";
            }
            fragment += "</font>";
        } else if (Resources.isImage(doc.get("filetype"))) {
            String lastModified = DateFormat.getDateInstance().format(
                    new Time(Long.valueOf(doc.get("last modified"))));
            
            fragment += "<font face=\"arial,sans-serif\" SIZE=\"3\" COLOR=\"#96b1cb\">";
            fragment += "Last Modified: " + lastModified + "<br />";
            fragment += "Dimensions: " + doc.get("width") + " x " + doc.get("height");
            fragment += "</font>";
        } else {
            QueryScorer scorer = new QueryScorer(query);
            Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(),
                    new SimpleHTMLEncoder(), scorer);
            String text;
            try {
                text = new FileHandler(false, false).getText(doc);
                if (text != null) {
                    TokenStream stream =
                            new CombinedAnalyzer().tokenStream("content",
                            new StringReader(text));
                    fragment = highlighter.getBestFragments(stream, text, 2, "...");
                    fragment = fragment.replaceAll("[\\x15\\x07\\x0b\r\n]", " ");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (FileHandlerException ex) {
                ex.printStackTrace();
            }
        }
        
        return fragment;
    }
    
    /**
     * Converts the specified long number to a human-readable form.
     */
    public static String adjustSize(long b) {
        String str;
        long g = 0, m = 0, k = 0;
        
        if (b == 0) {
            return "";
        }
        
        if (b > 1073741824) {
            g = b / 1073741824;
            b -= g * 1073741824;
            str = g +" GB";
        } else if (b > 1048576) {
            m = b / 1048576;
            b -= m * 1048576;
            str = m +" MB";
        } else if (b > 1024) {
            k = b / 1024;
            b -= k * 1024;
            str = k +" KB";
        } else {
            str = b +" bytes";
        }
        
        return str;
    }
    
    public static String selectAllMusicFilesQuery() {
        String ext[] = Resources.getMusicFiletypesArray();
        
        String query = "(filetype:" + ext[0];
        for (int i = 1; i < ext.length; ++i) {
            query += " OR filetype:" + ext[i];
        }
        query += ")";
        
        return query;
    }
    
    public static String selectAllDocumentsQuery() {
        String ext[] = Resources.getDocumentFiletypesArray();
        
        String query = "(filetype:" + ext[0];
        for (int i = 1; i < ext.length; ++i) {
            query += " OR filetype:" + ext[i];
        }
        query += ")";
        
        return query;
    }
    
    public static String selectAllImagesQuery() {
        String ext[] = Resources.getImageFiletypesArray();
        
        String query = "(filetype:" + ext[0];
        for (int i = 1; i < ext.length; ++i) {
            query += " OR filetype:" + ext[i];
        }
        query += ")";
        
        return query;
    }

    public static void hideFile(File f)
            throws InterruptedException, IOException {
        // XXX TODO: make it compliant with linux.
        String os = System.getProperty("os.name").toLowerCase();

        if (os.indexOf("windows") >= 0) {
            // win32 command line variant
            Process p = Runtime.getRuntime().exec("attrib +h " + f.getPath());
            p.waitFor();
        }
    }

    /**
     * Returns true if any of the files in the array is a parent directory of
     * file f.
     * @param dir an array of directories
     * @param f the file.
     * @return true if any of the files in the array is a parent directory of
     * file f.
     */
    public static boolean isParentDirectory(File[] dir, File f) {
        //XXX TODO: optimize it (and check if correct!)
        try {
            String fname = f.getCanonicalPath();
            for (int i = 0; i < dir.length; ++i) {
                String dirPath = dir[i].getCanonicalPath();
                if (dirPath.length() > fname.length()) {
                    continue;
                }
                String fname_sub = fname.substring(0, dirPath.length());
                assert(fname_sub.length() == dirPath.length());
                if (fname_sub.equals(dirPath) == true) {
                    return true;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Returns the size of file <code>file</code> in bytes. If <code>file</code>
     * is a directory, the method returns the summary of all the files contained.
     *
     * @param file A file or directory.
     * @return The size of file <code>file</code> in bytes.
     */
    public static long getFileSize(File file) {
        long size = 0;

        if (file.isDirectory() == false) {
            return file.length();
        }
        else {
            File[] filelist = file.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                if (filelist[i].isDirectory()) {
                    size += getFileSize(filelist[i]);
                } else {
                    size += filelist[i].length();
                }
            }
            return size;
        }
    }

}
