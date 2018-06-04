package com.bah;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import javax.swing.text.Highlighter;

public class BuscaSimple {

	public static void main(String[] args) {
		System.out.println("BuscaSimple\n");
		// TODO Auto-generated method stub
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("index")));
			IndexSearcher searcher = new IndexSearcher(reader);
		    Analyzer analyzer = new StandardAnalyzer();
		    
		    QueryParser parser = new QueryParser("contenido", analyzer);
		    Query query = parser.parse("Deadpool");
		    
		    TopDocs hits = searcher.search(query, 10);
		    //ScoreDoc[] hits = results.scoreDocs;


		    for(int i = 0; i < hits.scoreDocs.length; i++) {

		    	Document doc = searcher.doc(hits.scoreDocs[i].doc);
		    	String path = doc.get("path");
		    	System.out.println((i+1)+".- score="+hits.scoreDocs[i].score + " doc="+hits.scoreDocs[i].doc+" path="+path);
                System.out.println(doc.get("deadpool"));
            }

		    reader.close();
		}
		catch(IOException ioe) {
			Logger.getLogger(BuscaSimple.class.getName()).log(Level.SEVERE, null, ioe);
		}
		catch(ParseException pe) {
			Logger.getLogger(BuscaSimple.class.getName()).log(Level.SEVERE, null, pe);
		}

	}

}
