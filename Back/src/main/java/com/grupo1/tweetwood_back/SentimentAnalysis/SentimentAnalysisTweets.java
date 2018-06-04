package com.grupo1.tweetwood_back.SentimentAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import twitter4j.HttpParameter;
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
import java.net.URLEncoder;

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
}
