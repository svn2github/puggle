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
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import puggle.Indexer.IndexProperties;
import puggle.Indexer.Indexer;
import puggle.Resources.Resources;
import puggle.Util.Util;
import puggle.ui.ImageControl;
import puggle.ui.SearchFrame;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import org.apache.lucene.index.IndexReader;
import puggle.ui.IndexPropertiesDialog;

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

        /* Check if application already running */
        try {
            lockFile = new File(System.getProperty("user.home") 
                + File.separator + "puggle.lock");
            // Check if the lock exist
            if (lockFile.exists()) {
                // if exist try to delete it
                lockFile.delete();
            }
            // Try to get the lock
            channel = new RandomAccessFile(lockFile, "rw").getChannel();
            lock = channel.tryLock();
            if(lock == null)
            {
                // File is lock by other application
                channel.close();
                JOptionPane.showMessageDialog(null,
                        "Another instance of Puggle is already running.",
                        "Puggle",
                        JOptionPane.ERROR_MESSAGE,
                        ImageControl.getImageControl().getErrorIcon());
                return;
            }
            // Add shutdown hook to release lock when application shutdown
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);
        }
        catch(IOException e)
        {
            throw new RuntimeException("Could not start process.", e);
        }

        if (Resources.isPortableEdition() == true) {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("windows") < 0) {
                JOptionPane.showMessageDialog(null,
                        "Sorry, portable edition is currently running\n"
                        +"only in MS Windows operating systems.",
                        "Puggle Portable",
                        JOptionPane.ERROR_MESSAGE,
                        ImageControl.getImageControl().getErrorIcon());
                return;
            }
            
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
                System.gc();
                File f = new File(Resources.getIndexCanonicalPath());
                if (Util.deleteDir(f) == false) {
                    JOptionPane.showMessageDialog(null,
                            "Cannot delete directory '" +f +"'.\n"
                            + "Please remove it manually and start application again.",
                            "Error Opening Index Directory",
                            JOptionPane.ERROR_MESSAGE,
                            ImageControl.getImageControl().getErrorIcon());
                    System.exit(1);
                }
                props = new IndexProperties(propsFile);
            }
            
            if (IndexReader.indexExists(Resources.getIndexCanonicalPath()) == false) {
                props.setPortable(true);
                props.setFilesystemRoot(root.getAbsolutePath());
                props.setPath(root.getPath());
                Indexer indexer = new Indexer(index, props);
                indexer.close();
            }
        }
        else {
            Resources.makeApplicationDirectoryTree();
            
            File index = new File(Resources.getApplicationDirectoryCanonicalPath());
            File propsFile = new File(Resources.getApplicationPropertiesCanonicalPath());
            
            IndexProperties props = new IndexProperties(propsFile);
            
            if (props.getVersion().equals(Resources.getApplicationVersion()) == false) {
                props.close(); props = null;
                System.gc();
                File f = new File(Resources.getIndexCanonicalPath());
                if (Util.deleteDir(f) == false) {
                    JOptionPane.showMessageDialog(null,
                            "Cannot delete directory '" +f +"'.\n"
                            + "Please remove it manually and start application again.",
                            "Error Opening Index Directory",
                            JOptionPane.ERROR_MESSAGE,
                            ImageControl.getImageControl().getErrorIcon());
                    System.exit(1);
                }
                props = new IndexProperties(propsFile);
            }
            
            if (IndexReader.indexExists(Resources.getIndexCanonicalPath()) == false) {
                props.setPath(System.getProperty("user.home"));
                Indexer indexer = new Indexer(index, props);
                indexer.close();
                
                IndexPropertiesDialog dialog = new IndexPropertiesDialog((java.awt.Frame)null, true);
                dialog.setProperties(props);

                /* Check all options in dialog; default option for new Index */
                dialog.indexPropertiesPanel.setSelected(true);
                
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
            
/*            if (IndexReader.indexExists(Resources.getIndexCanonicalPath())) {
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
            }*/
        } // else (Resources.isPortableEdition() == false)

        // pop-up window!
        java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    SearchFrame frm = new SearchFrame();
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);
                }
            });
    }

    public static void unlockFile() {
        // release and delete file lock
        try {
            if(lock != null) {
                lock.release();
                channel.close();
                lockFile.delete();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static class ShutdownHook extends Thread {
        @Override
        public void run() {
            unlockFile();
        }
    }

    private static File lockFile;
    private static FileChannel channel;
    private static FileLock lock;
    
}
