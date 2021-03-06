/*
 * FileHandler.java
 *
 * Created on 2 September 2006, 1:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.ImageIcon;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import puggle.Util.RelativePath;
import puggle.ui.ImageControl;

/**
 *
 * @author gvasil
 */
public class FileHandler {
    private Properties handlerProps;
    private boolean STORE_TEXT;
    private boolean STORE_THUMBNAIL;
    
    /** Creates a new instance of FileHandler */
    public FileHandler(Properties props, boolean storeText, boolean storeThumb) {
        this.handlerProps = props;
        this.STORE_TEXT = storeText;
        this.STORE_THUMBNAIL = storeThumb;
    }
    
    public FileHandler(boolean storeText, boolean storeThumb) {
        this.STORE_TEXT = storeText;
        this.STORE_THUMBNAIL = storeThumb;
        
        this.handlerProps = new Properties();
        this.handlerProps.setProperty("txt", "puggle.LexicalAnalyzer.TextHandler");
        this.handlerProps.setProperty("pdf", "puggle.LexicalAnalyzer.PDFHandler");
        this.handlerProps.setProperty("doc", "puggle.LexicalAnalyzer.DOCHandler");
        this.handlerProps.setProperty("rtf", "puggle.LexicalAnalyzer.RTFHandler");
        this.handlerProps.setProperty("wpd", "puggle.LexicalAnalyzer.WordPerfectHandler");
        this.handlerProps.setProperty("html", "puggle.LexicalAnalyzer.HTMLHandler");
        this.handlerProps.setProperty("htm", "puggle.LexicalAnalyzer.HTMLHandler");
        this.handlerProps.setProperty("xls", "puggle.LexicalAnalyzer.XLSHandler");
        this.handlerProps.setProperty("ppt", "puggle.LexicalAnalyzer.PPTHandler");

        //this.handlerProps.setProperty("pptx", "puggle.LexicalAnalyzer.Office2007Handler");
        //this.handlerProps.setProperty("docx", "puggle.LexicalAnalyzer.Office2007Handler");
        //this.handlerProps.setProperty("xlsx", "puggle.LexicalAnalyzer.Office2007Handler");
        
        this.handlerProps.setProperty("mp3", "puggle.LexicalAnalyzer.MP3Handler");
        this.handlerProps.setProperty("jpg", "puggle.LexicalAnalyzer.ImageHandler");
        this.handlerProps.setProperty("jpeg", "puggle.LexicalAnalyzer.ImageHandler");
        this.handlerProps.setProperty("gif", "puggle.LexicalAnalyzer.ImageHandler");
        this.handlerProps.setProperty("png", "puggle.LexicalAnalyzer.ImageHandler");

        this.handlerProps.setProperty("zip", "puggle.LexicalAnalyzer.ZipHandler");
        this.handlerProps.setProperty("rar", "puggle.LexicalAnalyzer.RarHandler");

        this.handlerProps.setProperty("exe", "puggle.LexicalAnalyzer.AppHandler");
        this.handlerProps.setProperty("com", "puggle.LexicalAnalyzer.AppHandler");
        this.handlerProps.setProperty("cab", "puggle.LexicalAnalyzer.AppHandler");
        this.handlerProps.setProperty("msi", "puggle.LexicalAnalyzer.AppHandler");
    }

