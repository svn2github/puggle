/*
 * HTMLHandler.java
 *
 * Created on 4 September 2006, 1:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.lucene.document.Field;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.tidy.Tidy;

import java.io.InputStream;
import org.apache.lucene.document.Document;

/**
 *
 * @author gvasil
 */
public class HTMLHandler implements DocumentHandler {
    
    /** Creates a new instance of HTMLHandler */
    public HTMLHandler() {
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

        doc.add(new Field("filetype", "html", Field.Store.YES,
                Field.Index.UN_TOKENIZED));

        Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        org.w3c.dom.Document root = tidy.parseDOM(is, null);
        Element rawDoc = root.getDocumentElement();
        
        String title = getTitle(rawDoc);
        
        String text = getBody(rawDoc);
        /* Eat consecutive whitespace characters */
   //     text = text.replaceAll("\\s+", " ");
        
        if ((title != null) && (!title.equals(""))) {
            doc.add(new Field("title", title, Field.Store.YES, Field.Index.NO));
        }
        if ((text != null) && (!text.equals(""))) {
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

    /**
     * Gets the title text of the HTML document.
     * 
     * @rawDoc the DOM Element to extract title Node from
     * @return the title text
     */
    protected String getTitle(Element rawDoc) {
        if (rawDoc == null) {
            return null;
        }

        String title = "";
        
        NodeList children = rawDoc.getElementsByTagName("title");
        if (children.getLength() > 0) {
            Element titleElement = ((Element) children.item(0));
            Text text = (Text) titleElement.getFirstChild();
            if (text != null) {
                title = text.getData();
            }
        }
        return title;
    }

    /**
     * Gets the body text of the HTML document.
     * 
     * @rawDoc the DOM Element to extract body Node from
     * @return the body text
     */
    protected String getBody(Element rawDoc) {
        if (rawDoc == null) {
            return null;
        }

        String body = "";
        NodeList children = rawDoc.getElementsByTagName("body");
        if (children.getLength() > 0) {
            body = getText(children.item(0));
        }
        return body;
    }

    /**
     * Extracts text from the DOM node.
     *
     * @param node a DOM node
     * @return the text value of the node
     */
    protected String getText(Node node) {
        NodeList children = node.getChildNodes();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            switch (child.getNodeType()) {
                case Node.ELEMENT_NODE:
                    sb.append(getText(child));
                    sb.append(" ");
                    break;
                case Node.TEXT_NODE:
                    sb.append(((Text) child).getData());
                    break;
            }
        }
        return sb.toString();
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
        
        Tidy tidy = new Tidy();
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        org.w3c.dom.Document root = tidy.parseDOM(is, null);
        Element rawDoc = root.getDocumentElement();
        
        return getBody(rawDoc);*/
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

    public static void main(String args[]) throws Exception {
        HTMLHandler handler = new HTMLHandler();
        Document doc = handler.getDocument(new File(args[0]));
        System.out.println(doc);
    }
}
