/*
 * Main.java
 *
 * Created on 2 Σεπτέμβριος 2006, 1:37 πμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import puggle.Resources.Resources;
import puggle.ui.IndexerFrame;
import puggle.ui.SearchFrame;
import java.io.IOException;
import org.apache.lucene.index.IndexReader;

/**
 *
 * @author gvasil
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Είναι μια πολύ καλή αρχή!!!");

        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (IndexReader.indexExists(Resources.getIndexDirPath())) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new SearchFrame(Resources.getIndexDirPath()).setVisible(true);
                }
            });
        } else {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new IndexerFrame().setVisible(true);
                }
            });
        }
    }
    
}