    /**
     * Analyze the corresponding file and returns a document with the appropriate
     * information filled.
     *
     * @param file the file to be analyzed.
     *
     * @return a document with the appropriate information.
     */
    public Document getDocument(File file) throws FileHandlerException {
        Document doc = null;
        
        String name = file.getName();
        int dotIndex = name.lastIndexOf(".");

        String ext = "";
        
        if (file.isDirectory()) {
            try {
                doc = new FolderHandler().getDocument(file);
            } catch (DocumentHandlerException ex) {
                throw new FileHandlerException(
                        "Directory cannot be handled: "
                        + file.getAbsolutePath(), ex);
            }
        }
        else if ((dotIndex > 0) && (dotIndex < name.length())) {
            ext = name.substring(dotIndex + 1, name.length());
            ext = ext.toLowerCase();
            String handlerClassName = handlerProps.getProperty(ext);
            
            if (handlerClassName != null) {
                try {
                    Class handlerClass = Class.forName(handlerClassName);
                    DocumentHandler handler =
                            (DocumentHandler) handlerClass.newInstance();
                    handler.setStoreText(this.STORE_TEXT);
                    handler.setStoreThumbnail(this.STORE_THUMBNAIL);
                    doc = handler.getDocument(file);
                }
                catch (ClassNotFoundException e) {
                    doc = null;
                    /*throw new FileHandlerException(
                            "Cannot create instance of : "
                            + handlerClassName, e);*/
                }
                catch (InstantiationException e) {
                    doc = null;
                    /*throw new FileHandlerException(
                            "Cannot create instance of : "
                            + handlerClassName, e);*/
                }
                catch (IllegalAccessException e) {
                    doc = null;
                    /*throw new FileHandlerException(
                            "Cannot create instance of : "
                            + handlerClassName, e);*/
                }
                catch (DocumentHandlerException e) {
                    doc = null;
                    /*throw new FileHandlerException(
                            "Document cannot be handled: "
                            + file.getAbsolutePath(), e);*/
                }
                catch (Exception e) {
                    doc = null;
                    /*throw new FileHandlerException(
                            "Document cannot be handled: "
                            + file.getAbsolutePath(), e);*/
                }
                catch (OutOfMemoryError bounded){
                    doc = null;
                    /*throw new FileHandlerException(
                            "Out of Memory while handling: "
                            + file.getAbsolutePath());*/
                }
            }
        }

        if (doc == null) { // no file handler found, or something went wrong during analysis;
            doc = new Document();
            doc.add(new Field("filetype", ext, Field.Store.YES,
                    Field.Index.UN_TOKENIZED));
        }

        try {
            doc.add(new Field("path", file.getCanonicalPath(),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field("size", String.valueOf(file.length()),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field("inpath",
                    file.getCanonicalPath().replace(File.separatorChar, ' '),
                    Field.Store.YES,
                    Field.Index.TOKENIZED));
        } catch (IOException e) {
            throw new FileHandlerException(e.getMessage());
        }

        doc.add(new Field("last modified", String.valueOf(file.lastModified()),
                Field.Store.YES, Field.Index.NO));

        String name_only = null;
        if (dotIndex < 0) { name_only = name; }
        else { name_only = name.substring(0, dotIndex).toLowerCase(); }

        doc.add(new Field("filename", name_only, Field.Store.YES,
                  Field.Index.TOKENIZED));

        return doc;
    }
    
    /**
     * Analyze the corresponding file and returns a document with the appropriate
     * information filled. The path of the file is relative with the home file.
     *
     * @param file the file to be analyzed.
     * @param home the file that will be used as root to find the relative path
     * of the file to be analyzed.
     *
     * @return a document with the appropriate information.
     */
    public Document getDocument(File file, File home) throws FileHandlerException {
        Document doc = this.getDocument(file);
        
        if (home != null) {
            String path = RelativePath.getRelativePath(home, file);
            
            doc.removeField("path");
            doc.add(new Field("path", path, Field.Store.YES, Field.Index.UN_TOKENIZED));

            doc.removeField("inpath");
            doc.add(new Field("inpath",
                    path.replace(File.separatorChar, ' '),
                    Field.Store.YES,
                    Field.Index.TOKENIZED));
        }
        
        return doc;
    }
    
    public static ImageIcon getThumbnail(Document doc) throws FileHandlerException {
        ImageIcon icon = null;
        byte[] bytes = doc.getBinaryValue("thumbnail");
        if (bytes == null) {
            try {
                icon = ImageControl.getImageControl().getThumbnailOf(new File(doc.get("path")));
            } catch (FileNotFoundException ex) {
                throw new FileHandlerException(ex.getMessage());
            }
        } else {
            icon = new ImageIcon(bytes);
        }
        return icon;
    }

    public static ImageIcon getDefaultThumbnail(Document doc) throws FileHandlerException {
        try {
            return ImageControl.getImageControl().getDefaultThumbnailOf(new File(doc.get("path")));
        } catch (FileNotFoundException ex) {
            throw new FileHandlerException(ex.getMessage());
        }
    }
    
    public static ImageIcon getThumbnail(Document doc, int x, int y) throws FileHandlerException {
        ImageIcon icon = FileHandler.getThumbnail(doc);
        
        java.awt.Image image =
                icon.getImage().getScaledInstance(x, y, java.awt.image.BufferedImage.SCALE_SMOOTH);
        
        return new ImageIcon(image);
    }

    public static ImageIcon getDefaultThumbnail(Document doc, int x, int y) throws FileHandlerException {
        ImageIcon icon = FileHandler.getDefaultThumbnail(doc);

        java.awt.Image image =
                icon.getImage().getScaledInstance(x, y, java.awt.image.BufferedImage.SCALE_SMOOTH);

        return new ImageIcon(image);
    }
    
    public String getText(Document doc) throws FileHandlerException {
        File file = new File(doc.get("path"));
        
        if (file.isDirectory()) {
            try {
                return new FolderHandler().getText(doc);
            } catch (DocumentHandlerException ex) {
                throw new FileHandlerException(
                        "Directory cannot be handled: "
                        + file.getAbsolutePath(), ex);
            }
        }
        
        String name = file.getName();
        int dotIndex = name.lastIndexOf(".");
        if ((dotIndex > 0) && (dotIndex < name.length())) {
            String ext = name.substring(dotIndex + 1, name.length());
            ext = ext.toLowerCase();
            String handlerClassName = handlerProps.getProperty(ext);
            
            if (handlerClassName == null) {
                return null;
            }
            
            try {
                Class handlerClass = Class.forName(handlerClassName);
                DocumentHandler handler =
                        (DocumentHandler) handlerClass.newInstance();
                return handler.getText(doc);
            }
            catch (ClassNotFoundException e) {
                throw new FileHandlerException(
                        "Cannot create instance of : "
                        + handlerClassName, e);
            }
            catch (InstantiationException e) {
                throw new FileHandlerException(
                        "Cannot create instance of : "
                        + handlerClassName, e);
            }
            catch (IllegalAccessException e) {
                throw new FileHandlerException(
                        "Cannot create instance of : "
                        + handlerClassName, e);
            }
            catch (DocumentHandlerException e) {
                throw new FileHandlerException(
                        "Document cannot be handled: "
                        + file.getAbsolutePath(), e);
            }
        }
        return null;
    }
    
    public static void main(String[] args) throws Exception {
        if (args.length == 2) {
            Properties props = new Properties();
            props.load(new FileInputStream(args[0]));
            
            FileHandler fileHandler = new FileHandler(props, true, true);
            Document doc = fileHandler.getDocument(new File(args[1]));
            System.out.println(doc);
        } else {
            FileHandler fileHandler = new FileHandler(true, true);
            Document doc = fileHandler.getDocument(new File(args[1]));
            System.out.println(doc);
        }
    }
}

