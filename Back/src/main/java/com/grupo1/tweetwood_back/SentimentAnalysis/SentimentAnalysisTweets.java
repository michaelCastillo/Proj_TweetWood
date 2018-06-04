package com.grupo1.tweetwood_back.SentimentAnalysis;

import com.grupo1.tweetwood_back.modules.Estadistica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import twitter4j.HttpParameter;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SentimentAnalysisTweets {
    //@Autowired
    public double getAnalysis(String tweet) throws UnsupportedEncodingException {
        String encodeUrl = "http://localhost:8080/classify?text="+ URLEncoder.encode(tweet,"UTF-8")+"/";
        System.out.println(encodeUrl);
        String out;
        Double value;
        try{
            URL url = new URL(encodeUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new RuntimeException("HTTP error code: " +  conn.getResponseCode());
            //
            BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = bf.readLine();
            JSONObject object = new JSONObject(out);
            if((double)object.get("negative") > (double)object.get("positive")){
                conn.disconnect();
                return (double)object.get("negative")*-1;
            }
            else {
                conn.disconnect();
                return (double) object.get("positive");
            }
        }
        catch(MalformedURLException e){
            System.out.println(e);
            return -1.0;
        }
        catch(IOException e){
            e.printStackTrace();
            return -1.0;
        }
        catch(JSONException e){
            e.printStackTrace();
            return -1.0;
        }
    }

    public int getIdPelicula(String pelicula){
        String out;
        try{
            pelicula = pelicula.replaceAll("\\s+","").toLowerCase();
            URL url = new URL("http://localhost:1310/peliculas/search/"+pelicula);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new RuntimeException("HTTP error code: " +  conn.getResponseCode());
            //
            BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = bf.readLine();
            //JSONObject object = new JSONObject(out);
            conn.disconnect();
            return Integer.parseInt(out);

        }
        catch(MalformedURLException e){
            System.out.println(e);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    public boolean postEstadistica(Estadistica estadistica, int id){
        String sid = String.valueOf(id);
        String[] names = {"tipo","aprobacion","localizacion","fecha"};
        String url = "http://206.189.24.139:8080/tweetwood_back-0.0.1-SNAPSHOT/estadisticas/create"+sid;
        try{
            URL url2 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url2.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "application/json");
            JSONObject obj = new JSONObject(estadistica,names);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(obj.toString());
            wr.flush();
            wr.close();
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new RuntimeException("HTTP error code: " +  conn.getResponseCode());
        }
        catch(MalformedURLException e){
            System.out.println(e);
            return false;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
