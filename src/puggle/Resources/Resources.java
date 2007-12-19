/*
 * Resources.java
 *
 * Created on 6 Σεπτέμβριος 2006, 6:28 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.Resources;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import puggle.Indexer.PropertiesControl;
import puggle.LexicalAnalyzer.CombinedAnalyzer;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

/**
 *
 * @author gvasil
 */
public class Resources {
    
    public final static String APP_NAME = "Puggle";
    
    public final static String APP_VERSION = "v0.1";
    
    private final static String APP_DIR_NAME = System.getProperty("user.home") 
                + File.separator + "." + APP_NAME.toLowerCase();
    
    private static String INDEX_FILE_NAME = APP_DIR_NAME
                + File.separator + "index";

    private static String LOG_DIR_NAME = APP_DIR_NAME
                + File.separator + "logs";
    
    private static Properties Attributes = new Properties();
    
    private final static String[] MUSIC_FILETYPES = {
        "mp3"
    };
    
    private final static String[] IMAGES_FILETYPES = {
        "jpg", "jpeg", "gif", "png"
    };
    
    private final static String[] DOCUMENTS_FILETYPES = {
        "txt", "pdf", "doc", "rtf", "html", "htm",
        "xls", "ppt"
    };
    
    public static String getApplicationName() {
        return Resources.APP_NAME;
    }
    
    public static String getApplicationVersion() {
        return Resources.APP_VERSION;
    }

    public static String getApplicationDirPath() {
        return APP_DIR_NAME;
    }
    
    public static String getIndexDirPath() {
        return INDEX_FILE_NAME;
    }
    
    public static String[] getMusicFiletypesArray() {
        return MUSIC_FILETYPES;
    }
    
    public static Set getMusicFiletypesSet() {
        HashSet set = new HashSet();
        set.addAll(Arrays.asList(MUSIC_FILETYPES));
        return set;
    }
    
    public static String[] getImagesFiletypesArray() {
        return IMAGES_FILETYPES;
    }
    
    public static Set getImagesFiletypesSet() {
        HashSet set = new HashSet();
        set.addAll(Arrays.asList(IMAGES_FILETYPES));
        return set;
    }
    
    public static String[] getDocumentsFiletypesArray() {
        return DOCUMENTS_FILETYPES;
    }
    
    public static Set getDocumentsFiletypesSet() {
        HashSet set = new HashSet();
        set.addAll(Arrays.asList(DOCUMENTS_FILETYPES));
        return set;
    }
    
    public static boolean isImage(String extension) {
        extension = extension.toLowerCase();
        for (int i = 0; i < IMAGES_FILETYPES.length; ++i) {
            if (extension.equals(IMAGES_FILETYPES[i]))
                return true;
        }
        
        return false;
    }
    
    private static void makeApplicationFiles() throws IOException {
        File dir = new File(APP_DIR_NAME);
        if (dir.exists() == false) {
            if (dir.mkdir() == false) {
                throw new IOException("Cannot create directory '" +APP_DIR_NAME +"'");
            }
        }
        
        dir = new File(LOG_DIR_NAME);
        if (dir.exists() == false) {
            if (dir.mkdirs() == false) {
                throw new IOException("Cannot create directory '" +LOG_DIR_NAME +"'");
            }
        }
    }
    
    public static void makeApplicationDir() throws IOException {
        File dir = new File(APP_DIR_NAME);
        if (dir.exists() == false) {
            if (dir.mkdir() == false) {
                throw new IOException("Cannot create directory '" +APP_DIR_NAME +"'");
            }
        }
    }

    public static File getLogFile() throws IOException {

        
        String filePath = LOG_DIR_NAME + File.separator
                + "log" + Long.toHexString(new Date().getTime()) + ".txt";
        
        File dir = new File(LOG_DIR_NAME);
        if (dir.exists() == false) {
            if (dir.mkdirs() == false) {
                throw new IOException("Cannot create directory '" + LOG_DIR_NAME +"'");
            }
        }
        
        File file = new File(filePath);
        
        if (file.createNewFile() == false) {
            file = null;
        }
        
        return file;
    }
    
    public static Analyzer getAnalyzer() {
        return new CombinedAnalyzer();
    }
    
    public static HashSet<String> getAcceptedFileExtensions() {
        PropertiesControl prop = PropertiesControl.getPropertiesControl();

        String ext = prop.getImageFiletypes() + ","
                + prop.getMusicFiletypes() + ","
                + prop.getDocumentFiletypes();
        
        HashSet<String> set = new HashSet<String>();
        
        String str[] = ext.split(",");
        for (int i=0; i < str.length; i++) {
            set.add(str[i]);
        }
        
        return set;
    }
    
    public static FileFilter getFiletypeFilter() {
        FileFilter filter = new FileFilter() {
            private HashSet<String> FiletypeSet =
                    Resources.getAcceptedFileExtensions();
            
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String name = null;
                try {
                    name = file.getCanonicalPath();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                int dotIndex = name.lastIndexOf(".");
                String ext = name.substring(dotIndex + 1, name.length());
                ext = ext.toLowerCase();
                
                if (FiletypeSet.contains(ext)) {
                    return true;
                }
                return false;
            }
        };
        
        return filter;
    }
    
}
