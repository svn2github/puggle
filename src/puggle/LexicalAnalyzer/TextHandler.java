/*
 * TextHandler.java
 *
 * Created on 2 Σεπτέμβριος 2006, 9:20 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import java.util.StringTokenizer;

/**
 *
 * @author gvasil
 */
public class TextHandler implements DocumentHandler {

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
      
      try {
          doc.add(new Field("path", f.getCanonicalPath(),
                  Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field("size", String.valueOf(f.length()),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
      } catch (IOException e) {
          throw new DocumentHandlerException(e.getMessage());
      }
      
      doc.add(new Field("filetype", "txt", Field.Store.YES,
              Field.Index.UN_TOKENIZED));
      doc.add(new Field("last modified", String.valueOf(f.lastModified()),
              Field.Store.YES, Field.Index.NO));
      
      String text = null;
      try {
          text = getText(is);
      }
      catch(IOException e) {
          throw new DocumentHandlerException("Cannot read the text document", e);
      }
      
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
      catch(IOException e) {
          throw new DocumentHandlerException("Cannot read the text document", e);
      }
      
      return str;*/
  }
  
  private String getText(InputStream is) throws IOException {
      StringBuffer str = new StringBuffer();
      
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String line = null;
      while ((line = br.readLine()) != null) {
          line = line.replaceAll("\\s+", " ");
          str.append(line + "\n");
      }
      br.close();
      
      return str.toString();
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
      TextHandler handler = new TextHandler();
      Document doc = handler.getDocument(
              new File("C:\\Documents and Settings\\gvasil\\Τα έγγραφά μου\\My eBooks\\Misc\\The Notebooks of Leonardo Da Vinci, Complete.txt"));
      System.out.println(doc);
  }
}