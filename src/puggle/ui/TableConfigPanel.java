/*
 * TableConfigPanel.java
 *
 * Created on 16 March 2007, 8:51
 */

package puggle.ui;

import java.io.File;
import javax.swing.JLabel;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import puggle.Indexer.IndexProperties;

/**
 *
 * @author  gvasil
 */
public class TableConfigPanel extends ResultsPanel {
    
    /**
     * Creates new form ClassicResultsPanel
     */
    public TableConfigPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Attribute", "Value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setTableHeader(null);
        jScrollPane1.setViewportView(table);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void executeFile(File file) {
        throw new NullPointerException("Method is not implemented.");
    }
    
    private void printCurrentHits() {
        throw new NullPointerException("Method is not implemented.");
    }
    
    private void printHit(Document doc, int score, JLabel titleLabel, JLabel scoreLabel,
            JLabel pathLabel, JLabel sizeLabel, JLabel iconLabel) {
            throw new NullPointerException("Method is not implemented.");
    }
    
    private void printCurrentFragments() {
        throw new NullPointerException("Method is not implemented.");
    }
    
    public void setResults(Query query, Hits hits) {
        throw new NullPointerException("Method is not implemented.");
    }
    
    public int getCurrentResultsNumber() {
        throw new NullPointerException("Method is not implemented.");
    }
    
    public int getTotalResultsNumber() {
        throw new NullPointerException("Method is not implemented.");
    }
    
    public boolean hasPreviousResults() {
        throw new NullPointerException("Method is not implemented.");
    }
    
    public boolean hasNextResults() {
        throw new NullPointerException("Method is not implemented.");
    }
    
    public boolean nextResults() {
        throw new NullPointerException("Method is not implemented.");
    }
    
    public boolean previousResults() {
        throw new NullPointerException("Method is not implemented.");
    }
    
    public void add(String attr, String value) {
        /* Append attribute and its value to the table */
    }
    
    private IndexProperties propertiesControl;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
    
}
