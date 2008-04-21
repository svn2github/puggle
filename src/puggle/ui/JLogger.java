/*
 * JLogger.java
 *
 * Created on 23 September 2006, 6:38 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.ui;

import java.io.PrintStream;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author gvasil
 */
public class JLogger {
    
    private JTextArea textArea;
    private PrintStream printStream;
    
    /** Creates a new instance of Logger */
    public JLogger(PrintStream printStream, JTextArea textArea) {
        this.setPrintStream(printStream);
        this.setTextArea(textArea);
    }
    
    public JLogger(PrintStream printStream) {
        this.setPrintStream(printStream);
    }
    
    public JLogger(JTextArea textArea) {
        this.setTextArea(textArea);
    }
    
    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
    
    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }
    
    public JTextArea getTextArea() {
        return this.textArea;
    }
        
    public PrintStream getPrintStream() {
        return this.printStream;
    }
    
    public void write(String str) {
        if (this.textArea != null)
            this.textArea.setText(str);
        if (this.printStream != null)
            this.printStream.println(str);
    }
    
    public void error(String str) {
        String s = "[ERROR]" + str;
        
        if (this.textArea != null)
            this.textArea.setText(str);
        if (this.printStream != null)
            this.printStream.println(str);
    }
    
    public void warning(String str) {
        String s = "[WARNING]" + str;
        
        if (this.textArea != null)
            this.textArea.setText(str);
        if (this.printStream != null)
            this.printStream.println(str);
    }
}
