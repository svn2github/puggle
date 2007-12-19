/*
 * FolderHandler.java
 *
 * Created on 14 Σεπτέμβριος 2006, 11:42 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 *
 * @author gvasil
 */
public class FolderHandler implements DocumentHandler {

    public Document getDocument(File file) throws DocumentHandlerException {
        Document doc = new Document();
        String path = null, filename = null;
        try {
            path = file.getCanonicalPath();
            filename = path.substring(path.lastIndexOf(File.separatorChar) + 1);
            
            doc.add(new Field("content", filename, Field.Store.NO,
                    Field.Index.TOKENIZED, Field.TermVector.YES));
            doc.add(new Field("path", path,
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field("size", String.valueOf(file.length()),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
        } catch (IOException e) {
            throw new DocumentHandlerException(e.getMessage());
        }
        
        String type = path.substring(path.lastIndexOf('.') + 1);
        doc.add(new Field("filetype", "", Field.Store.YES,
                Field.Index.UN_TOKENIZED));
        doc.add(new Field("last modified", String.valueOf(file.lastModified()),
                Field.Store.YES, Field.Index.NO));
        
        return doc;
    }
    
  public String getText(Document doc) throws DocumentHandlerException {
      return null;
  }
  
  private String getText(InputStream is) {
      return null;
  }
    
    public static void main(String[] args) throws Exception {
        FolderHandler handler = new FolderHandler();
        Document doc = handler.getDocument(
                new File("C:\\Documents and Settings\\gvasil\\Τα έγγραφά μου\\"));
        System.out.println(doc);
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
}
