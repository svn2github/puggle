/*
 * ListResultsPanel.java
 *
 * Created on 02 January 2010, 12:32
 */

package puggle.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
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
public class ListResultsPanel extends ResultsPanel {
    private IndexProperties indexProperties;
    
    /**
     * Creates new form ClassicResultsPanel
     */
    public ListResultsPanel() {
        this.imageControl = ImageControl.getImageControl();
        
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainScrollPane = new javax.swing.JScrollPane();
        resultsList = new javax.swing.JList();

        setPreferredSize(new java.awt.Dimension(118, 99));

        mainScrollPane.addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                mainScrollPaneResized(evt);
            }
        });

        resultsList.setModel(new javax.swing.DefaultListModel());
        resultsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        resultsList.setCellRenderer(new JavaDocumentRenderer());
        resultsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultsListMouseClicked(evt);
            }
        });
        resultsList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                resultsListKeyTyped(evt);
            }
        });
        mainScrollPane.setViewportView(resultsList);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void mainScrollPaneResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_mainScrollPaneResized

}//GEN-LAST:event_mainScrollPaneResized

    private void resultsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultsListMouseClicked
        if (evt.getClickCount() == 2) {
            int index = this.resultsList.locationToIndex(evt.getPoint());
            Document doc = (Document)this.resultsList.getModel().getElementAt(index);

            if (doc != null) {
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                this.executeFile(new File(doc.get("path")));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_resultsListMouseClicked

    private void resultsListKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsListKeyTyped
        if (evt.getKeyChar() == '\n') {
            int index = this.resultsList.getSelectedIndex();
            Document doc = (Document)this.resultsList.getModel().getElementAt(index);

            if (doc != null) {
                setCursor(new Cursor(Cursor.WAIT_CURSOR));
                this.executeFile(new File(doc.get("path")));
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }//GEN-LAST:event_resultsListKeyTyped
    
    private void executeFile(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE,
                        this.imageControl.getErrorIcon());
        }
    }

    public class JavaDocumentRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean hasFocus) {
            JLabel label =
                    (JLabel)super.getListCellRendererComponent(list,
                    value,
                    index,
                    isSelected,
                    hasFocus);

            if (value instanceof Document) {
                Document doc = (Document)value;

                String path = doc.get("path");
                String path_portable = doc.get("path_portable");
                if (indexProperties.isPortable() && path_portable == null) {
                    path = path_portable = indexProperties.getFilesystemRoot() + path;
                    doc.add(new Field("path_portable", path_portable, Field.Store.YES, Field.Index.UN_TOKENIZED));
                    doc.removeField("path");
                    doc.add(new Field("path", path_portable, Field.Store.YES, Field.Index.UN_TOKENIZED));
                }
                if (indexProperties.isPortable()) {
                    path = path_portable;
                }

                label.setText(path);

                try {
                    ImageIcon icon = FileHandler.getDefaultThumbnail(doc, 16, 16);
                    label.setIcon(icon);
                } catch (FileHandlerException e) {
                    label.setIcon(null);
                }
            } else {
                // Clear old icon; needed in 1st release of JDK 1.2
                label.setIcon(null);
            }
            return(label);
        }
    }
    
    private void printCurrentHits() {
        if (rendererThread != null) {
            try {
                rendererThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }


        rendererThread = new Thread(
                new Runnable() {
            public void run() {
        javax.swing.DefaultListModel model =
                (javax.swing.DefaultListModel)resultsList.getModel();

        model.clear();

        model.setSize(RESULTS_PER_FRAME);

        for (int i = 0; i < RESULTS_PER_FRAME; i++) {
            if (currHits + i < totalHits) {
                try {
                    model.set(i, hits.doc(currHits + i));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                break;
            }
        }

        JScrollBar bar = mainScrollPane.getVerticalScrollBar();
        bar.setValue(bar.getMinimum());

            }
        });
        rendererThread.start();
    }
    
    public void setResults(Query query, Hits hits) {
        this.hits = hits;
        this.query = query;
        this.totalHits = hits.length();
        this.currHits = 0;
        this.printCurrentHits();
    }

    @Override
    public synchronized void setResults(Query query, Hits hits, IndexProperties properties) {
        this.hits = hits;
        this.query = query;
        this.totalHits = hits.length();
        this.currHits = 0;
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
    
    private ImageControl imageControl;
    private Hits hits;
    private Query query;
    
    private int currHits;
    private int totalHits;

    private final int RESULTS_PER_FRAME = 100;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JList resultsList;
    // End of variables declaration//GEN-END:variables
    
}
