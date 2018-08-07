package com.tbd.twitter;

import javax.annotation.PostConstruct;

//import com.sun.xml.internal.ws.server.DefaultResourceInjector;

//import com.sun.xml.internal.ws.api.ResourceLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.DefaultResourceLoader;
//import sun.misc.Resource;
import twitter4j.FilterQuery;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONArray;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.json.DataObjectFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;

@Service
@Configurable
public class TwitterListener {
	@Autowired
	private TwitterStream twitterStream;
	@Autowired
	private MongoTemplate mongo;
		
	@PostConstruct
	public void run() {
		twitterStream.addListener(new StatusListener() {
			public void onStatus(Status status) {
				mongo.insert(status);
	        }

			@Override
			public void onException(Exception arg0) {
								
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
							
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
								
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
								
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
								
			}			
		});
		
		try{
			ArrayList<String> words = new ArrayList<String>();
			words = getKeywords();
			int s = words.size();
			String[] wordsToFilter = new String[s];
			for(int i = 0; i < s; i++)
				wordsToFilter[i] = words.get(i);
			for(int i = 0; i < s; i++)
				System.out.println(wordsToFilter[i]);
			double[][] stgoCoordinates = {{-70.656962,-33.936031},{-70.510731,-33.093747}};
			FilterQuery filter=new FilterQuery(0,null,wordsToFilter,stgoCoordinates,new String[]{"es"});
			//filter.track(new String[]{"estrenos","peliculas"});
			//filter.track(wordsToFilter).locations(stgoCoordinates);

			//filter.track(wordsToFilter);
			//filter.language(new String[]{"es"});
			twitterStream.filter(filter);
		}
		catch(Exception e){
			System.out.println(e);
		}
		
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
                System.out.println("palabra=> "+object.getString("palabra") );
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

	public TwitterStream getTwitterStream() {
		return twitterStream;
	}

	public void setTwitterStream(TwitterStream twitterStream) {
		this.twitterStream = twitterStream;
	}

	public MongoTemplate getMongo() {
		return mongo;
	}

	public void setMongo(MongoTemplate mongo) {
		this.mongo = mongo;
	}
}
