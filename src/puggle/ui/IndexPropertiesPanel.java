/*
 * IndexerPanel.java
 *
 * Created on 19 December 2007, 7:57
 */

package puggle.ui;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import puggle.Indexer.IndexProperties;
import puggle.Resources.Resources;
import puggle.Util.Util;

/**
 *
 * @author  gvasil
 */
public class IndexPropertiesPanel extends javax.swing.JPanel {
    
    /** Creates new form IndexerPanel */
    public IndexPropertiesPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        storeTextCb = new javax.swing.JCheckBox();
        storeThumbsCb = new javax.swing.JCheckBox();
        jpegCheck = new javax.swing.JCheckBox();
        pngCheck = new javax.swing.JCheckBox();
        gifCheck = new javax.swing.JCheckBox();
        txtCheck = new javax.swing.JCheckBox();
        pdfCheck = new javax.swing.JCheckBox();
        docCheck = new javax.swing.JCheckBox();
        rtfCheck = new javax.swing.JCheckBox();
        htmlCheck = new javax.swing.JCheckBox();
        xlsCheck = new javax.swing.JCheckBox();
        pptCheck = new javax.swing.JCheckBox();
        mp3Check = new javax.swing.JCheckBox();
        indexDirectoryPanel = new javax.swing.JPanel();
        indexDirectoryLabel = new javax.swing.JLabel();
        indexTextField = new javax.swing.JTextField();
        indexLabel = new javax.swing.JLabel();
        foldersLabel = new javax.swing.JLabel();
        optionsLabel = new javax.swing.JLabel();
        filetypesLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        pathScrollPane = new javax.swing.JScrollPane();
        pathList = new javax.swing.JList();
        this.pathList.setModel(new javax.swing.DefaultListModel());
        addFolderButton = new javax.swing.JButton();
        removeFolderButton = new javax.swing.JButton();
        filetypesLabel1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lastIndexedLabel = new javax.swing.JLabel();
        zipCheck = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        indexSizeLabel = new javax.swing.JLabel();

