/*
 * ResultsPanel.java
 *
 * Created on 17 March 2007, 7:49
 */

package puggle.ui;

import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;

/**
 *
 * @author  gvasil
 */
public class ResultsPanel extends javax.swing.JPanel {
    
    /** Creates new form ResultsPanel */
    public ResultsPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);

    }// </editor-fold>//GEN-END:initComponents
    
    public void setResults(Query query, Hits hits)
    throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public int getCurrentResultsNumber() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    };
    
    public int getTotalResultsNumber() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    };
    
    public boolean hasPreviousResults() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    };
    
    public boolean hasNextResults() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    };
    
    public boolean nextResults() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    };
    
    public boolean previousResults() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not yet implemented");
    };
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
