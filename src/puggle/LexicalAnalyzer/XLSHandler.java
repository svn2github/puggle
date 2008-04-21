/*
 * XLSHandler.java
 *
 * Created on 14 September 2006, 6:49 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author gvasil
 */
public class XLSHandler implements DocumentHandler {
    
    /** Creates a new instance of XLSHandler */
    public XLSHandler() {
    }
    
    public Document getDocument(File f) throws DocumentHandlerException {
        InputStream is = null;
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e) {
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
        
        doc.add(new Field("filetype", "xls", Field.Store.YES,
                Field.Index.UN_TOKENIZED));
        doc.add(new Field("last modified", String.valueOf(f.lastModified()),
                Field.Store.YES, Field.Index.NO));

        String text = null;
        try {
            text = getText(is);
            /* Eat consecutive whitespace characters */
   //         text = text.replaceAll("\\s+", " ");
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
        POIFSFileSystem fs;
        HSSFWorkbook workbook;
        
        fs = new POIFSFileSystem(is);
        workbook = new HSSFWorkbook(fs);

        String text = new String();
        
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            HSSFSheet sheet = workbook.getSheetAt(i);
            
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                
                Iterator cells = row.cellIterator();
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            String num = Double.toString(cell.getNumericCellValue());
                            text += num + " ";
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            String string = cell.getRichStringCellValue().getString();
                            text += string + " ";
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            String formula = Double.toString(cell.getNumericCellValue());
                            text += formula + " ";
                            break;
                        default: // skip
                    }
                }
                text += "\n";
            }
        }
        return text;
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
        XLSHandler handler = new XLSHandler();
        Document doc =
                handler.getDocument(new File("C:\\Documents and Settings\\gvasil\\Τα έγγραφά μου\\grades.xls"));
        System.out.println(doc);
    }
}
