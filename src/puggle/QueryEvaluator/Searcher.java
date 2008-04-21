/*
 * Searcher.java
 *
 * Created on 3 September 2006, 5:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.QueryEvaluator;

import puggle.LexicalAnalyzer.CombinedAnalyzer;
import puggle.LexicalAnalyzer.FileHandler;
import puggle.LexicalAnalyzer.TextHandler;
import puggle.Resources.Resources;
import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermPositionVector;
import org.apache.lucene.index.TermVectorOffsetInfo;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.Directory;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import java.io.File;
import java.util.Date;

/**
 *
 * @author gvasil
 */
public class Searcher {
    
    /** Creates a new instance of Searcher */
    public Searcher() {
    }
    
    public static Query createQuery(String field, String query)
            throws ParseException {
        QueryParser qp = new QueryParser(field, Resources.getAnalyzer());
        qp.setDefaultOperator(QueryParser.AND_OPERATOR);

        return qp.parse(query);
    }
    
    public static Hits search(File indexDir, Query query) throws IOException {
        Directory fsDir = FSDirectory.getDirectory(indexDir);
        IndexSearcher is = new IndexSearcher(fsDir);

        return is.search(query);
    }
    
    public static void main(String[] args) throws Exception {
        File indexDir = null;
        String q = null;
        if (args.length == 2) {
            indexDir = new File(args[0]);
            q = args[1];
        } else {
            indexDir = new File(args[1]);
            q = "perl programming";
        }
        
        if (!indexDir.exists() || !indexDir.isDirectory()) {
            throw new Exception(indexDir +
                    " does not exist or is not a directory.");
        }

        Query query = createQuery("content", q);
        
        long start = new Date().getTime();
        Hits hits = search(indexDir, query);
        long end = new Date().getTime();
        
        System.out.println("Found " + hits.length() +
                " document(s) (in " + (end - start) +
                " milliseconds) that matched query '" +
                q + "':"
        );
        
        QueryScorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(scorer);

        IndexReader index = IndexReader.open(indexDir);
        for (int i = 0; i < hits.length(); i++) {
            Document doc = hits.doc(i);
            
            int id = hits.id(i);
/*            TermPositionVector pos = (TermPositionVector) index.getTermFreqVector(id, "content");
            int in = pos.indexOf(q);
            TermVectorOffsetInfo[] t = pos.getOffsets(in);
*/
            System.out.println(doc.get("path") +" " +doc.get("title") +" " +"(" +hits.score(i) +")");
/*            
            for (int j = 0; j < t.length; j++) {
                System.out.print(" " +t[j].getStartOffset() +"-" +t[j].getEndOffset());
            }
*/
            String content = new FileHandler(false, false).getText(doc);
            if (content != null) {
                TokenStream stream =
                        new CombinedAnalyzer().tokenStream("content",
                        new StringReader(content));
                String fragment = highlighter.getBestFragments(stream, content, 2, "...");
                
                System.out.println(fragment.replaceAll("\r", "").replaceAll("\n", " "));
            }
        }
    }
}
