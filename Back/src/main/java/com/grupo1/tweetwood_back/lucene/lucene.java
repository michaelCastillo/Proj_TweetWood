package com.grupo1.tweetwood_back.lucene;

import com.grupo1.tweetwood_back.SentimentAnalysis.SentimentAnalysisTweets;
import com.grupo1.tweetwood_back.modules.*;
import com.grupo1.tweetwood_back.repositories.EstadisticaRepository;
import com.grupo1.tweetwood_back.repositories.GeneroRepository;
import com.grupo1.tweetwood_back.repositories.KeyWordRepository;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
import com.mongodb.*;
import javassist.compiler.ast.Keyword;
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
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;


public class lucene {

     private static KeyWordRepository keywordRepository;
     private SentimentAnalysisTweets sat;

     @Autowired
     private KeyWordRepository keyWordRepository;
     @Autowired
     private PeliculaRepository peliculaRepository;

     public lucene(KeyWordRepository keyWordRepository){
         this.keyWordRepository = keyWordRepository;
     }

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
                doc.add(new TextField("retweetCount",obj.get("retweetCount").toString(), Field.Store.YES));
                doc.add(new StringField("id_user",((DBObject)obj.get("user")).get("_id").toString(),Field.Store.YES));
                doc.add(new StringField("name_user",((DBObject)obj.get("user")).get("name").toString(),Field.Store.YES));
                doc.add(new StringField("num_followers",((DBObject)obj.get("user")).get("followersCount").toString(),Field.Store.YES));
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

