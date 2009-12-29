/*
 * ImageHandler.java
 *
 * Created on 4 September 2006, 2:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 *
 * @author gvasil
 */
public class ImageHandler implements DocumentHandler {
    
    /** Creates a new instance of JPEGHandler */
    public ImageHandler() {
    }
    
    public Document getDocument(File f) throws DocumentHandlerException {
        String path = null, filename = null, parent = null;
        Document doc = new Document();
        try {
            path = f.getCanonicalPath();
            filename = path.substring(path.lastIndexOf(File.separatorChar) + 1);
            parent = path.substring(0, path.lastIndexOf(File.separatorChar));
            parent = parent.substring(parent.lastIndexOf(File.separatorChar) + 1);
            
            BufferedImage img = ImageIO.read(new File(path));
            
            if (img == null) {
                throw new DocumentHandlerException("Error decoding image.");
            }
            
            int width = img.getWidth();
            int height = img.getHeight();
            
            doc.add(new Field("content", filename, Field.Store.NO,
                    Field.Index.TOKENIZED, Field.TermVector.YES));
            doc.add(new Field("content", parent, Field.Store.NO,
                    Field.Index.TOKENIZED, Field.TermVector.YES));
/*            doc.add(new Field("path", path,
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field("size", String.valueOf(f.length()),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
*/            doc.add(new Field("width", String.valueOf(width),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field("height", String.valueOf(height),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
        } catch (IOException e) {
            throw new DocumentHandlerException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new DocumentHandlerException(e.getMessage());
        }

        String type = path.substring(path.lastIndexOf('.') + 1);
        doc.add(new Field("filetype", type, Field.Store.YES,
                Field.Index.UN_TOKENIZED));
        doc.add(new Field("last modified", String.valueOf(f.lastModified()),
                Field.Store.YES, Field.Index.NO));
        
        return doc;
    }
    
  public String getText(Document doc) {
      return null;
  }
  
  private String getText(InputStream is) {
      return null;
  }
  
    private boolean STORE_TEXT;
    private boolean STORE_THUMBNAIL;
    
    public void setStoreText(boolean b) {
        this.STORE_TEXT = b;
    }

    public boolean getStoreText() {
        return this.STORE_TEXT;
    }

    public void setStoreThumbnail(boolean b) {
        this.STORE_THUMBNAIL = b;
    }

    public boolean getStoreThumbnail() {
        return this.STORE_THUMBNAIL;
    }
    
    public static void main(String[] args) throws Exception {
        ImageHandler handler = new ImageHandler();
        Document doc = handler.getDocument(
                new File(args[1]));
        System.out.println(doc);
    }
}
