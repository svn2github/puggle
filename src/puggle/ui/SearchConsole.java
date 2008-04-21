/*
 * SearchConsole.java
 *
 * Created on 25 February 2007, 2:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.ui;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import puggle.QueryEvaluator.Searcher;
import puggle.Resources.Resources;

/**
 *
 * @author gvasil
 */
public class SearchConsole {
    
    /** Creates a new instance of SearchConsole */
    public SearchConsole() {
    }
    
    private static Hits performSearch(File index, String q) throws IOException, ParseException {
        Query query = Searcher.createQuery("content", q);
        Hits hits = Searcher.search(index, query);

        return hits;
    }
    
    private static void printResults(Hits hits) throws IOException {
        for (int i = 0; i < hits.length(); i++) {
            Document doc = hits.doc(i);
            float score = hits.score(i);
            
            String path = doc.get("path");
            
            System.out.println("["+(i+1) +"]" +" "  +path);
        }
    }
    
    public static void main(String args[]) {
        
        String str = "";
        
        if (args.length == 0) {
            System.out.print("Search: ");
            System.out.flush();
            
            Scanner in = new Scanner(System.in);
            
            str = in.nextLine();
            in.close();
        }
        else {
            for (int i = 0; i < args.length; ++i) {
                str += args[i] + " ";
            }
        }
        
        try {
            long start = new Date().getTime();
            
            Hits hits = performSearch(new File(Resources.getIndexCanonicalPath()), str);
            
            long end = new Date().getTime();

            printResults(hits);
            
            System.out.println("\nFound " + hits.length() +
                    " document(s) (in " + (end - start) +
                    " milliseconds) that matched query '" +
                    str + "'."
                    );
        
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
            return;
        } catch (ParseException ex) {
            System.out.println("Parse Error: " + ex.getMessage());
            return;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }
        
    }

}
