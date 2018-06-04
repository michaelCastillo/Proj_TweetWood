package com.bah;

import java.io.*;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.store.RAMDirectory;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONArray;

public class CreaIndiceInvertido {
	private CreaIndiceInvertido() {}

	public static void main(String[] args) {
        //getDocuments();
		System.out.println("CreaIndiceInvertido\n");
		// TODO Auto-generated method stub
	   String indexPath = "index";
	   String docsPath = "documentos";
	   boolean create = true;
	   final Path docDir = Paths.get(docsPath);
	   if (!Files.isReadable(docDir)) {
	     System.out.println("Document directory '" +docDir.toAbsolutePath()+ "' does not exist or is not readable, please check the path");
	     System.exit(1);
	   }
	   
	   Date start = new Date();
	   try {
	     System.out.println("Indexando en : '" + indexPath + "'...");
	
	     Directory dir = FSDirectory.open(Paths.get(indexPath));
	     Analyzer analyzer = new StandardAnalyzer();
	     IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
	
	     if (create) {
	       // Create a new index in the directory, removing any
	       // previously indexed documents:
	       iwc.setOpenMode(OpenMode.CREATE);
	     } else {
	       // Add new documents to an existing index:
	       iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	     }
	
	     // Optional: for better indexing performance, if you
	     // are indexing many documents, increase the RAM
	     // buffer.  But if you do this, increase the max heap
	     // size to the JVM (eg add -Xmx512m or -Xmx1g):
	     //
	     // iwc.setRAMBufferSizeMB(256.0);
	
	     IndexWriter writer = new IndexWriter(dir, iwc);
	     indexDocs(writer, docDir);
	
	     // NOTE: if you want to maximize search performance,
	     // you can optionally call forceMerge here.  This can be
	     // a terribly costly operation, so generally it's only
	     // worth it when your index is relatively static (ie
	     // you're done adding documents to it):
	     //
	     // writer.forceMerge(1);
	
	     writer.close();
	
	     Date end = new Date();
	     System.out.println(end.getTime() - start.getTime() + " total milliseconds");
	
	   } catch (IOException e) {
	     System.out.println(" caught a " + e.getClass() +
	      "\n with message: " + e.getMessage());
	   }
	 }

	 static void getDocuments(){
	    //ArrayList<String> documents = new ArrayList<>();

         String out;
	    StringBuffer sb = new StringBuffer();
	    try{
            FileWriter document = new FileWriter("tweets.txt");
            URL url = new URL("http://localhost:8080/tweets");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type","application/json");

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new RuntimeException("Http error code: " + conn.getResponseCode());

            BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = bf.readLine();
            JSONArray jsonResult = new JSONArray(out);
            System.out.println(jsonResult.getJSONObject(0));
            for(int i  = 0; i < jsonResult.length(); i++){
                JSONObject obj = jsonResult.getJSONObject(i);
                document.write(obj.getString("text") + "\n");
                //documents.add(obj.getString("text"));
            }
            conn.disconnect();
            document.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
     }
	 
	 static void indexDocs(final IndexWriter writer, Path path) throws IOException {
		System.out.println("indexDocs\n");
	   if (Files.isDirectory(path)) {
	     Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
	       @Override
	       public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	         try {
	           indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
	         } catch (IOException ignore) {
	           // don't index files that can't be read.
	         }
	         return FileVisitResult.CONTINUE;
	       }
	     });
	   } else {
	     indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
	   }
	 }
	
	 /** Indexes a single document */
	 static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
		 System.out.println("indexDocs\n");
	   try (InputStream stream = Files.newInputStream(file)) {
	     // make a new, empty document
	     Document doc = new Document();
	     
	     // Add the path of the file as a field named "path".  Use a
	     // field that is indexed (i.e. searchable), but don't tokenize 
	     // the field into separate words and don't index term frequency
	     // or positional information:
	     Field pathField = new StringField("path", file.toString(), Field.Store.YES);
	     doc.add(pathField);
	     
	     // Add the last modified date of the file a field named "modified".
	     // Use a LongPoint that is indexed (i.e. efficiently filterable with
	     // PointRangeQuery).  This indexes to milli-second resolution, which
	     // is often too fine.  You could instead create a number based on
	     // year/month/day/hour/minutes/seconds, down the resolution you require.
	     // For example the long value 2011021714 would mean
	     // February 17, 2011, 2-3 PM.
	     doc.add(new LongPoint("modified", lastModified));
	     
	     // Add the contents of the file to a field named "contents".  Specify a Reader,
	     // so that the text of the file is tokenized and indexed, but not stored.
	     // Note that FileReader expects the file to be in UTF-8 encoding.
	     // If that's not the case searching for special characters will fail.
	     doc.add(new TextField("contenido", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));
	     
	     if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
	       // New index, so we just add the document (no old document can be there):
	       System.out.println("adding " + file);
	       writer.addDocument(doc);
	     } else {
	       // Existing index (an old copy of this document may have been indexed) so 
	       // we use updateDocument instead to replace the old one matching the exact 
	       // path, if present:
	       System.out.println("updating " + file);
	       writer.updateDocument(new Term("path", file.toString()), doc);
	     }
	   }
	 }
}


