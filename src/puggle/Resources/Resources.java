/*
 * Resources.java
 *
 * Created on 6 September 2006, 6:28
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
    
    /* final string variables */
    public final static String APP_NAME = "Puggle";
    
    public final static String APP_VERSION = "v0.2";
    
    private final static String APP_DIRECTORY_NAME = System.getProperty("user.home") 
                + File.separator + "." + APP_NAME.toLowerCase();
    
    private final static String APP_DIRECTORY_NAME_PORTABLE =
            System.getProperty("user.dir") + File.separator + "."
            + APP_NAME.toLowerCase();
    
    private final static String LOG_DIRECTORY_NAME      = "logs";
    private final static String PROPERTIES_FILE_NAME    = "props";
    
    /***/
    
    private static String INDEX_DIRECTORY_NAME = "default";
    
    private static String INDEX_CANONICAL_DIRECTORY_NAME =
            Resources.APP_DIRECTORY_NAME
            + File.separator + Resources.INDEX_DIRECTORY_NAME;

    
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

    public static String getApplicationDirectoryCanonicalPath() {
        return Resources.APP_DIRECTORY_NAME;
    }
    
    public static String getApplicationPropertiesCanonicalPath() {
        return Resources.INDEX_CANONICAL_DIRECTORY_NAME + File.separator + Resources.PROPERTIES_FILE_NAME;
    }

    public static String getIndexCanonicalPath() {
        return Resources.INDEX_CANONICAL_DIRECTORY_NAME + File.separator;
    }
    
    public static void setIndex(File path) throws IOException {
        Resources.INDEX_CANONICAL_DIRECTORY_NAME = path.getCanonicalPath();
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
    
    public static void makeApplicationDirectoryTree() throws IOException {
        File dir = new File(Resources.INDEX_CANONICAL_DIRECTORY_NAME);
        if (dir.exists() == false) {
            if (dir.mkdirs() == false) {
                throw new IOException("Cannot create directory '" +dir.getPath() +"'");
            }
        }
    }

    public static File getLogFile() throws IOException {

        String logDir = Resources.INDEX_CANONICAL_DIRECTORY_NAME
                + File.separator + Resources.LOG_DIRECTORY_NAME;
        
        String filePath = logDir + File.separator
                + "log" + Long.toHexString(new Date().getTime()) + ".txt";
        
        File dir = new File(logDir);
        if (dir.exists() == false) {
            if (dir.mkdirs() == false) {
                throw new IOException("Cannot create directory '" + logDir +"'");
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
    
    public static HashSet<String> getAcceptedFileExtensions(PropertiesControl prop) {
//        PropertiesControl prop = new PropertiesControl(new File(Resources.getApplicationPropertiesName()));

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
    
    public static FileFilter getFiletypeFilter(final PropertiesControl prop) {
        FileFilter filter = new FileFilter() {
            private HashSet<String> FiletypeSet =
                    Resources.getAcceptedFileExtensions(prop);
            
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
