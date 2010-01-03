/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package puggle.Util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import puggle.Resources.Resources;

/**
 *
 * @author gvasil
 */
public class Updater {

    public Updater() {

    }

    public boolean isUpdated() throws UnknownHostException, IOException {
        String host = "puggle.sourceforge.net";
        String file = "/";

        HTTPConnection webConnection = new HTTPConnection(host);
        if (webConnection != null) {
            BufferedReader in = webConnection.get(file);
            String line;
            while ((line = in.readLine()) != null) { // read until EOF
                if (line.matches(".*a\\s*href.*current version.*")) {
                    line = line.replaceAll(".*a\\s*href\\s*=\\s*\"", "");
                    line = line.replaceAll("/.*", "");
                    line = line.replaceAll("[^0-9\\.]", "");

                    double webVersion;
                    try {
                        webVersion = Double.parseDouble(line);
                    } catch (NumberFormatException e) {
                        throw new IOException(e.getMessage());
                    }

                    if (webVersion > Double.parseDouble(Resources.getApplicationVersion())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        new Updater().isUpdated();
    }
}

class HTTPConnection {
    public final static int HTTP_PORT = 80;

    InetAddress wwwHost;

    DataInputStream dataInputStream;

    PrintStream outputStream;

    public HTTPConnection(String host) throws UnknownHostException {
        wwwHost = InetAddress.getByName(host);
    }

    public BufferedReader get(String file) throws IOException {
      Socket httpPipe;
      InputStream in;
      OutputStream out;
      BufferedReader bufReader;
      PrintWriter printWriter;
      httpPipe = new Socket(wwwHost, HTTP_PORT);
      if (httpPipe == null) {
        return null;
      }
      // get raw streams
      in = httpPipe.getInputStream();
      out = httpPipe.getOutputStream();
      // turn into useful ones
      bufReader = new BufferedReader(new InputStreamReader(in));
      printWriter = new PrintWriter(new OutputStreamWriter(out), true);
      if (in == null || out == null || bufReader == null || printWriter == null) {
        throw new IOException("Failed to open streams to socket.");
      }
      // send GET request
      printWriter.print("GET " + file + " HTTP/1.0\r\n");
      printWriter.print("Host: puggle.sourceforge.net\r\n");
      printWriter.print("User-Agent: Puggle/1.0\r\n");
      printWriter.print("Content-Length: 2\r\n");
      printWriter.println("\r\n\r\n");

      return bufReader;
    }
}

