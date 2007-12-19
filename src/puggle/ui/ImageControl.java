/*
 * ImageControl.java
 *
 * Created on 5 Φεβρουάριος 2007, 12:05 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.ui;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import org.jpedal.PdfDecoder;

/**
 *
 * @author gvasil
 */
public class ImageControl {
    
    private ImageIcon STARS_ICON[];
    
    private Hashtable<String, ImageIcon> iconsTable;
    
    private ImageIcon INFO_ICON;
    
    private ImageIcon QUESTION_ICON;
    
    private ImageIcon WARNING_ICON;
    
    private ImageIcon ERROR_ICON;
    
    private ImageIcon SEARCH_ICON;
    
    private Image TRAY_ICON;
    
    private static final float DEFAULT_JPEG_QUALITY = 0.75f;
    private static final int DEFAULT_THUMBNAIL_HEIGHT = 150;
    
    private static final int ICON_DIMENSION = 64;
    
    private static final int IMAGE_DIMENSION = 80;
    
    private static ImageControl imageControl;
    
    private ImageControl() {
        this.STARS_ICON = new ImageIcon[6];
        
        this.STARS_ICON[0] = new ImageIcon(getClass().getResource("/star-0.gif"));
        this.STARS_ICON[1] = new ImageIcon(getClass().getResource("/star-1.gif"));
        this.STARS_ICON[2] = new ImageIcon(getClass().getResource("/star-2.gif"));
        this.STARS_ICON[3] = new ImageIcon(getClass().getResource("/star-3.gif"));
        this.STARS_ICON[4] = new ImageIcon(getClass().getResource("/star-4.gif"));
        this.STARS_ICON[5] = new ImageIcon(getClass().getResource("/star-5.gif"));
        
        this.INFO_ICON = new ImageIcon(getClass().getResource("/gtk-dialog-info.png"));
        this.QUESTION_ICON = new ImageIcon(getClass().getResource("/gtk-dialog-question.png"));
        this.WARNING_ICON = new ImageIcon(getClass().getResource("/gtk-dialog-warning.png"));
        this.ERROR_ICON = new ImageIcon(getClass().getResource("/gtk-dialog-error.png"));
        this.SEARCH_ICON = new ImageIcon(getClass().getResource("/icon-search.png"));

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        this.TRAY_ICON = toolkit.getImage(getClass().getResource("/tray-icon.png"));
        
        this.iconsTable = new Hashtable<String, ImageIcon>();
        
        Image image = toolkit.getImage(getClass().getResource("/text-html.png"));
        image = image.getScaledInstance(ICON_DIMENSION, ICON_DIMENSION, BufferedImage.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        this.iconsTable.put("html", icon);
        this.iconsTable.put("htm", icon);
        
        image = toolkit.getImage(getClass().getResource("/text-spreadsheet.png"));
        image = image.getScaledInstance(ICON_DIMENSION, ICON_DIMENSION, BufferedImage.SCALE_SMOOTH);
        this.iconsTable.put("xls", new ImageIcon(image));
        
        image = toolkit.getImage(getClass().getResource("/text-richtext.png"));
        image = image.getScaledInstance(ICON_DIMENSION, ICON_DIMENSION, BufferedImage.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        this.iconsTable.put("rtf", icon);
        this.iconsTable.put("doc", icon);
        
        image = toolkit.getImage(getClass().getResource("/text-x-generic.png"));
        image = image.getScaledInstance(ICON_DIMENSION, ICON_DIMENSION, BufferedImage.SCALE_SMOOTH);
        this.iconsTable.put("txt", new ImageIcon(image));
        
        image = toolkit.getImage(getClass().getResource("/application-pdf.png"));
        image = image.getScaledInstance(ICON_DIMENSION, ICON_DIMENSION, BufferedImage.SCALE_SMOOTH);
        this.iconsTable.put("pdf", new ImageIcon(image));
        
        image = toolkit.getImage(getClass().getResource("/audio-x-mpeg.png"));
        image = image.getScaledInstance(ICON_DIMENSION, ICON_DIMENSION, BufferedImage.SCALE_SMOOTH);
        this.iconsTable.put("mp3", new ImageIcon(image));
        
        image = toolkit.getImage(getClass().getResource("/gnome-fs-directory.png"));
        image = image.getScaledInstance(ICON_DIMENSION, ICON_DIMENSION, BufferedImage.SCALE_SMOOTH);
        this.iconsTable.put("folder", new ImageIcon(image));
    }
    
    public static synchronized ImageControl getImageControl() {
        if (imageControl == null) {
            imageControl = new ImageControl();
        }

        return imageControl;
    }
    
    public ImageIcon getStarsIcon(int number) {
        return STARS_ICON[number];
    }
    
    public ImageIcon getWarningIcon() {
        return this.WARNING_ICON;
    }
    
    public ImageIcon getInfoIcon() {
        return this.INFO_ICON;
    }
    
    public ImageIcon getQuestionIcon() {
        return this.QUESTION_ICON;
    }
    
    public ImageIcon getErrorIcon() {
        return this.ERROR_ICON;
    }
    
    public Image getTrayIcon() {
        return this.TRAY_ICON;
    }
    
    /**A method that scales a Buffered image and takes the required height as a refference point**/
    public BufferedImage getPDFScaledCover(String filename, int maxDim)
    throws java.lang.Exception {
        BufferedImage image = null;
        try {
            PdfDecoder decoder = new PdfDecoder();
            decoder.setExtractionMode(0, 72, 1);
            decoder.openPdfFile(filename);
            decoder.decodePage(1);
            image = decoder.getPageAsImage(1);
            
            decoder.closePdfFile();
            
            decoder = null;
        } catch (final java.lang.OutOfMemoryError e ) {
            return null;
        } catch (NoClassDefFoundError ex) {
            return null;
        }
        
        int width = image.getWidth();
        int height = image.getHeight();

        int max = Math.max(width, height);
        int z = max / maxDim;
        
        Image scaledImage = image.getScaledInstance(width / z, height / z,
                BufferedImage.SCALE_SMOOTH);
        
        BufferedImage outImage = new BufferedImage(width / z, height / z,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)outImage.createGraphics();
        g2.drawImage(scaledImage, 0, 0, null);
        
        scaledImage = null;
        return outImage;
    }
    
    public ImageIcon getThumbnailOf(File file) throws FileNotFoundException {
        sun.awt.shell.ShellFolder sf;
        ImageIcon icon = null;
        
        String path = file.getPath();
        
        String suffix = path.substring(path.lastIndexOf('.') + 1).toLowerCase();

        if (file.isDirectory()) {
            icon = this.iconsTable.get("folder");
        } else if (suffix.equals("jpg") || suffix.equals("jpeg") ||
                suffix.equals("png") || suffix.equals("gif")) {
                try {
                    icon = new ImageIcon(
                            getImageThumbnail(file.getCanonicalPath(),
                            IMAGE_DIMENSION));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        } else {
            icon = this.iconsTable.get(suffix);
        }
        
        if (icon == null) {
            sf = sun.awt.shell.ShellFolder.getShellFolder(file);
            icon = new ImageIcon(sf.getIcon(true), sf.getFolderType());
        }
        
        return icon;
    }
    
    private Image getImageThumbnail(String filename, int maxDim) {
        ImageIcon icon = new ImageIcon(filename);
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        Image image = icon.getImage();
        int max = Math.max(width, height);
        int z = max / maxDim;
        if (z == 0) {
            z = 1;
        }
        image = image.getScaledInstance(width / z, height / z,
                BufferedImage.SCALE_SMOOTH);
        return image; /*
        
        BufferedImage outImage = new BufferedImage(width / z, height / z,
         BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)outImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        
        image = null;
        return outImage;*/
        
/*        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(filename);
        image = image.getScaledInstance(maxDim, maxDim, Image.SCALE_DEFAULT);
        return image;*/
    }
    
    public byte[] toByteArray(BufferedImage img) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Iterator writers= ImageIO.getImageWritersByMIMEType("image/jpeg");
        ImageWriter imgWriter= (ImageWriter)writers.next();
        
        ImageOutputStream imgStream= ImageIO.createImageOutputStream(output);
        imgWriter.setOutput(imgStream);
        
        imgWriter.write(img);

        byte[] data = null;

        data = output.toByteArray();
        output.close();
        
        return data;
    }
    
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(); 
    }
}