        storeTextCb.setText("Store text");
        storeTextCb.setToolTipText("Store the extracted text of each file");
        storeTextCb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        storeTextCb.setMargin(new java.awt.Insets(0, 0, 0, 0));
        storeTextCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingOptions(evt);
            }
        });

        storeThumbsCb.setText("Store thumbnails");
        storeThumbsCb.setToolTipText("Store a thumbnail for each file");
        storeThumbsCb.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        storeThumbsCb.setMargin(new java.awt.Insets(0, 0, 0, 0));
        storeThumbsCb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingOptions(evt);
            }
        });

        jpegCheck.setText("jpeg");
        jpegCheck.setToolTipText("Whether to index JPEG files");
        jpegCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jpegCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jpegCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        pngCheck.setText("png");
        pngCheck.setToolTipText("Whether to index PNG files");
        pngCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pngCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        pngCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        gifCheck.setText("gif");
        gifCheck.setToolTipText("Whether to index GIF files");
        gifCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        gifCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gifCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        txtCheck.setText("txt");
        txtCheck.setToolTipText("Whether to index TXT files");
        txtCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        txtCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        txtCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        pdfCheck.setText("pdf");
        pdfCheck.setToolTipText("Whether to index PDF files");
        pdfCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pdfCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        pdfCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        docCheck.setText("doc");
        docCheck.setToolTipText("Whether to index MS WORD files");
        docCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        docCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        docCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        rtfCheck.setText("rtf");
        rtfCheck.setToolTipText("Whether to index RTF files");
        rtfCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rtfCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        rtfCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        htmlCheck.setText("html");
        htmlCheck.setToolTipText("Whether to index HTML files");
        htmlCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        htmlCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        htmlCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        xlsCheck.setText("xls");
        xlsCheck.setToolTipText("Whether to index MS EXCEL files");
        xlsCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        xlsCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        xlsCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        pptCheck.setText("ppt");
        pptCheck.setToolTipText("Whether to index MS POWERPOINT files");
        pptCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        pptCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        pptCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        mp3Check.setText("mp3");
        mp3Check.setToolTipText("Whether to index MP3 files");
        mp3Check.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        mp3Check.setMargin(new java.awt.Insets(0, 0, 0, 0));
        mp3Check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        indexDirectoryLabel.setText("Index Directory Path:");

        indexTextField.setEditable(false);
        this.indexTextField.setText(Resources.getIndexCanonicalPath());
        indexTextField.setToolTipText("Absolute path where the Index will be stored");

        org.jdesktop.layout.GroupLayout indexDirectoryPanelLayout = new org.jdesktop.layout.GroupLayout(indexDirectoryPanel);
        indexDirectoryPanel.setLayout(indexDirectoryPanelLayout);
        indexDirectoryPanelLayout.setHorizontalGroup(
            indexDirectoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(indexDirectoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(indexDirectoryLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(indexTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addContainerGap())
        );
        indexDirectoryPanelLayout.setVerticalGroup(
            indexDirectoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(indexDirectoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(indexDirectoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(indexDirectoryLabel)
                    .add(indexTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        indexLabel.setBackground(new java.awt.Color(102, 102, 255));
        indexLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        indexLabel.setForeground(new java.awt.Color(255, 255, 255));
        indexLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        indexLabel.setText("Index");
        indexLabel.setOpaque(true);

        foldersLabel.setBackground(new java.awt.Color(102, 102, 255));
        foldersLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        foldersLabel.setForeground(new java.awt.Color(255, 255, 255));
        foldersLabel.setText("Indexing Folders");
        foldersLabel.setOpaque(true);

        optionsLabel.setBackground(new java.awt.Color(102, 102, 255));
        optionsLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        optionsLabel.setForeground(new java.awt.Color(255, 255, 255));
        optionsLabel.setText("Indexing Options");
        optionsLabel.setOpaque(true);

        filetypesLabel.setBackground(new java.awt.Color(102, 102, 255));
        filetypesLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        filetypesLabel.setForeground(new java.awt.Color(255, 255, 255));
        filetypesLabel.setText("Filetypes");
        filetypesLabel.setOpaque(true);

        pathScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        pathScrollPane.setViewportView(pathList);

        addFolderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/list-add.png"))); // NOI18N
        addFolderButton.setText("Add");
        addFolderButton.setToolTipText("Add folder");
        addFolderButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        addFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFolderButtonActionPerformed(evt);
            }
        });

        removeFolderButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/list-remove.png"))); // NOI18N
        removeFolderButton.setText("Remove");
        removeFolderButton.setToolTipText("Remove folder");
        removeFolderButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        removeFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFolderButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(pathScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(removeFolderButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(addFolderButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(pathScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 66, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(addFolderButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removeFolderButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(13, 13, 13))
        );

        filetypesLabel1.setBackground(new java.awt.Color(102, 102, 255));
        filetypesLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        filetypesLabel1.setForeground(new java.awt.Color(255, 255, 255));
        filetypesLabel1.setText("Index Information");
        filetypesLabel1.setOpaque(true);

        jLabel1.setText("Last Full Indexing was at:");

        lastIndexedLabel.setText("<empty>");

        zipCheck.setText("zip");
        zipCheck.setToolTipText("Whether to index MP3 files");
        zipCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        zipCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        zipCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storeIndexingFiletypes(evt);
            }
        });

        jLabel2.setText("Index Size:");

        indexSizeLabel.setText("<empty>");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(zipCheck)
                .addContainerGap(563, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(mp3Check)
                .addContainerGap(557, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, filetypesLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(storeTextCb)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(storeThumbsCb))
                            .add(layout.createSequentialGroup()
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jpegCheck)
                                    .add(txtCheck))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(pdfCheck)
                                    .add(pngCheck))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(layout.createSequentialGroup()
                                        .add(docCheck)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(rtfCheck)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(htmlCheck)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(xlsCheck)
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(pptCheck))
                                    .add(gifCheck))))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 319, Short.MAX_VALUE))
                    .add(filetypesLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .add(optionsLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, indexDirectoryPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, indexLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, foldersLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE))
                .add(0, 0, 0))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(indexSizeLabel)
                    .add(lastIndexedLabel))
                .addContainerGap(419, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(indexLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(indexDirectoryPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(foldersLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(optionsLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(storeTextCb)
                    .add(storeThumbsCb))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(filetypesLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jpegCheck)
                    .add(pngCheck)
                    .add(gifCheck))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtCheck)
                    .add(pdfCheck)
                    .add(docCheck)
                    .add(rtfCheck)
                    .add(htmlCheck)
                    .add(xlsCheck)
                    .add(pptCheck))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(zipCheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mp3Check)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(filetypesLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(lastIndexedLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(indexSizeLabel))
                .addContainerGap(12, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void storeIndexingFiletypes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeIndexingFiletypes
        this.storeIndexingFiletypes();
    }//GEN-LAST:event_storeIndexingFiletypes

    private void storeIndexingOptions(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storeIndexingOptions
        this.storeIndexingOptions();
    }//GEN-LAST:event_storeIndexingOptions

    private void removeFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFolderButtonActionPerformed
        int[] selectedRows = this.pathList.getSelectedIndices();
        
        java.util.Arrays.sort(selectedRows);
        
        javax.swing.DefaultListModel model = 
                (javax.swing.DefaultListModel)this.pathList.getModel();
        
        for (int i = selectedRows.length - 1; i >= 0; --i) {
            model.removeElementAt(selectedRows[i]);
        }
        
        this.storeIndexingFolders();
    }//GEN-LAST:event_removeFolderButtonActionPerformed

    private void addFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFolderButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            javax.swing.DefaultListModel model = 
                    (javax.swing.DefaultListModel)this.pathList.getModel();

            String path = chooser.getSelectedFile().getPath();
            int len = model.getSize();
            
            while (--len >= 0) {
                if (path.equals(model.getElementAt(len)) == true) {
                    return;
                }
            }
            
            model.addElement(path);
            this.storeIndexingFolders();
        }
        
        
    }//GEN-LAST:event_addFolderButtonActionPerformed

    @Override
    public void setEnabled(boolean b) {
        if (this.indexProperties.isPortable() == true) {
            this.pathList.setEnabled(false);
            this.addFolderButton.setEnabled(false);
            this.removeFolderButton.setEnabled(false);
        }
        this.jpegCheck.setEnabled(b); this.gifCheck.setEnabled(b);
        this.pngCheck.setEnabled(b); this.txtCheck.setEnabled(b);
        this.docCheck.setEnabled(b); this.pdfCheck.setEnabled(b);
        this.xlsCheck.setEnabled(b); this.pptCheck.setEnabled(b);
        this.htmlCheck.setEnabled(b); this.rtfCheck.setEnabled(b);
        this.zipCheck.setEnabled(b);
        this.mp3Check.setEnabled(b);
        this.storeTextCb.setEnabled(b); this.storeThumbsCb.setEnabled(b);
    }
    
    private void storeIndexingFolders() {
        javax.swing.DefaultListModel model = 
                (javax.swing.DefaultListModel)this.pathList.getModel();
        int rowCount = model.getSize();
        
        String path = "";
        for (int i = 0; i < rowCount; ++i) {
            File f = new File((String)model.getElementAt(i));
            
            if (f.isDirectory() == false || f.exists() == false) {
                JOptionPane.showMessageDialog(this, "File '" +f
                        +"' does not exists or is not a directory",
                        "Indexing error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                path += f.getCanonicalPath() + File.pathSeparator;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        this.indexProperties.setPath(path);
    }
    
    private void storeIndexingFiletypes() {
        File dataDir = null, indexDir = null;
        File[] dataDirsFile = null;
        
        /* <Save properties> */
        String image_filetypes = "";
        String document_filetypes = "";
        String misc_filetypes = "";
        String music_filetypes = "";
        String archive_filetypes = "";
        
        if (this.jpegCheck.isSelected())
            image_filetypes += "jpeg,jpg,";
        if (this.pngCheck.isSelected())
            image_filetypes += "png,";
        if (this.gifCheck.isSelected())
            image_filetypes += "gif,";
        if (this.txtCheck.isSelected())
            document_filetypes += "txt,";
        if (this.pdfCheck.isSelected())
            document_filetypes += "pdf,";
        if (this.docCheck.isSelected())
            document_filetypes += "doc,";
        if (this.rtfCheck.isSelected())
            document_filetypes += "rtf,";
        if (this.htmlCheck.isSelected())
            document_filetypes += "html,htm,";
        if (this.xlsCheck.isSelected())
            document_filetypes += "xls,";
        if (this.pptCheck.isSelected())
            document_filetypes += "ppt,";
        if (this.mp3Check.isSelected())
            music_filetypes += "mp3,";
        if (this.zipCheck.isSelected())
            archive_filetypes += "zip,";
        
        if (image_filetypes.lastIndexOf(',') != -1) {
            image_filetypes = image_filetypes.substring(0, image_filetypes.lastIndexOf(','));
        }
        
        if (document_filetypes.lastIndexOf(',') != -1) {
            document_filetypes = document_filetypes.substring(0, document_filetypes.lastIndexOf(','));
        }
        
        if (misc_filetypes.lastIndexOf(',') != -1) {
            misc_filetypes = misc_filetypes.substring(0, misc_filetypes.lastIndexOf(','));
        }
        
        if (music_filetypes.lastIndexOf(',') != -1) {
            music_filetypes = music_filetypes.substring(0, music_filetypes.lastIndexOf(','));
        }

        if (archive_filetypes.lastIndexOf(',') != -1) {
            archive_filetypes = archive_filetypes.substring(0, archive_filetypes.lastIndexOf(','));
        }
        
        this.indexProperties.setImageFiletypes(image_filetypes);
        this.indexProperties.setDocumentFiletypes(document_filetypes);
        this.indexProperties.setMiscFiletypes(misc_filetypes);
        this.indexProperties.setMusicFiletypes(music_filetypes);
        this.indexProperties.setArchiveFiletypes(archive_filetypes);
    }
    
    private void storeIndexingOptions() {
        this.indexProperties.setStoreText(this.storeTextCb.isSelected());
        this.indexProperties.setStoreThumbnail(this.storeThumbsCb.isSelected());
    }
    
    public void setProperties(IndexProperties properties) {
        
        this.indexProperties = properties;
        
        String image_filetypes = indexProperties.getImageFiletypes();
        String document_filetypes = indexProperties.getDocumentFiletypes();
        String music_filetypes = indexProperties.getMusicFiletypes();
        String archive_filetypes = indexProperties.getArchiveFiletypes();
        
        if (indexProperties.isPortable() == true) {
            this.pathList.setEnabled(false);
            this.addFolderButton.setEnabled(false);
            this.removeFolderButton.setEnabled(false);
        }
        else {
            this.pathList.setEnabled(true);
            this.addFolderButton.setEnabled(true);
            this.removeFolderButton.setEnabled(true);
        }
        
        this.indexTextField.setText(Resources.getIndexCanonicalPath());
        
        javax.swing.DefaultListModel model = 
                (javax.swing.DefaultListModel)this.pathList.getModel();
        
        /* clear list */
        model.clear();
        
        /* load new values */
        File splt[] = indexProperties.getDataDirectories();
        for (int i = 0; i < splt.length; ++i) {
            model.addElement(splt[i].getPath());
        }
        
        storeTextCb.setSelected(indexProperties.getStoreText());
        storeThumbsCb.setSelected(indexProperties.getStoreThumbnail());
    
        if (image_filetypes != null && (image_filetypes.contains(new StringBuffer("jpg"))
        && image_filetypes.contains(new StringBuffer("jpeg")))) {
            this.jpegCheck.setSelected(true);
        } else {
            this.jpegCheck.setSelected(false);
        }
        
        if (image_filetypes != null && (image_filetypes.contains(new StringBuffer("png")))) {
            this.pngCheck.setSelected(true);
        } else {
            this.pngCheck.setSelected(false);
        }
        
        if (image_filetypes != null && (image_filetypes.contains(new StringBuffer("gif")))) {
            this.gifCheck.setSelected(true);
        } else {
            this.gifCheck.setSelected(false);
        }
        
        if (document_filetypes != null && (document_filetypes.contains(new StringBuffer("txt")))) {
            this.txtCheck.setSelected(true);
        } else {
            this.txtCheck.setSelected(false);
        }
        
        if (document_filetypes != null && (document_filetypes.contains(new StringBuffer("pdf")))) {
            this.pdfCheck.setSelected(true);
        } else {
            this.pdfCheck.setSelected(false);
        }
        
        if (document_filetypes != null && (document_filetypes.contains(new StringBuffer("doc")))) {
            this.docCheck.setSelected(true);
        } else {
            this.docCheck.setSelected(false);
        }
        
        if (document_filetypes != null && (document_filetypes.contains(new StringBuffer("rtf")))) {
            this.rtfCheck.setSelected(true);
        } else {
            this.rtfCheck.setSelected(false);
        }
        
        if (document_filetypes != null && (document_filetypes.contains(new StringBuffer("html")) && document_filetypes.contains(new StringBuffer("htm")))) {
            this.htmlCheck.setSelected(true);
        } else {
            this.htmlCheck.setSelected(false);
        }
        
        if (document_filetypes != null && (document_filetypes.contains(new StringBuffer("xls")))) {
            this.xlsCheck.setSelected(true);
        } else {
            this.xlsCheck.setSelected(false);
        }
        
        if (document_filetypes != null && (document_filetypes.contains(new StringBuffer("ppt")))) {
            this.pptCheck.setSelected(true);
        } else {
            this.pptCheck.setSelected(false);
        }
        
        if (music_filetypes != null && (music_filetypes.contains(new StringBuffer("mp3")))) {
            this.mp3Check.setSelected(true);
        } else {
            this.mp3Check.setSelected(false);
        }

        if (archive_filetypes != null && (archive_filetypes.contains(new StringBuffer("zip")))) {
            this.zipCheck.setSelected(true);
        } else {
            this.zipCheck.setSelected(false);
        }

        if (this.indexProperties.getLastIndexed() == 0) {
            this.lastIndexedLabel.setText("Never");
            this.lastIndexedLabel.setToolTipText("Index is incomplete. Please start indexer.");
        }
        else {
            this.lastIndexedLabel.setText(
                    DateFormat.getDateInstance().format(this.indexProperties.getLastIndexedDate()));
        }

        this.indexSizeLabel.setText(Util.adjustSize(Util.getFileSize(new File(Resources.getIndexCanonicalPath()))));

    }
    
    private IndexProperties indexProperties;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFolderButton;
    private javax.swing.JCheckBox docCheck;
    private javax.swing.JLabel filetypesLabel;
    private javax.swing.JLabel filetypesLabel1;
    private javax.swing.JLabel foldersLabel;
    private javax.swing.JCheckBox gifCheck;
    private javax.swing.JCheckBox htmlCheck;
    private javax.swing.JLabel indexDirectoryLabel;
    private javax.swing.JPanel indexDirectoryPanel;
    private javax.swing.JLabel indexLabel;
    private javax.swing.JLabel indexSizeLabel;
    private javax.swing.JTextField indexTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox jpegCheck;
    private javax.swing.JLabel lastIndexedLabel;
    private javax.swing.JCheckBox mp3Check;
    private javax.swing.JLabel optionsLabel;
    private javax.swing.JList pathList;
    private javax.swing.JScrollPane pathScrollPane;
    private javax.swing.JCheckBox pdfCheck;
    private javax.swing.JCheckBox pngCheck;
    private javax.swing.JCheckBox pptCheck;
    private javax.swing.JButton removeFolderButton;
    private javax.swing.JCheckBox rtfCheck;
    private javax.swing.JCheckBox storeTextCb;
    private javax.swing.JCheckBox storeThumbsCb;
    private javax.swing.JCheckBox txtCheck;
    private javax.swing.JCheckBox xlsCheck;
    private javax.swing.JCheckBox zipCheck;
    // End of variables declaration//GEN-END:variables
    
}
