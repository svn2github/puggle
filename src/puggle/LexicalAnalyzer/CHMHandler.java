/*
 * CHMHandler.java
 *
 * Created on 23 Σεπτέμβριος 2006, 2:01 πμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.document.Document;

/**
 *
 * @author gvasil
 */
public class CHMHandler  implements DocumentHandler {

    public Document getDocument(File f) throws DocumentHandlerException {
        File tmp = null;
        try {
            tmp = File.createTempFile("foo", ".tmp");
            tmp.delete();
            tmp.mkdir();
            System.out.println(tmp);

            String cmd = "cmd /c hh.exe -decompile \"" + tmp
            + "\" \"" + f + "\"";
            
            System.out.println(cmd);
            Runtime.getRuntime().exec(cmd);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
//        deleteDir(tmp);
        return null;
    }

    public String getText(Document doc) throws DocumentHandlerException {
        return null;
    }
    
    // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
    
        // The directory is now empty so delete it
        return dir.delete();
    }
    
    public static void main(String[] args) throws Exception {
        CHMHandler handler = new CHMHandler();
        Document doc = handler.getDocument(
                new File("C:\\Documents and Settings\\gvasil\\Επιφάνεια εργασίας\\TestPrj.chm"));
        System.out.println(doc);
    }

    public void setStoreText(boolean b) {
    }

    public boolean getStoreText() {
        return false;
    }

    public void setStoreThumbnail(boolean b) {
    }

    public boolean getStoreThumbnail() {
        return false;
    }
}
