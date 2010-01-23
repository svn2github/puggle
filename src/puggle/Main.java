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
import puggle.Util.Updater;
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

        /* use anti-alias fonts */
        // XXX comment this for now - does not display well on my laptop; maybe
        // needs lcd instead.
        //System.setProperty("awt.useSystemAAFontSettings", "on");

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

        IndexProperties props = null;

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
            props = new IndexProperties(propsFile);
            
            if (props.getVersion().equals(Resources.getApplicationVersion()) == false) {
                props.close(); props = null;
                System.gc();
                File f = new File(Resources.getIndexCanonicalPath());
                if (Util.deleteDir(f) == false) {
                    JOptionPane.showMessageDialog(null,
                            "Cannot delete directory '" +f +"'.\n"
                            + "Please remove it manually and start application again.",
                            "Error Removing Old Index Directory",
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

                IndexPropertiesDialog dialog = new IndexPropertiesDialog((java.awt.Frame)null, true);
                dialog.setProperties(props);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        }
        else {
            Resources.makeApplicationDirectoryTree();
            
            File index = new File(Resources.getApplicationDirectoryCanonicalPath());
            File propsFile = new File(Resources.getApplicationPropertiesCanonicalPath());
            
            props = new IndexProperties(propsFile);
            
            if (props.getVersion().equals(Resources.getApplicationVersion()) == false) {
                props.close(); props = null;
                System.gc();
                File f = new File(Resources.getApplicationDirectoryCanonicalPath());
                if (Util.deleteDir(f) == false) {
                    JOptionPane.showMessageDialog(null,
                            "Cannot delete directory '" +f +"'.\n"
                            + "Please remove it manually and start application again.",
                            "Error Removing Old Index Directory",
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
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        } // else (Resources.isPortableEdition() == false)

        // pop-up window!
        java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    SearchFrame frm = new SearchFrame();
                    frm.setLocationRelativeTo(null);
                    frm.setVisible(true);
                }
            });

            Updater update = new Updater();

            try {
                if (update.isUpdated() == false) {
                    JOptionPane.showMessageDialog(null,
                            "<html>"
                            +"The Puggle version that is currently running appears to be old. <br/><br/>"
                            +"You can update Puggle manually by visiting this link and <br/>"
                            +"downloading the latest version:<p align=\"center\">"
                            +"<a href=\"" +Resources.getApplicationWebsite() +"\">"
                            +Resources.getApplicationWebsite()
                            +"</p></a><br/></html>",
                            "Update found",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (Exception ex) {
                // checking for updates fail for some reason; do nothing
            }
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