    public ArrayList<Map<String,String>> searchIndex(String film, int op){
         ArrayList<Map<String,String>> docs = new ArrayList<>();
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
                Map<String,String> doc_elements = new HashMap<>();
                doc_elements.put("id",doc.get("id"));
                doc_elements.put("text",doc.get("text"));
                if(op == 1){
                    doc_elements.put("retweetedCount",doc.get("retweetCount"));
                    doc_elements.put("id_user",doc.get("id_user"));
                    doc_elements.put("name_user",doc.get("name_user"));
                    doc_elements.put("num_followers",doc.get("num_followers"));
                }
                docs.add(doc_elements);
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
            URL url = new URL("http://206.189.224.139:8080/tweetwood_back-0.0.1-SNAPSHOT/keywords/");
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

    public List<List<Map<String,String>>> getUsers(PeliculaRepository peliculaRepository, EstadisticaRepository estadisticaRepository, GeneroRepository generoRepository){
        MongoClient myMongo;
        myMongo = new MongoClient("206.189.224.139:27017");
        DB db = myMongo.getDB("twitternew");
        DBCollection dbCollection = db.getCollection("statusJSONImpl");
        DBCursor db_cursor = dbCollection.find();
        ArrayList<Map<String,String>> tweets;
        List<KeyWord> keyWords = this.keyWordRepository.findAll();
        ArrayList<String> keywords = new ArrayList<>();
        for(KeyWord keyWord: keyWords){
            keywords.add(keyWord.getPalabra());
        }
        System.out.println("Antes del index");
        boolean isIndexReady = createIndex(db_cursor); //Se crea el indice invertido.
        System.out.println("Pase el index");
        List<List<Map<String,String>>> usersToOut = new ArrayList<>();
        if(isIndexReady){
            List<Pelicula> peliculas;
            List<Genero> generos = generoRepository.findAll();
            if(generos != null){
                for(Genero gen : generos){
                    ArrayList<Map<String,String>> users = new ArrayList<>();
                    Double valoration = new Double(0);
                    Long numTweets =new Long(0);
                    System.out.println("Genero=> "+gen.getNombre());
                    peliculas = gen.getPeliculas();
                    Map<String,String> user ;
                    for(Pelicula peli: peliculas){
                        System.out.println("Pelicula: "+peli.getNombre());
                        for(KeyWord keyWord: peli.getKeywords()){
                            System.out.println("Keyword => "+keyWord.getPalabra());
                            String key = keyWord.getPalabra();
                            tweets = searchIndex(key,1);
                            //System.out.println("pelicula: " + key);
                            for (Map<String,String> t : tweets) {
                                numTweets++;
                                valoration += (Long.parseLong(t.get("num_followers")) *0.5);
                                valoration += (Long.parseLong(t.get("retweetedCount")) *0.5) ;
                                user = new HashMap<>();
                                //System.out.println("id_user => "+t.get("id_user"));
                                user.put("id_user",t.get("id_user"));
                                user.put("name_user",t.get("name_user"));
                                user.put("num_followers",t.get("num_followers"));
                                users.add(user); //almaceno los usuarios
                            }
                        }
                    }
                    if(numTweets != 0){
                        valoration = valoration/numTweets; //Esta se tiene que guardar en el genero.
                    }

                    Collections.sort(users,new SortByFollowers());
                    String fiveMostFollowed = mapIdUsers(users); //Tambien se guarda en el genero
                    gen.setFiveMostFollowedTwitters(fiveMostFollowed);
                    gen.setValNeo4j(valoration);
                    generoRepository.save(gen);
                    usersToOut.add(users.subList(0,5));
                }
            }
        }
        return usersToOut;
    }


    private String mapIdUsers(ArrayList<Map<String,String>> users){
         String mappedUsers = "";
         int max = users.size()<5 ? users.size() : 5;

         for(int x= 0; x < max ; x++){
             Map<String,String> user = users.get(x);
             mappedUsers+= (user.get("id_user")+"|");
             if(x == (max-1)){
                 mappedUsers+=(user.get("id_user"));
             }
         }
        //System.out.println("mapped users => "+ mappedUsers);
         return mappedUsers;
    }

    //@Autowired
    public void exec_lucene(PeliculaRepository peliculaRepository, EstadisticaRepository estadisticaRepository,
                            GeneroRepository generoRepository) {
        sat = new SentimentAnalysisTweets(peliculaRepository,estadisticaRepository);
        MongoClient myMongo;
        myMongo = new MongoClient("localhost:27017");
        DB db = myMongo.getDB("twitternew");
        DBCollection dbCollection = db.getCollection("statusJSONImpl");
        DBCursor db_cursor = dbCollection.find();
        ArrayList<Map<String,String>> tweets;
        List<KeyWord> keyWords = this.keyWordRepository.findAll();
        ArrayList<String> keywords = new ArrayList<>();
        for(KeyWord keyWord: keyWords){
            keywords.add(keyWord.getPalabra());
        }
        double pos = 0.0;
        double neg = 0.0;
        double stat = 0.0;
        System.out.println("antes del index");
        boolean isIndexReady = createIndex(db_cursor);
        System.out.println("despues del index");
        if (isIndexReady) {
            //Hay que cambiar esto y dejarlo por cada pelicula, que entonces
            //lo haga por cada una de las keywords...! ya que asi se pueden sumar
            //las estadisticas resultantes, se busca la id de la pelicula y se
            //guarda el total de la estadistica. capishi? !!
            List<Pelicula> peliculas = peliculaRepository.findAll();
            if(peliculas != null){
                for(Pelicula pelicula: peliculas){
                    Long numTweets = new Long(0);
                    //System.out.println("Pelicula: "+pelicula.getNombre());
                    ArrayList<Tweet> tweets_obj = new ArrayList<>();
                    for (KeyWord keyword : pelicula.getKeywords()) {
                        String key = keyword.getPalabra();
                        tweets = searchIndex(key,0);
                        //System.out.println("pelicula: " + key);
                        for (Map<String,String> t : tweets) {
                            Tweet tw = new Tweet();
                            numTweets++;
                            try {

                                Double value = sat.getAnalysis(t.get("text"));
                                t.put("value",Double.toString(value));
                                //System.out.println("tw_user=> "+t.get("id"));
                                tw.setId(t.get("id"));
                                tw.setText(t.get("text"));
                                tw.setValue(value);
                                tweets_obj.add(tw);
                                if (value > 0) {
                                    //System.out.println("Positiva");
                                    pos++;
                                }
                                else neg++;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            //System.out.println("\n");
                        }
                    }

                    //Ordenar los tweets segun la valoracion
                    Collections.sort(tweets_obj,new SortByValue());
                    int x = 0;
                    List<String> idsTweets = new ArrayList<>();
                    for(Tweet tweet: tweets_obj){
                        if(x == 5) break;
                        idsTweets.add(tweet.getId());
                        x++;
                    }
                    System.out.println("Pelicula: "+pelicula.getNombre());
                    pelicula.fiveTweets(idsTweets);
                    Estadistica estadistica = new Estadistica();

                    if((pos == 0) && (neg == 0)){
                        stat = 0;
                    }else{
                        stat = (pos * 100) / (pos + neg);
                    }
                    estadistica.setAprobacion((float) stat);
                    estadistica.setNumTweets(numTweets);
                    Long id = pelicula.getId();
                    pelicula.setNumTweets(numTweets);
                    System.out.println("estadistica: " + stat);
                    pelicula.setValue((Double) stat);
                    System.out.println("ID: "+id);
                    if (sat.postEstadistica(estadistica, id)) System.out.println("Estadistica subida con exito");
                    else System.out.printf("No se logro subir la estadistica");
                    pos = 0.0;
                    neg = 0.0;
                }
            }
        }

        List<Genero> generos = generoRepository.findAll();
        for(Genero genero: generos){
            Double statics = new Double(0);
            List<Pelicula> peliculas =genero.getPeliculas();
            for(Pelicula pelicula: peliculas){
                statics += pelicula.getValue();
            }
            if(peliculas.size() >0){
                statics = statics / peliculas.size();
            }
            genero.setValorizacion(statics);
            generoRepository.save(genero);
        }
    }



}

class SortByFollowers implements Comparator<Map<String,String>>{

    @Override
    public int compare(Map<String, String> o1, Map<String, String> o2) {
        return -((int)(Long.parseLong(o1.get("num_followers")) - Long.parseLong(o2.get("num_followers"))));
    }
}

class SortByValue implements Comparator<Tweet>{

    @Override
    public int compare(Tweet o1, Tweet o2) {
        return o2.getValue().intValue() - o1.getValue().intValue();
    }
}