/*
 * MP3Handler.java
 *
 * Created on 8 September 2006, 2:35
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
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.ID3v1;

/**
 *
 * @author gvasil
 */
public class MP3Handler implements DocumentHandler {
    
    /** Creates a new instance of MP3Handler */
    public MP3Handler() {
    }
    
  public Document getDocument(File f) throws DocumentHandlerException {
        MP3File mp3;
        
        try {
            mp3 = new MP3File(f);
        } catch (TagException ex) {
            throw new DocumentHandlerException(
                    "Tag error: "
                    + f.getAbsolutePath(), ex);
        } catch (IOException ex) {
            throw new DocumentHandlerException(
                    "IO error: "
                    + f.getAbsolutePath(), ex);
        } catch (ArrayIndexOutOfBoundsException ex) {
            mp3 = null;
        } catch (StringIndexOutOfBoundsException ex) {
            mp3 = null;
        }

      Document doc = new Document();
      
      try {
          String path = f.getCanonicalPath();
          String filename = path.substring(path.lastIndexOf(File.separatorChar) + 1);
          
          doc.add(new Field("content", filename, Field.Store.NO,
                  Field.Index.TOKENIZED, Field.TermVector.YES));

          doc.add(new Field("filename", f.getName(), Field.Store.YES,
                  Field.Index.TOKENIZED));
          doc.add(new Field("path", path,
                  Field.Store.YES, Field.Index.UN_TOKENIZED));
          doc.add(new Field("size", String.valueOf(f.length()),
                    Field.Store.YES, Field.Index.UN_TOKENIZED));
      } catch (IOException e) {
          throw new DocumentHandlerException(e.getMessage());
      }
      
      doc.add(new Field("filetype", "mp3", Field.Store.YES,
              Field.Index.UN_TOKENIZED));
      doc.add(new Field("last modified", String.valueOf(f.lastModified()),
              Field.Store.YES, Field.Index.NO));
      
      ID3v1 id3v1 = null;
      if (mp3 != null) {
          id3v1 = mp3.getID3v1Tag();
      }

      if (id3v1 != null) {
          String artist = id3v1.getArtist();
          doc.add(new Field("content", artist, Field.Store.NO,
                  Field.Index.TOKENIZED, Field.TermVector.YES));
          doc.add(new Field("artist", artist, Field.Store.YES, Field.Index.UN_TOKENIZED));
          
          String title = id3v1.getTitle();
          doc.add(new Field("content", title, Field.Store.NO,
                  Field.Index.TOKENIZED, Field.TermVector.YES));
          doc.add(new Field("title", title, Field.Store.YES, Field.Index.UN_TOKENIZED));
          
          String album = id3v1.getAlbum();
          doc.add(new Field("content", album, Field.Store.NO,
                  Field.Index.TOKENIZED, Field.TermVector.YES));
          doc.add(new Field("album", album, Field.Store.YES, Field.Index.UN_TOKENIZED));
          
//          Byte genre = id3v1.getGenre();
//          doc.add(new Field("content", genre, Field.Store.NO, Field.Index.TOKENIZED));
//          doc.add(new Field("genre", genre, Field.Store.YES, Field.Index.UN_TOKENIZED));
          
          String year = id3v1.getYear();
          doc.add(new Field("content", album, Field.Store.NO,
                  Field.Index.TOKENIZED, Field.TermVector.YES));
          doc.add(new Field("year", year, Field.Store.YES, Field.Index.UN_TOKENIZED));
      }
      
      return doc;
  }
  
  public String getText(Document doc) {
      return null;
  }
  
  private String getText(InputStream is) {
      return null;
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
      MP3Handler handler = new MP3Handler();
      Document doc = handler.getDocument(
              new File(args[1]));
      System.out.println(doc);
  }
}
