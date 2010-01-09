/*
 * TinyResultsPanel.java
 *
 * Created on 22 December 2007, 2:07
 */

package puggle.ui;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import puggle.Indexer.IndexProperties;
import puggle.LexicalAnalyzer.FileHandler;
import puggle.LexicalAnalyzer.FileHandlerException;

/**
 *
 * @author  gvasil
 */
public class TinyResultsPanel extends ResultsPanel {
    
    /** Creates new form TinyResultsPanel */
    public TinyResultsPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridLayout(this.RESULTS_PER_FRAME, 1, 0, 1));
    }// </editor-fold>//GEN-END:initComponents
    
    private void printCurrentHits() {
        if (rendererThread != null) {
            try {
                rendererThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        this.removeAll();
        //this.updateUI();

        rendererThread = new Thread(
                new Runnable() {
            public void run() {
                for (int i=0; i < RESULTS_PER_FRAME; ++i) {
                    TinyResultPanel resultPanel = new puggle.ui.TinyResultPanel();

                    if (currHits + i < totalHits) {
                        add(resultPanel);
                        try {
                            printHit(hits.doc(currHits + i),
                                    (int) Math.ceil(hits.score(currHits + i) * 10) / 2,
                                    resultPanel);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
            }
        });
        rendererThread.start();
    }
    
    private void printHit(Document doc, int score, TinyResultPanel resultPanel) {
        String title = doc.get("title");
        String path = doc.get("path");
        String path_portable = doc.get("path_portable");
        if (this.indexProperties.isPortable() && path_portable == null) {
            path = path_portable = this.indexProperties.getFilesystemRoot() + path;
            doc.add(new Field("path_portable", path_portable, Field.Store.YES, Field.Index.UN_TOKENIZED));
            doc.removeField("path");
            doc.add(new Field("path", path_portable, Field.Store.YES, Field.Index.UN_TOKENIZED));
        }
        if (this.indexProperties.isPortable()) {
            path = path_portable;
        }
        long size = Long.parseLong(doc.get("size"));
        if (title == null || title.trim().compareTo("") == 0) {
            title = path.substring(path.lastIndexOf(File.separatorChar) + 1);
        }
        resultPanel.setTitle(title);
        resultPanel.getTitleLabel().setToolTipText(path);
        String folder = path.substring(0, path.lastIndexOf(File.separatorChar) + 1);
        resultPanel.setPath(folder);
        resultPanel.getPathLabel().setToolTipText(folder);
        
        try {
            ImageIcon icon = FileHandler.getThumbnail(doc, ICON_DIMENSION, ICON_DIMENSION);
            resultPanel.getIconLabel().setText("");
            resultPanel.setIcon(icon);
        } catch (FileHandlerException e) {
            resultPanel.getIconLabel().setForeground(Color.RED);
            resultPanel.getIconLabel().setText("File not found");
            resultPanel.getIconLabel().setIcon(null);
        }
    }
    
    public void clearResultPanel(TinyResultPanel resultPanel) {
        resultPanel.setTitle(""); resultPanel.getTitleLabel().setToolTipText(null);
        resultPanel.setPath(""); resultPanel.setIcon(null);
        resultPanel.getPathLabel().setToolTipText(null);
    }
    
    @Override
    public void setResults(Query query, Hits hits, IndexProperties properties) {
        this.hits = hits;
        this.query = query;
        this.currHits = 0;
        this.totalHits = hits.length();
        this.indexProperties = properties;
        this.printCurrentHits();
    }
    
    @Override
    public int getCurrentResultsNumber() {
        return (this.currHits);
    }
    
    @Override
    public void setCurrentResultsNumber(int number) {
        if (number < 0 || number >= this.totalHits) {
            throw new IllegalArgumentException("Number:" +number + " Total:" +this.totalHits);
        }
        this.currHits = number;
    }
    
    @Override
    public int getTotalResultsNumber() {
        return (this.totalHits);
    }
    
    @Override
    public boolean hasPreviousResults() {
        return (this.currHits - this.RESULTS_PER_FRAME >= 0);
    }
    
    @Override
    public boolean hasNextResults() {
        return (this.currHits + this.RESULTS_PER_FRAME < this.totalHits);
    }
    
    @Override
    public boolean nextResults() {
        if (this.hasNextResults()) {
            this.currHits += this.RESULTS_PER_FRAME;
            this.printCurrentHits();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean previousResults() {
        if (this.hasPreviousResults()) {
            this.currHits -= this.RESULTS_PER_FRAME;
            this.printCurrentHits();
            return true;
        }
        return false;
    }

    @Override
    public int getResultsNumberPerFrame() {
        return this.RESULTS_PER_FRAME;
    };

    private Thread rendererThread;
    
    private IndexProperties indexProperties;
        
    private ImageControl imageControl;
    private Hits hits;
    private Query query;
    
    private int currHits;
    private int totalHits;

    private final int ICON_DIMENSION = 32;
    private final int RESULTS_PER_FRAME = 20;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
