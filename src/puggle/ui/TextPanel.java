/*
 * ClassicResultsPanel.java
 *
 * Created on 16 March 2007, 8:51
 */

package puggle.ui;

import java.io.File;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;

/**
 *
 * @author  gvasil
 */
public class TextPanel extends ResultsPanel {
    
    /**
     * Creates new form ClassicResultsPanel
     */
    public TextPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jToolBar1 = new javax.swing.JToolBar();

        mainPanel.setBackground(new java.awt.Color(239, 239, 231));
        mainPanel.setAlignmentX(0.0F);
        mainPanel.setAlignmentY(0.0F);

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 487, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jToolBar1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(mainPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void executeFile(File file) {
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
    
}
