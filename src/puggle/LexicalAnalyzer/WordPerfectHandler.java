/*
 * WordPerfectHandler.java
 *
 * Created on 16 January 2010, 1:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.File;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 *
 * @author gvasil
 */
public class WordPerfectHandler implements DocumentHandler {

  public Document getDocument(File f) throws DocumentHandlerException {

      Document doc = new Document();

      doc.add(new Field("filetype", "wpd", Field.Store.YES,
              Field.Index.UN_TOKENIZED));
      
      String text = getText(f);
      
      if (text != null && text.trim().length() > 0) {
          doc.add(new Field("content", text, Field.Store.NO,
                  Field.Index.TOKENIZED, Field.TermVector.WITH_OFFSETS));
            if (this.STORE_TEXT) {
                doc.add(new Field("text", text, Field.Store.YES,
                        Field.Index.NO, Field.TermVector.NO));
            } else {
                doc.add(new Field("text", "", Field.Store.YES,
                        Field.Index.NO, Field.TermVector.NO));
            }
      }
      
      return doc;
  }
  
  public String getText(Document doc) {
      return doc.get("text");
  }

  private String getText(File file) throws DocumentHandlerException {
      //XXX TODO: fix this!
      return "";
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
      WordPerfectHandler handler = new WordPerfectHandler();
      Document doc = handler.getDocument(
              new File("C:\\test.wpd"));
      System.out.println(doc);
  }
}