/*
 * CombinedAnalyzer.java
 *
 * Created on 14 September 2006, 8:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package puggle.LexicalAnalyzer;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.el.GreekAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;

/**
 *
 * @author gvasil
 */

public class CombinedAnalyzer extends Analyzer {
    private GreekAnalyzer greek = new GreekAnalyzer();
    public TokenStream tokenStream(String fieldName, Reader reader) {
        // Filters greek
        TokenStream tokens = greek.tokenStream(fieldName, reader);
        
        // Filters english
        tokens = new StandardFilter(tokens);
        tokens = new LowerCaseFilter(tokens);
        tokens = new PorterStemFilter(tokens);
        
        return tokens;
    }
}
