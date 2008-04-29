/*
 * ClassicResultsPanel.java
 *
 * Created on 16 March 2007, 8:51
 */

package puggle.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.text.html.HTMLEditorKit;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import puggle.Indexer.IndexProperties;
import puggle.LexicalAnalyzer.FileHandler;
import puggle.LexicalAnalyzer.FileHandlerException;
import puggle.Util.Util;

/**
 *
 * @author  gvasil
 */
public class ClassicResultsPanel extends ResultsPanel {
    
    /**
     * Creates new form ClassicResultsPanel
     */
    public ClassicResultsPanel() {
        this.imageControl = ImageControl.getImageControl();
        
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        resultPanel1 = new puggle.ui.ClassicResultPanel();
        resultPanel2 = new puggle.ui.ClassicResultPanel();
        resultPanel3 = new puggle.ui.ClassicResultPanel();
        resultPanel4 = new puggle.ui.ClassicResultPanel();
        resultPanel5 = new puggle.ui.ClassicResultPanel();
        resultPanel6 = new puggle.ui.ClassicResultPanel();
        resultPanel7 = new puggle.ui.ClassicResultPanel();
        resultPanel8 = new puggle.ui.ClassicResultPanel();
        resultPanel9 = new puggle.ui.ClassicResultPanel();
        resultPanel10 = new puggle.ui.ClassicResultPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setLayout(new java.awt.GridLayout(10, 0, 0, 1));

        mainPanel.setAlignmentX(0.0F);
        mainPanel.setAlignmentY(0.0F);
        mainPanel.setMaximumSize(new java.awt.Dimension(10, 10));
        mainPanel.add(resultPanel1);

        mainPanel.add(resultPanel2);

        mainPanel.add(resultPanel3);

        mainPanel.add(resultPanel4);

        mainPanel.add(resultPanel5);

        mainPanel.add(resultPanel6);

        mainPanel.add(resultPanel7);

        mainPanel.add(resultPanel8);

        mainPanel.add(resultPanel9);

        mainPanel.add(resultPanel10);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void printCurrentHits() {
        Document doc;
        Icon icon;
        sun.awt.shell.ShellFolder sf;
        String title = null, path = null, folder = null;
        long size;
        int score;
        
        try {
            if (this.currHits < this.totalHits) {
                printHit(this.hits.doc(currHits),
                        (int)Math.ceil(this.hits.score(currHits) * 10) / 2,
                        resultPanel1);
            } else {
                clearResultPanel(resultPanel1);
            }
            
            if (this.currHits + 1 < this.totalHits) {
                printHit(this.hits.doc(currHits + 1),
                        (int)Math.ceil(this.hits.score(currHits + 1) * 10) / 2,
                        resultPanel2);
            } else {
                clearResultPanel(resultPanel2);
            }

            if (currHits + 2 < totalHits) {
                printHit(this.hits.doc(currHits + 2),
                        (int)Math.ceil(this.hits.score(currHits + 2) * 10) / 2,
                        resultPanel3);
            } else {
                clearResultPanel(resultPanel3);
            }
            
            if (currHits + 3 < totalHits) {
                printHit(this.hits.doc(currHits + 3),
                        (int)Math.ceil(this.hits.score(currHits + 3) * 10) / 2,
                        resultPanel4);
            } else {
                clearResultPanel(resultPanel4);
            }
            
            if (currHits + 4 < totalHits) {
                printHit(this.hits.doc(currHits + 4),
                        (int)Math.ceil(this.hits.score(currHits + 4) * 10) / 2,
                        resultPanel5);
            } else {
                clearResultPanel(resultPanel5);
            }
            
            if (currHits + 5 < totalHits) {
                printHit(this.hits.doc(currHits + 5),
                        (int)Math.ceil(this.hits.score(currHits + 5) * 10) / 2,
                        resultPanel6);
            } else {
                clearResultPanel(resultPanel6);
            }
            
            if (currHits + 6 < totalHits) {
                printHit(this.hits.doc(currHits + 6),
                        (int)Math.ceil(this.hits.score(currHits + 6) * 10) / 2,
                        resultPanel7);
            } else {
                clearResultPanel(resultPanel7);
            }
                
            if (currHits + 7 < totalHits) {
                printHit(this.hits.doc(currHits + 7),
                        (int)Math.ceil(this.hits.score(currHits + 7) * 10) / 2,
                        resultPanel8);
            } else {
                clearResultPanel(resultPanel8);
            }
            
            if (currHits + 8 < totalHits) {
                printHit(this.hits.doc(currHits + 8),
                        (int)Math.ceil(this.hits.score(currHits + 8) * 10) / 2,
                        resultPanel9);
            } else {
                clearResultPanel(resultPanel9);
            }
            
            if (currHits + 9 < totalHits) {
                printHit(this.hits.doc(currHits + 9),
                        (int)Math.ceil(this.hits.score(currHits + 9) * 10) / 2,
                        resultPanel10);
            } else {
                clearResultPanel(resultPanel10);
            }

//            JScrollBar bar = this.scrollPane.getVerticalScrollBar();
//            bar.setValue(bar.getMinimum());
            
            //pack();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void printHit(Document doc, int score, ClassicResultPanel resultPanel) {
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
        resultPanel.setScoreIcon(this.imageControl.getStarsIcon(score));
        String folder = path.substring(0, path.lastIndexOf(File.separatorChar) + 1);
        resultPanel.setPath(folder);
        resultPanel.getPathLabel().setToolTipText(folder);
        resultPanel.setSize(Util.adjustSize(size));
        

        String f = doc.get("fragment");
        if (f == null) {
            f = Util.getFragment(doc, query);
            doc.removeField("text");
            doc.add(new Field("fragment", f, Field.Store.YES,
                    Field.Index.NO, Field.TermVector.NO));
        }
        resultPanel.setFragment(f);
        
        try {
            ImageIcon icon = FileHandler.getThumbnail(doc);
            resultPanel.getIconLabel().setText("");
            resultPanel.setIcon(icon);
        } catch (FileHandlerException e) {
            resultPanel.getIconLabel().setForeground(Color.RED);
            resultPanel.getIconLabel().setText("File not found");
            resultPanel.getIconLabel().setIcon(null);
        }
    }
    
    public void clearResultPanel(ClassicResultPanel resultPanel) {
        resultPanel.setTitle(""); resultPanel.getTitleLabel().setToolTipText("");
        resultPanel.setIcon(null); resultPanel.setPath("");
        resultPanel.getPathLabel().setToolTipText(""); resultPanel.setSize("");
        resultPanel.setFragment(""); 
        resultPanel.getIconLabel().setText("");
        resultPanel.setScoreIcon(null);
        resultPanel.setFragment("");
    }
    

    
    public void setResults(Query query, Hits hits, IndexProperties properties) {
        this.hits = hits;
        this.query = query;
        this.currHits = 0;
        this.totalHits = hits.length();
        this.indexProperties = properties;
        this.printCurrentHits();
    }
    
    public int getCurrentResultsNumber() {
        return (this.currHits);
    }
    
    public void setCurrentResultsNumber(int number) {
        if (number < 0 || number >= this.totalHits) {
            throw new IllegalArgumentException("Number:" +number + " Total:" +this.totalHits);
        }
        this.currHits = number;
    }
    
    public int getTotalResultsNumber() {
        return (this.totalHits);
    }
    
    public boolean hasPreviousResults() {
        return (this.currHits - 10 >= 0);
    }
    
    public boolean hasNextResults() {
        return (this.currHits + 10 < this.totalHits);
    }
    
    public boolean nextResults() {
        if (this.hasNextResults()) {
            this.currHits += 10;
            this.printCurrentHits();
            return true;
        }
        return false;
    }
    
    public boolean previousResults() {
        if (this.hasPreviousResults()) {
            this.currHits -= 10;
            this.printCurrentHits();
            return true;
        }
        return false;
    }
    
    private IndexProperties indexProperties;
    
    private ImageControl imageControl;
    private Hits hits;
    private Query query;
    
    private int currHits;
    private int totalHits;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private puggle.ui.ClassicResultPanel resultPanel1;
    private puggle.ui.ClassicResultPanel resultPanel10;
    private puggle.ui.ClassicResultPanel resultPanel2;
    private puggle.ui.ClassicResultPanel resultPanel3;
    private puggle.ui.ClassicResultPanel resultPanel4;
    private puggle.ui.ClassicResultPanel resultPanel5;
    private puggle.ui.ClassicResultPanel resultPanel6;
    private puggle.ui.ClassicResultPanel resultPanel7;
    private puggle.ui.ClassicResultPanel resultPanel8;
    private puggle.ui.ClassicResultPanel resultPanel9;
    // End of variables declaration//GEN-END:variables
    
}
