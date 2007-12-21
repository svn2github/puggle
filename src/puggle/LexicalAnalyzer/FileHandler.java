/*
 * FileHandler.java
 *
 * Created on 2 Σεπτέμβριος 2006, 1:38 μμ
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
        this.handlerProps.setProperty("html", "puggle.LexicalAnalyzer.HTMLHandler");
        this.handlerProps.setProperty("htm", "puggle.LexicalAnalyzer.HTMLHandler");
        this.handlerProps.setProperty("xls", "puggle.LexicalAnalyzer.XLSHandler");
        this.handlerProps.setProperty("ppt", "puggle.LexicalAnalyzer.PPTHandler");
        
        this.handlerProps.setProperty("mp3", "puggle.LexicalAnalyzer.MP3Handler");
        this.handlerProps.setProperty("jpg", "puggle.LexicalAnalyzer.ImageHandler");
        this.handlerProps.setProperty("jpeg", "puggle.LexicalAnalyzer.ImageHandler");
        this.handlerProps.setProperty("gif", "puggle.LexicalAnalyzer.ImageHandler");
        this.handlerProps.setProperty("png", "puggle.LexicalAnalyzer.ImageHandler");
    }

    public Document getDocument(File file) throws FileHandlerException {

        if (file.isDirectory()) {
            try {
                return new FolderHandler().getDocument(file);
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
                handler.setStoreText(this.STORE_TEXT);
                handler.setStoreThumbnail(this.STORE_THUMBNAIL);
                return handler.getDocument(file);
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
            catch (Exception e) {
                throw new FileHandlerException(
                        "Document cannot be handled: "
                        + file.getAbsolutePath(), e);
            }
            catch (OutOfMemoryError bounded){
                throw new FileHandlerException(
                        "Out of Memory while handling: "
                        + file.getAbsolutePath());
            }
        }
        return null;
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
            Document doc = fileHandler.getDocument(new File("C:\\Documents and Settings\\gvasil\\Επιφάνεια εργασίας\\la.pdf"));
            System.out.println(doc);
        }
    }
}

