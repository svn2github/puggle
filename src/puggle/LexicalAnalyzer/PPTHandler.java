/*
 * PPTHandler.java
 *
 * Created on 4 Σεπτέμβριος 2006, 10:50 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;

import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.util.LittleEndian;

/**
 *
 * @author gvasil
 */
public class PPTHandler implements DocumentHandler, POIFSReaderListener {
    
    /** Writer to store parsed content. */
    private ByteArrayOutputStream writer;
    
    public PPTHandler() {
        this.writer = new ByteArrayOutputStream();
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
        try {
            doc.add(new Field("path", f.getCanonicalPath(),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.add(new Field("size", String.valueOf(f.length()),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
        } catch (IOException e) {
            throw new DocumentHandlerException(e.getMessage());
        }
        
        doc.add(new Field("filetype", "ppt", Field.Store.YES,
                Field.Index.UN_TOKENIZED));
        doc.add(new Field("last modified", String.valueOf(f.lastModified()),
              Field.Store.YES, Field.Index.NO));
        
        String text = null;
        try {
            text = getText(is);
            /* Eat consecutive whitespace characters */
     //       text = text.replaceAll("\\s+", " ");
        } catch (IOException e) {
            text = null;
        }
        
        if ((text != null) && (text.trim().length() > 0)) {
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

    public void processPOIFSReaderEvent(POIFSReaderEvent event) {
        if (!event.getName().equalsIgnoreCase("PowerPoint Document")) {
            return;
        }
        
        DocumentInputStream input = event.getStream();
        byte[] buffer = null;
        try {
            buffer = new byte[input.available()];
            input.read(buffer, 0, input.available());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        for (int i = 0; i < buffer.length - 20; i++) {
            long type = LittleEndian.getUShort(buffer, i + 2);
            long size = LittleEndian.getUInt(buffer, i + 4);
            if (type == 4008) {
                writer.write(' ');
                writer.write(buffer, i + 4 + 1, (int) size +3);
                i = i + 4 + 1 + (int) size - 1;
            }
        }
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
        catch (Exception e) {
            throw new DocumentHandlerException(
                    "Cannot extract text from a ppt document", e);
        }
        
        return str;*/
    }
    
    private String getText(InputStream is) throws IOException {
        POIFSReader reader = new POIFSReader();
        reader.registerListener(this);
        try {
            reader.read(is);
        } catch (IndexOutOfBoundsException e) {
            throw new IOException(e.toString());
        }
        return new String(writer.toByteArray());
    }
    
    /*
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
        
        doc.add(new Field("filetype", "ppt", Field.Store.YES,
                Field.Index.UN_TOKENIZED));
        doc.add(new Field("last modified", String.valueOf(f.lastModified()),
              Field.Store.YES, Field.Index.NO));
        
        String text = null, notes = null;
        try {
            PowerPointExtractor ppt = new PowerPointExtractor(is);
            text = ppt.getText();
            notes = ppt.getNotes();
        } catch (IOException e) {
            text = notes = null;
        } catch (ArrayIndexOutOfBoundsException e) {
            text = notes = null;
        }
        
        if ((text != null) && (text.trim().length() > 0)) {
            doc.add(new Field("content", text, Field.Store.NO,
                    Field.Index.TOKENIZED, Field.TermVector.WITH_OFFSETS));
        }    
        return doc;
    }
    */
    
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
        PPTHandler handler = new PPTHandler();
        Document doc = handler.getDocument(new File("C:\\Documents and Settings\\gvasil\\Τα έγγραφά μου\\My eBooks\\Comic\\Αρκάς\\Ο Κόκκορας\\Αδιέξοδα.ppt"));
        System.out.println(doc);
    }
}
