/*
 * RarHandler.java
 *
 * Created on 02 January 2010, 8:19
 */

package puggle.LexicalAnalyzer;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import puggle.Util.Util;

/**
 *
 * @author gvasil
 */
public class RarHandler implements DocumentHandler {

  public Document getDocument(File f) throws DocumentHandlerException {
      Archive ar = null;
      
      try {
          ar = new Archive(f);
      } catch (RarException ex) {
          throw new DocumentHandlerException(ex.getMessage());
      } catch (IOException ex) {
          throw new DocumentHandlerException(ex.getMessage());
      }

      if (ar.isEncrypted() == true) {
          throw new DocumentHandlerException("Archive is encrypted.");
      }

      Document doc = new Document();

      doc.add(new Field("filetype", "rar", Field.Store.YES,
              Field.Index.UN_TOKENIZED));
      
      String text = null;
      try {
          text = getText(ar);
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

      /*try {
          is.close();
      } catch (IOException ex) {
          throw new DocumentHandlerException(ex.getMessage());
      }*/
      
      return doc;
  }
  
  public String getText(Document doc) throws DocumentHandlerException {
      return doc.get("text");
  }
  
  private String getText(Archive archive) throws IOException {
      FileHandler handler = new FileHandler(true, false); //store text, not thumbs

      File tmpDir = Util.createTempDir();
      String tmpDirPath = tmpDir.getPath();

      List<FileHeader> fileList = archive.getFileHeaders();

      /* build directory tree */
      /* XXX TODO fix this */
      Iterator i = fileList.iterator();
      while (i.hasNext()) {
          FileHeader fhd = (FileHeader) i.next();

          if (fhd.isDirectory()) {
              String outFileName = tmpDirPath + File.separator + fhd.getFileNameString();
              File dir = new File(outFileName);
              dir.mkdirs();
          }
      }

      StringBuffer str = new StringBuffer();

      i = fileList.iterator();
      while (i.hasNext()) {
          FileHeader fhd = (FileHeader) i.next();

          if (fhd.isDirectory()) {
              continue;
          }

          String outFileName = null;

          // unrar archive
          outFileName = tmpDirPath + File.separator + fhd.getFileNameString();
          OutputStream out = new FileOutputStream(outFileName);
          try {
              archive.extractFile(fhd, out);
          } catch (RarException ex) {
              throw new IOException(ex.getMessage());
          }
          out.close();

          try {
              Document doc = handler.getDocument(new File(outFileName));
              if (doc != null) {
                  String s = handler.getText(doc);
                  str.append(s + "\n");
              }
          } catch (FileHandlerException ex) {
              //do nothing...
          }
          
      }

      // delete unrar'ed files
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

    // XXX TODO: test this function with a huge rar archive to see if
    // javolution.jar is need.
  public static void main(String[] args) throws Exception {
      RarHandler handler = new RarHandler();
      Document doc = handler.getDocument(
              //new File(args[1]));
              new File("C:\\test.rar"));
      System.out.println(doc);
  }
}