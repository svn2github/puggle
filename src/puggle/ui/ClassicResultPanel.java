/*
 * ClassicResultPanel.java
 *
 * Created on 27 April 2007, 2:13
 */

package puggle.ui;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author  gvasil
 */
public class ClassicResultPanel extends javax.swing.JPanel {
    
    /**
     * Creates new form ClassicResultPanel
     */
    public ClassicResultPanel() {
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

        popupMenu = new javax.swing.JPopupMenu();
        menuItem = new javax.swing.JMenuItem();
        iconLabel = new javax.swing.JLabel();
        pathLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();
        titleLabel = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();

        menuItem.setFont(new java.awt.Font("Tahoma", 0, 10));
        menuItem.setText("Copy");
        menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemActionPerformed(evt);
            }
        });
        popupMenu.add(menuItem);

        setBackground(new java.awt.Color(255, 255, 255));

        iconLabel.setBackground(new java.awt.Color(207, 215, 227));
        iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLabel.setAlignmentY(0.0F);
        iconLabel.setOpaque(true);
        iconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                iconLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                iconLabelMouseExited(evt);
            }
        });

        pathLabel.setBackground(new java.awt.Color(255, 255, 255));
        pathLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
        pathLabel.setForeground(new java.awt.Color(0, 102, 51));
        pathLabel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        pathLabel.setMinimumSize(new java.awt.Dimension(6, 20));
        pathLabel.setPreferredSize(new java.awt.Dimension(6, 20));
        pathLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pathLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pathLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pathLabelMouseExited(evt);
            }
        });

        scrollPane.setBackground(new java.awt.Color(255, 255, 255));
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new java.awt.Dimension(6, 20));

        textPane.setBorder(null);
        textPane.setEditable(false);
        textPane.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        textPane.setSelectionColor(new java.awt.Color(255, 204, 102));
        textPane.setEditorKit(new HTMLEditorKit());
        scrollPane.setViewportView(textPane);

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        titleLabel.setForeground(new java.awt.Color(41, 89, 140));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        titleLabel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        titleLabel.setMinimumSize(new java.awt.Dimension(6, 20));
        titleLabel.setPreferredSize(new java.awt.Dimension(6, 20));
        titleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                titleLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                titleLabeltitleLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                titleLabelMouseExited(evt);
            }
        });

        scoreLabel.setMaximumSize(new java.awt.Dimension(72, 16));
        scoreLabel.setMinimumSize(new java.awt.Dimension(72, 16));
        scoreLabel.setPreferredSize(new java.awt.Dimension(72, 16));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(iconLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(scrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .add(pathLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(titleLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                        .add(35, 35, 35)
                        .add(scoreLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(iconLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(scoreLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(titleLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pathLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemActionPerformed
        // get the system clipboard
        Clipboard systemClipboard =
                Toolkit.getDefaultToolkit().getSystemClipboard();
        // set the textual content on the clipboard to our 
        // Transferable object
        // we use the 
        Transferable transferableText = new StringSelection(this.pathLabel.getText());
        systemClipboard.setContents(transferableText, null);
    }//GEN-LAST:event_menuItemActionPerformed

    private void pathLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pathLabelMouseExited
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pathLabel.setFont(new java.awt.Font("Tahoma", 1, 11));
    }//GEN-LAST:event_pathLabelMouseExited

    private void pathLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pathLabelMouseEntered
        if (!titleLabel.getText().equals("")) {
            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            pathLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        }
    }//GEN-LAST:event_pathLabelMouseEntered

    private void pathLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pathLabelMouseClicked
        if (pathLabel.getText().equals("")) {
            return;
        }
        
        int button = evt.getButton();
        if (button == java.awt.event.MouseEvent.BUTTON1) {
            this.executeFile(new File(pathLabel.getText()));
        } else if (button == java.awt.event.MouseEvent.BUTTON3) {
            popupMenu.show(evt.getComponent(), evt.getX(), evt.getY() );
        }
    }//GEN-LAST:event_pathLabelMouseClicked

    private void titleLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleLabelMouseExited
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
    }//GEN-LAST:event_titleLabelMouseExited

    private void titleLabeltitleLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleLabeltitleLabelMouseEntered
        if (!titleLabel.getText().equals("")) {
            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            titleLabel.setFont(new java.awt.Font("Tahoma", 1, 15));
        }
    }//GEN-LAST:event_titleLabeltitleLabelMouseEntered

    private void titleLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleLabelMouseClicked
        int button = evt.getButton();
        if (button == java.awt.event.MouseEvent.BUTTON1
                && !titleLabel.getText().equals("")) {
            this.executeFile(new File(titleLabel.getToolTipText()));
        }
    }//GEN-LAST:event_titleLabelMouseClicked

    private void iconLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLabelMouseExited
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_iconLabelMouseExited

    private void iconLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLabelMouseEntered
        if (iconLabel.getIcon() != null) {
            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }
    }//GEN-LAST:event_iconLabelMouseEntered

    private void iconLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLabelMouseClicked
        int button = evt.getButton();
        if (button == java.awt.event.MouseEvent.BUTTON1
                && !titleLabel.getText().equals("")) {
            try {
                this.executeFile(new File(titleLabel.getToolTipText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE, this.imageControl.getErrorIcon());
            }
        }
    }//GEN-LAST:event_iconLabelMouseClicked
    
    private void executeFile(File file) {
        try {
            file = file.getCanonicalFile();
            Desktop.getDesktop().open(file);
        } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE,
                        this.imageControl.getErrorIcon());
        }
    }
    
    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }
    
    public javax.swing.JLabel getTitleLabel() {
        return this.titleLabel;
    }
    
    public void setPath(String path) {
        this.pathLabel.setText(path);
    }
    
    public javax.swing.JLabel getPathLabel() {
        return this.pathLabel;
    }
    
    public void setFragment(String fragment) {
        this.textPane.setText(fragment);
    }
    
    public void setSize(String size) {
        //this.sizeLabel.setText(size);
    }
    
    public void setScoreIcon(Icon score) {
        this.scoreLabel.setIcon(score);
    }
    
    public void setIcon(Icon icon) {
        this.iconLabel.setIcon(icon);
    }
    
    public javax.swing.JLabel getIconLabel() {
        return this.iconLabel;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconLabel;
    private javax.swing.JMenuItem menuItem;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JLabel scoreLabel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextPane textPane;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    private ImageControl imageControl;
    
}
