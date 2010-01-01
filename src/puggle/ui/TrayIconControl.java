/*
 * TrayIconControl.java
 *
 * Created on 31 March 2007, 11:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.ui;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import puggle.Resources.Resources;

/**
 *
 * @author gvasil
 */

public class TrayIconControl {
    private final SystemTray tray;
    private final TrayIcon trayIcon;
    
    private ImageControl imageControl;
    
    private JFrame frame;
    
    public TrayIconControl(JFrame f) throws UnsupportedOperationException {
        
        this.imageControl = ImageControl.getImageControl();
        
        this.tray = SystemTray.getSystemTray();
        
        ActionListener openListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                frame.setState(java.awt.Frame.NORMAL);
                tray.remove(trayIcon);
            }
        };
        
        PopupMenu popup = new PopupMenu();
        MenuItem defaultItem = new MenuItem("Open " + Resources.APP_NAME
                + " " +((Resources.isPortableEdition() == true) ?
                    "Portable " : "Desktop ")
                    + "Search ");
        defaultItem.addActionListener(openListener);
        popup.add(defaultItem);
        
        this.trayIcon = new TrayIcon(imageControl.getTrayIcon(),
                Resources.APP_NAME + " " +
                ((Resources.isPortableEdition() == true) ? "Portable " : "Desktop ")
                + "Search ", popup);

        this.frame = f;
    }
    
    public void add() {
        if (SystemTray.isSupported()) {
            
            MouseListener mouseListener = new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    
                }
                public void mouseEntered(MouseEvent e) {
                    
                }
                public void mouseExited(MouseEvent e) {
                    
                }
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        frame.setVisible(true);
                        frame.setState(java.awt.Frame.NORMAL);
                        tray.remove(trayIcon);
                    }
                }
                public void mouseReleased(MouseEvent e) {
                    
                }
            };

            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
            //        trayIcon.displayMessage("Action Event", 
            //                "An Action Event Has Been Peformed!",
            //                TrayIcon.MessageType.INFO);
                }
            };
            
            this.trayIcon.setImageAutoSize(true);
            this.trayIcon.addActionListener(actionListener);
            this.trayIcon.addMouseListener(mouseListener);
            
            //    Depending on which Mustang build you have, you may need to uncomment
            //    out the following code to check for an AWTException when you add 
            //    an image to the system tray.
            
            try {
                this.tray.add(this.trayIcon);
                this.frame.setVisible(false);
            }
            catch (java.awt.AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        } else {
            System.err.println("System tray is currently not supported.");
        }
    }
    
    public void displayMessage(String caption, String text, TrayIcon.MessageType type) {
        trayIcon.displayMessage(caption, text, type);
    }

    public void setToolTip(String text) {
        this.trayIcon.setToolTip(text);
    }
}

