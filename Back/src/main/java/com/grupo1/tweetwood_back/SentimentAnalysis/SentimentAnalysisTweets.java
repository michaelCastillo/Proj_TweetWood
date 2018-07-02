package com.grupo1.tweetwood_back.SentimentAnalysis;

import com.grupo1.tweetwood_back.modules.Estadistica;
import com.grupo1.tweetwood_back.modules.Pelicula;
import com.grupo1.tweetwood_back.repositories.EstadisticaRepository;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
import com.grupo1.tweetwood_back.services.PeliculaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
import java.util.List;

@Component
@Service
public class SentimentAnalysisTweets {
    //@Autowired

    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private EstadisticaRepository estadisticaRepository;

    public SentimentAnalysisTweets(PeliculaRepository peliculaRepository, EstadisticaRepository estadisticaRepository){
        this.peliculaRepository = peliculaRepository;
        this.estadisticaRepository = estadisticaRepository;
    }

    public double getAnalysis(String tweet) throws UnsupportedEncodingException {
        String encodeUrl = "http://localhost:8080/SentimentAnalysis-0.0.1-SNAPSHOT/classify?text="+ URLEncoder.encode(tweet,"UTF-8")+"/";
        //System.out.println(encodeUrl);
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
            //System.out.println("out: " + out);
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

    public Long getIdPelicula(String pelicula){

        PeliculaServices peliculaServices = new PeliculaServices();
        if(this.peliculaRepository.findAll().isEmpty()){
            System.out.println("Esta vacio");
        }
        for(Pelicula pelicula_repo: peliculaRepository.findAll()){
            System.out.println(pelicula_repo.getNombre() + " : " + pelicula);
            if(pelicula_repo.getNombre().compareTo(pelicula) == 0){
                return pelicula_repo.getId();
            }
        }
        return new Long(0);
    }

    public boolean postEstadistica(Estadistica estadistica, Long id_pelicula){

        if(this.peliculaRepository.findPeliculaById(id_pelicula) != null) {
            Pelicula pelicula = this.peliculaRepository.findPeliculaById(id_pelicula);
            estadistica.setPelicula(pelicula);
            List<Estadistica> estadisticas = pelicula.getEstadisticas();
            estadisticas.add(estadistica);
            pelicula.setEstadisticas(estadisticas);
            estadisticaRepository.save(estadistica);
            //System.out.println("POST ESTADISTICA: "+pelicula.getNombre());
            peliculaRepository.save(pelicula);
            return true;
        }else{
            return false;
        }

    }

}
