/*
 * FileHandlerException.java
 *
 * Created on 2 September 2006, 2:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author gvasil
 */
public class FileHandlerException extends Exception {
  private Throwable cause;

  /**
   * Default constructor.
   */
  public FileHandlerException() {
    super();
  }

  /**
   * Constructs with message.
   */
  public FileHandlerException(String message) {
    super(message);
  }

  /**
   * Constructs with chained exception.
   */
  public FileHandlerException(Throwable cause) {
    super(cause.toString());
    this.cause = cause;
  }

  /**
   * Constructs with message and exception.
   */
  public FileHandlerException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Retrieves nested exception.
   */
  public Throwable getException() {
    return cause;
  }

  public void printStackTrace() {
    printStackTrace(System.err);
  }

  public void printStackTrace(PrintStream ps) {
    synchronized (ps) {
      super.printStackTrace(ps);
      if (cause != null) {
        ps.println("--- Nested Exception ---");
        cause.printStackTrace(ps);
      }
    }
  }

  public void printStackTrace(PrintWriter pw) {
    synchronized (pw) {
      super.printStackTrace(pw);
      if (cause != null) {
        pw.println("--- Nested Exception ---");
        cause.printStackTrace(pw);
      }
    }
  }
}