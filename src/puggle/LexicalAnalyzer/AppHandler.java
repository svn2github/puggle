/*
 * AppHandler.java
 *
 * Created on 31 December 2009, 6:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 *
 * @author gvasil
 */
public class AppHandler implements DocumentHandler {

  public Document getDocument(File f) throws DocumentHandlerException {
      String fileName = f.getName().toLowerCase();
      InputStream is = null;

      try {
          is = new FileInputStream(f);
      }
      catch (FileNotFoundException e) {
          throw new DocumentHandlerException(
                  "File not found: "
                  + f.getAbsolutePath(), e);
      }

      Document doc = new Document();

      int dotIndex = fileName.lastIndexOf(".");
      String ext = fileName.substring(dotIndex + 1, fileName.length());
      String name = fileName.substring(0, dotIndex);

      doc.add(new Field("filetype", ext, Field.Store.YES,
              Field.Index.UN_TOKENIZED));

      doc.add(new Field("app", name + " " + ext + " " + fileName, Field.Store.NO,
                  Field.Index.TOKENIZED));

      doc.add(new Field("content", name + " " + ext + " " + fileName, Field.Store.NO,
                  Field.Index.TOKENIZED, Field.TermVector.WITH_OFFSETS));

      return doc;
  }
  
  public String getText(Document doc) throws DocumentHandlerException {
      return null;
  }
  
  private String getText(InputStream is) throws IOException {
      throw new UnsupportedOperationException("Cannot extract text from applications.");
  }
  
    private boolean STORE_TEXT;
    private boolean STORE_THUMBNAIL;
    private boolean COMPRESSED;
    
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

    public void setCompressed(boolean b) {
        this.COMPRESSED = b;
    }

    public boolean isCompressed() {
        return this.COMPRESSED;
    }
  
  public static void main(String[] args) throws Exception {
      AppHandler handler = new AppHandler();
      Document doc = handler.getDocument(
              new File(args[1]));
      System.out.println(doc);
  }
}
