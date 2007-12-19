/*
 * DocumentHandler.java
 *
 * Created on 2 Σεπτέμβριος 2006, 1:41 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.File;
import org.apache.lucene.document.Document;

/**
 *
 * @author gvasil
 */
public interface DocumentHandler {
    
    /**
     * Creates a Lucene Document from an InputStream.
     * This method can return <code>null</code>.
     * 
     * @param is the InputStream to convert to a Document
     * @return a ready-to-index instance of Document
     */
    
    void setStoreText(boolean b);
    
    boolean getStoreText();
    
    void setStoreThumbnail(boolean b);
    
    boolean getStoreThumbnail();
    
    Document getDocument(File f) throws DocumentHandlerException;
    
    String getText(Document doc) throws DocumentHandlerException;
}



