/*
 * RTFHandler.java
 *
 * Created on 4 September 2006, 3:17
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
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 *
 * @author gvasil
 */
public class RTFHandler implements DocumentHandler {
    
    /** Creates a new instance of RTFHandler */
    public RTFHandler() {
    }
    
    public Document getDocument(File f) throws DocumentHandlerException {

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

      doc.add(new Field("filetype", "rtf", Field.Store.YES,
              Field.Index.UN_TOKENIZED));
      
      String text = null;
      DefaultStyledDocument styledDoc = new DefaultStyledDocument();
      try {
          text = getText(is);
          /* Eat consecutive whitespace characters */
   //       text = text.replaceAll("\\s+", " ");
      }
      catch (IOException e) {
          throw new DocumentHandlerException(
                  "Cannot extract text from a RTF document", e);
      }
      catch (BadLocationException e) {
          throw new DocumentHandlerException(
                  "Cannot extract text from a RTF document", e);
      }
      
      if (text != null && (!text.equals(""))) {
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
    
    public String getText(Document doc) throws DocumentHandlerException {
        return doc.get("text");
        /*
        File file = new File(doc.get("path"));
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {
            throw new DocumentHandlerException(
                    "File not found: "
                    + file.getAbsolutePath(), e);
        }
        
        String str = null;
        try {
            str = getText(is);
        }
        catch (IOException e) {
            throw new DocumentHandlerException(
                    "Cannot extract text from a RTF document", e);
        }
        catch (BadLocationException e) {
            throw new DocumentHandlerException(
                    "Cannot extract text from a RTF document", e);
        }
        
        return str;*/
    }
    
    private String getText(InputStream is) throws IOException, BadLocationException {
      String bodyText = null;
      DefaultStyledDocument styledDoc = new DefaultStyledDocument();
      
      new RTFEditorKit().read(is, styledDoc, 0);
      bodyText = styledDoc.getText(0, styledDoc.getLength());
      
      return bodyText;
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
        RTFHandler handler = new RTFHandler();
        Document doc = handler.getDocument(new File(args[0]));
        System.out.println(doc);
    }
}
