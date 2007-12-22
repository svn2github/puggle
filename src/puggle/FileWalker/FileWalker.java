/*
 * FileWalker.java
 *
 * Created on 2 Σεπτέμβριος 2006, 1:41 πμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.FileWalker;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import puggle.Resources.Resources;

/**
 *
 * @author gvasil
 */
public class FileWalker {

    private ArrayList<File> FileList;
    private HashSet<File> Directories;
    private FileFilter filter;
    
    /** Creates a new instance of FileWalker */
    public FileWalker(File dir) throws IOException {
        if (!dir.exists()) {
            throw new IOException("Directory " +dir +" does not exist.");
        }
        if (!dir.isDirectory()) {
            throw new IOException("File " +dir +" is not a directory.");
        }
        if (!dir.canRead()) {
            throw new IOException("Cannot read directory " +dir +".");
        }

        this.FileList = new ArrayList<File>();
        this.FileList.add(dir.getCanonicalFile());
        
        this.Directories = new HashSet<File>();
        
        this.filter = Resources.getFiletypeFilter();
    }
    
    public boolean hasNext() {
        return (!FileList.isEmpty());
    }
    
    public File next() {
        if (FileList.isEmpty() == true) {
            return null;
        }
        
        File f = FileList.remove(FileList.size() - 1);
        
        if (f.isDirectory()) {
            this.Directories.add(f);
            File[] list = f.listFiles(filter);
            if (list != null) {
                for (int i = 0; i < list.length; i++) {
                    if (list[i].exists() && list[i].canRead() && this.Directories.contains(list[i]) == false) {
                        try {
                            FileList.add(list[i].getCanonicalFile());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        
        return f;
    }

    public static void main(String[] args) throws IOException {
        FileWalker walk = new FileWalker(new File("C:\\"));
        
        File f = null;
        int counter = 0;
        while ((f = walk.next()) != null) {
            counter++;
            System.out.println(f + (f.isDirectory() ? "<DIR>" : ""));
        }
        System.out.println("Total: " + counter + " files and directories.");
    }
}
