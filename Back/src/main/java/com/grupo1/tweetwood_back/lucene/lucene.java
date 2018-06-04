package com.grupo1.tweetwood_back.lucene;

import com.grupo1.tweetwood_back.repositories.KeyWordRepository;
import com.grupo1.tweetwood_back.SentimentAnalysis.SentimentAnalysisTweets;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.mongodb.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;


public class lucene {

     private static KeyWordRepository keywordRepository;
     private SentimentAnalysisTweets sat;
     public boolean createIndex(DBCursor db_cursor){
        try{
            Directory dir = FSDirectory.open(Paths.get("index/"));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            iwc.setOpenMode(OpenMode.CREATE);

            IndexWriter writer = new IndexWriter(dir, iwc);

            while(db_cursor.hasNext()){
                DBObject obj = db_cursor.next();
                Document doc = new Document();
                doc.add(new StringField("id",obj.get("_id").toString(),Field.Store.YES));
                doc.add(new TextField("text",obj.get("text").toString(), Field.Store.YES));
                if(writer.getConfig().getOpenMode() == OpenMode.CREATE){
                    writer.addDocument(doc);
                }
                else{
                    writer.updateDocument(new Term("id",obj.get("_id").toString()),doc);
                }
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<String> searchIndex(String film){
         ArrayList<String> docs = new ArrayList<>();
        try{
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("index/")));
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new StandardAnalyzer();

            QueryParser parser = new QueryParser("text",analyzer);
            Query query = parser.parse(film);

            TopDocs results = searcher.search(query,10000);
            ScoreDoc[] hits = results.scoreDocs;
            for(int i = 0; i < hits.length;i++){
                Document doc = searcher.doc(hits[i].doc);
                docs.add(doc.get("text"));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return docs;
    }

    public ArrayList<String>  getKeywords(){
        ArrayList<String> keywords = new ArrayList<String>();
        String out;
        StringBuffer sb = new StringBuffer();
        try{
            URL url = new URL("http://localhost:1310/keywords/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new RuntimeException("HTTP error code: " +  conn.getResponseCode());
            //
            BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = bf.readLine();
            JSONArray jsonResult = new JSONArray(out);
            for(int i = 0; i < jsonResult.length(); i++){
                JSONObject object = jsonResult.getJSONObject(i);
                keywords.add(object.getString("palabra"));
            }
            conn.disconnect();
            return keywords;

        }
        catch(MalformedURLException e){
            System.out.println(e);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return null;

    }

    //@Autowired
    public void exec_lucene() {
        sat = new SentimentAnalysisTweets();
        MongoClient myMongo;
        myMongo = new MongoClient("localhost");
        DB db = myMongo.getDB("twitter");
        DBCollection dbCollection = db.getCollection("statusJSONImpl");
        DBCursor db_cursor = dbCollection.find();

        ArrayList<String> tweets;
        ArrayList<String> keywords = getKeywords();
        boolean isIndexReady = createIndex(db_cursor);
        if(isIndexReady){
            for(String key: keywords) {
                tweets = searchIndex(key);
                for(String t: tweets)
                    try {
                        System.out.println("valoracion: " + sat.getAnalysis(t));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                System.out.println("\n");
            }
        }


    }
}
