/*
 * PDFHandler.java
 *
 * Created on 2 September 2006, 1:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.encryption.DocumentEncryption;
import org.pdfbox.exceptions.CryptographyException;
import org.pdfbox.exceptions.InvalidPasswordException;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.pdfbox.util.PDFTextStripper;
import puggle.ui.ImageControl;

/**
 *
 * @author gvasil
 */
public class PDFHandler implements DocumentHandler {

  public static String password = "-password";


  public Document getDocument(File f) throws DocumentHandlerException {
      String path = null;
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
          path = f.getCanonicalPath();
      } catch (IOException e) {
          throw new DocumentHandlerException(e.getMessage());
      }

      String fileName = f.getName();
      int dotIndex = fileName.lastIndexOf(".");
      String name = fileName.substring(0, dotIndex).toLowerCase();

      doc.add(new Field("filename", name, Field.Store.YES,
              Field.Index.TOKENIZED));
      doc.add(new Field("filetype", "pdf", Field.Store.YES,
              Field.Index.UN_TOKENIZED));
      
      COSDocument cosDoc = null;
      try {
          cosDoc = parseDocument(is);
      }
      catch (IOException e) {
          closeCOSDocument(cosDoc);
          return doc;
          //throw new DocumentHandlerException("Cannot parse PDF document", e);
      }

      // decrypt the PDF document, if it is encrypted
      try {
          if (cosDoc.isEncrypted()) {
              DocumentEncryption decryptor = new DocumentEncryption(cosDoc);
              decryptor.decryptDocument(password);
          }
      }
      catch (CryptographyException e) {
          closeCOSDocument(cosDoc);
          //throw new DocumentHandlerException("Cannot decrypt PDF document", e);
          return doc;
      }
      catch (InvalidPasswordException e) {
          closeCOSDocument(cosDoc);
          //throw new DocumentHandlerException("Cannot decrypt PDF document", e);
          return doc;
      }
      catch (IOException e) {
          closeCOSDocument(cosDoc);
          //throw new DocumentHandlerException("Cannot decrypt PDF document", e);
          return doc;
      }
      
      if (this.STORE_THUMBNAIL) {
          byte[] bytes = null;
          try {
              ImageControl imgctl = ImageControl.getImageControl();
              BufferedImage img = imgctl.getPDFScaledCover(path, 80);
              bytes = imgctl.toByteArray(img);
          } catch (Exception ex) {
              bytes = null;
          }

          if (bytes != null) {
              doc.add(new Field("thumbnail", bytes, Field.Store.YES));
          }
      }

      // extract PDF document's textual content
      String text = null;
      try {
          PDFTextStripper stripper = new PDFTextStripper();
          text = stripper.getText(new PDDocument(cosDoc));
          
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
      catch (IOException e) {
          closeCOSDocument(cosDoc);
          //throw new DocumentHandlerException("Cannot parse PDF document", e);
          return doc;
      }

      //Now release everything for GC
      text = null;
      text = new String();
      
      // extract PDF document's meta-data
      PDDocument pdDoc = null;
      try {
          pdDoc = new PDDocument(cosDoc);
          PDDocumentInformation docInfo = pdDoc.getDocumentInformation();
          String author   = docInfo.getAuthor();
          String title    = docInfo.getTitle();
          String keywords = docInfo.getKeywords();
          String summary  = docInfo.getSubject();
          if ((author != null) && (!author.equals(""))) {
              doc.add(new Field("author", author, Field.Store.YES, Field.Index.NO));
          }
          if ((title != null) && (!title.equals(""))) {
              doc.add(new Field("title", title, Field.Store.YES, Field.Index.NO));
          }
          if ((keywords != null) && (!keywords.equals(""))) {
              doc.add(new Field("keywords", keywords, Field.Store.YES, Field.Index.NO));
          }
          if ((summary != null) && (!summary.equals(""))) {
              doc.add(new Field("summary", summary, Field.Store.YES, Field.Index.NO));
          }
      } catch (Exception e) {
          System.err.println("Cannot get PDF document meta-data: "
                  + e.getMessage());
      } finally {
          closeCOSDocument(cosDoc);
          closePDDocument(pdDoc);
      }
      
      return doc;
  }

  private static COSDocument parseDocument(InputStream is) throws IOException {
      PDFParser parser = new PDFParser(is);
      parser.parse();
      return parser.getDocument();
  }
  
  private void closeCOSDocument(COSDocument cosDoc) {
      if (cosDoc != null) {
          try {
              cosDoc.close();
          }
          catch (IOException e) {
              // eat it, what else can we do?
          }
      }
  }
  
  private void closePDDocument(PDDocument pdDoc) {
      if (pdDoc != null) {
          try {
              pdDoc.close();
          }
          catch (IOException e) {
              // eat it, what else can we do?
          }
      }
  }
  
  public String getText(Document doc) throws DocumentHandlerException {
      return doc.get("text");
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
      PDFHandler handler = new PDFHandler();
      Document doc =
              handler.getDocument(new File(args[1]));
      System.out.println(doc);
  }
}