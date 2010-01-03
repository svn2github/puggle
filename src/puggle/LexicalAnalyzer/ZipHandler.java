/*
 * ZipHandler.java
 *
 * Created on 29 December 2009, 1:57
 */

package puggle.LexicalAnalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import puggle.Util.Util;

/**
 *
 * @author gvasil
 */
public class ZipHandler implements DocumentHandler {

  public Document getDocument(File f) throws DocumentHandlerException {

      // Open the ZIP file
      ZipInputStream is = null;
        try {
            is = new ZipInputStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e) {
            throw new DocumentHandlerException(
                    "File not found: "
                    + f.getAbsolutePath(), e);
      }

      Document doc = new Document();

      doc.add(new Field("filetype", "zip", Field.Store.YES,
              Field.Index.UN_TOKENIZED));
      
      String text = null;
      try {
          text = getText(is);
      }
      catch(IOException e) {
          throw new DocumentHandlerException("Cannot read the zip document", e);
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

      try {
          is.close();
      } catch (IOException ex) {
          throw new DocumentHandlerException(ex.getMessage());
      }
      
      return doc;
  }
  
  public String getText(Document doc) throws DocumentHandlerException {
      return doc.get("text");
  }
  
  private String getText(ZipInputStream is) throws IOException {
      FileHandler handler = new FileHandler(true, false); //store text, not thumbs

      StringBuffer str = new StringBuffer();

      File tmpDir = Util.createTempDir();
      String tmpDirPath = tmpDir.getPath();

      ZipEntry entry = null;
      while ((entry = is.getNextEntry()) != null) {
          String outFileName = null;
          if (entry.isDirectory()) {
              outFileName = tmpDirPath + File.separator + entry.getName();
              File dir = new File(outFileName);
              dir.mkdirs();
          }
          else {
              // unzipping archive
              outFileName = tmpDirPath + File.separator + entry.getName();
              OutputStream out = new FileOutputStream(outFileName);

              byte[] buf = new byte[1024];
              int len;
              while ((len = is.read(buf)) > 0) {
                  out.write(buf, 0, len);
              }

              out.close();
          }

          try {
              Document doc = handler.getDocument(new File(outFileName));
              if (doc != null) {
                  String s = handler.getText(doc);
                  str.append(s + "\n");
              }
          } catch (FileHandlerException ex) {
              //do nothing
          }
          
      }

      // delete unzipped files
      Util.deleteDir(tmpDir);

      return str.toString();
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
      ZipHandler handler = new ZipHandler();
      Document doc = handler.getDocument(
              //new File(args[1]));
              new File("C:\\gmlw.zip"));
      System.out.println(doc);
  }
}