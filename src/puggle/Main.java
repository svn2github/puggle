/*
 * Main.java
 *
 * Created on 2 September 2006, 1:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle;

import java.io.File;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import puggle.Indexer.IndexProperties;
import puggle.Indexer.Indexer;
import puggle.Resources.Resources;
import puggle.Util.Util;
import puggle.ui.ImageControl;
import puggle.ui.IndexPropertiesPanel;
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
        
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        
        
        if (Resources.isPortableEdition() == true) {
            File root = new File(System.getProperty("user.dir"));
            while (root.getParentFile() != null) {
                root = root.getParentFile();
            }
            File index = new File(root.getPath() + ".puggle");
            
            Resources.setIndex(index);
            
            File propsFile = new File(Resources.getApplicationPropertiesCanonicalPath());
            IndexProperties props = new IndexProperties(propsFile);
            
            if (props.getVersion().equals(Resources.getApplicationVersion()) == false) {
                props.close(); props = null;
                File f = new File(Resources.getIndexCanonicalPath());
                if (Util.deleteDir(f) == false) {
                    JOptionPane.showMessageDialog(null,
                            "Cannot delete old format directory '" +f +"'.\n"
                            + "Please remove it manually and start again.",
                            "Error Opening Index Directory",
                            JOptionPane.ERROR_MESSAGE,
                            ImageControl.getImageControl().getErrorIcon());
                    System.exit(1);
                }
            }
            
            if (IndexReader.indexExists(Resources.getIndexCanonicalPath()) == false) {
                
                props.setPortable(true);
                props.setFilesystemRoot(root.getAbsolutePath());
                props.setPath(root.getPath());
                Indexer indexer = new Indexer(index, props);
                indexer.close();
                
                IndexPropertiesPanel panel = new IndexPropertiesPanel();
                panel.setProperties(props);
                
                JDialog dialog = new JDialog((java.awt.Dialog)null, "Index Properties", true);
                
                dialog.getContentPane().add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setResizable(true);
                dialog.setVisible(true);
            }
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new SearchFrame().setVisible(true);
                }
            });
        }
        else {
            Resources.makeApplicationDirectoryTree();

            if (IndexReader.indexExists(Resources.getIndexCanonicalPath())) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new SearchFrame().setVisible(true);
                    }
                });
            } else {
                Object[] options = {"Create Index...", "Open Index..."};
                
                int n = JOptionPane.showOptionDialog(null,
                        "Puggle wasn't able to find any valid Index Directory.\n "
                        + "You can either create a new one or explicity load an\n"
                        + "existing one.",
                        "Puggle Desktop Search",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        null);
                
                if (n == JOptionPane.YES_OPTION) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            new IndexerFrame().setVisible(true);
                        }
                    });
                }
                else if (n == JOptionPane.NO_OPTION) {
                    JFileChooser fc = new JFileChooser();
                    
                    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fc.setCurrentDirectory(new java.io.File(Resources.getApplicationDirectoryCanonicalPath()));
                    fc.setDialogTitle("Select Index Directory");
                    
                    boolean error = true;
                    while (error == true) {
                        error = false;
                        
                        int returnVal = fc.showOpenDialog(null);
                        
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = fc.getSelectedFile();
                            
                            boolean exists = Indexer.indexExists(file);
                            String directory = file.getPath();
                            
                            if (exists == true) {
                                try {
                                    Resources.setIndex(file);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                
                                File propsFile = new File(Resources.getApplicationPropertiesCanonicalPath());
                                IndexProperties props = new IndexProperties(propsFile);
                                
                                if (props.getVersion().equals(Resources.getApplicationVersion()) == false) {
                                    props.close(); props = null;
                                    File f = new File(Resources.getIndexCanonicalPath());
                                    if (Util.deleteDir(f) == false) {
                                        JOptionPane.showMessageDialog(null,
                                                "Cannot delete old format directory '" +f +"'.\n"
                                                + "Please remove it manually and start again.",
                                                "Error Opening Index Directory",
                                                JOptionPane.ERROR_MESSAGE,
                                                ImageControl.getImageControl().getErrorIcon());
                                        System.exit(1);
                                    }
                                }
                                
                                JOptionPane.showMessageDialog(null,
                                        "Index directory '" +directory +"' successfully loaded.",
                                        "Open Index Directory",
                                        JOptionPane.INFORMATION_MESSAGE,
                                        ImageControl.getImageControl().getInfoIcon());
                                
                                java.awt.EventQueue.invokeLater(new Runnable() {
                                    public void run() {
                                        new SearchFrame().setVisible(true);
                                    }
                                });
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Directory '" +directory +"' is not a valid index.",
                                        "Error Opening Index Directory",
                                        JOptionPane.ERROR_MESSAGE,
                                        ImageControl.getImageControl().getErrorIcon());
                                error = true;
                            }

                        } // if
                        
                    } // while
                }
            }
        } // else (Resources.isPortableEdition() == false)
    }
    
}
