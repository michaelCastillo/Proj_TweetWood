package com.grupo1.tweetwood_back.services;


import com.grupo1.tweetwood_back.modules.Genero;
import com.grupo1.tweetwood_back.modules.KeyWord;
import com.grupo1.tweetwood_back.modules.Pelicula;
import com.grupo1.tweetwood_back.repositories.GeneroRepository;
import com.grupo1.tweetwood_back.repositories.KeyWordRepository;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/peliculas")
public class PeliculaServices {

    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private KeyWordRepository keyWordRepository;
    @Autowired
    private GeneroRepository generoRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Pelicula> getPeliculas(){
        return this.peliculaRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}")
    @ResponseBody
    public Pelicula getPelicula(@PathVariable Long id){
        return this.peliculaRepository.findPeliculaById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    @ResponseBody
    public Pelicula createPeliculas(@RequestBody Pelicula pelicula){
        List<KeyWord> keyWords = pelicula.getKeywords();
        for(Genero genero: pelicula.getGeneros()){
            genero.addPelicula(pelicula);
        }
        for(KeyWord key: keyWords){
            System.out.println("Key: "+key.getPalabra());
            key.setPelicula(pelicula);
        }
        Pelicula peli = this.peliculaRepository.save(pelicula);
        this.generoRepository.saveAll(pelicula.getGeneros());
        this.keyWordRepository.saveAll(keyWords);
        return peli;
    }

    @CrossOrigin
    @RequestMapping(value = "{id}/getKeywords",method = RequestMethod.GET)
    @ResponseBody
    public List<KeyWord> getKeywords(@PathVariable Long id){
        return this.peliculaRepository.findPeliculaById(id).getKeywords();
    }

    @CrossOrigin
    @RequestMapping(value = "{id}/addKeyword", method = RequestMethod.POST)
    @ResponseBody
    public Pelicula addKeyword(@PathVariable Long id, @Valid @RequestBody  KeyWord keyWord){
        Pelicula pelicula = this.peliculaRepository.findPeliculaById(id);
        keyWord.setPelicula(pelicula);
        this.keyWordRepository.save(keyWord);
        List<KeyWord> keywords = pelicula.getKeywords();
        keywords.add(keyWord);
        pelicula.setKeywords(keywords);
        return pelicula;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/addKeywords", method = RequestMethod.POST)
    @ResponseBody
    public Pelicula addKeywords(@PathVariable Long id, @Valid @RequestBody List<KeyWord> keywords){
        Pelicula pelicula = this.peliculaRepository.findPeliculaById(id);
        List<KeyWord> keywordsFromPelicula = pelicula.getKeywords();
        ArrayList<KeyWord> keyWordsToRemove = new ArrayList<>();

        for( KeyWord keyWord : keywords ){
            if(this.keyWordRepository.existsKeyWordByPalabra(keyWord.getPalabra())){
                keyWordsToRemove.add(keyWord);
            }
        }
        keywords.removeAll(keyWordsToRemove);
        this.keyWordRepository.saveAll(keywords);

        keywordsFromPelicula.addAll(keywords);
        pelicula.setKeywords(keywordsFromPelicula);
        return pelicula;
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/addGeneros")
    @ResponseBody
    public Pelicula addGeneros(@PathVariable Long id, @Valid @RequestBody List<Genero> generos ){
        Pelicula pelicula = this.peliculaRepository.findPeliculaById(id);

        ArrayList<Genero> generosToRemove = new ArrayList<>();

        for(Genero genero: generos){
            //Si no esta en el repositorio quiere decir que no existe.
            if(!this.generoRepository.existsGeneroByNombre(genero.getNombre())){
                generosToRemove.add(genero);
            }else{
                if(genero.getPeliculas() == null){
                    ArrayList<Pelicula> peliculas = new ArrayList<>();
                    peliculas.add(pelicula);
                    genero.setPeliculas(peliculas);
                }else{
                    List<Pelicula> peliculas = genero.getPeliculas();
                    peliculas.add(pelicula);
                    genero.setPeliculas(peliculas);
                }

            }
        }
        //Se remueven aquellos que no existen
        generos.removeAll(generosToRemove);
        List<Genero> generosFromPelicula = pelicula.getGeneros();
        generosFromPelicula.addAll(generos);

        return pelicula;
    }
    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePelicula(@RequestBody Map<String,String> msg){
        Long id_pelicula = Long.parseLong(msg.get("id_pelicula"));
        this.peliculaRepository.delete(this.peliculaRepository.findPeliculaById(id_pelicula));
    }
    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,Object> updatePelicula(@RequestBody Pelicula pelicula){
        Pelicula peliculaFromRepo;
        Map<String,Object> response = new HashMap<>();
        if( (peliculaFromRepo = this.peliculaRepository.findPeliculaById(pelicula.getId()) )!= null){
            //Si existe la pelicula
            for(KeyWord keyword : pelicula.getKeywords()){
                System.out.println("Keyword: "+keyword.getPalabra());
                keyword.setPelicula(peliculaFromRepo);
            }
            //Se guardan los generos
            for(Genero genero: pelicula.getGeneros()){
                System.out.println("genero: "+genero.getNombre());
                genero.addPelicula(pelicula);
            }
            //Se guardan las keywords
            this.generoRepository.saveAll(pelicula.getGeneros());
            this.keyWordRepository.saveAll(pelicula.getKeywords());
            this.peliculaRepository.save(pelicula);
            response.put("status","OK! updated");
        }else{
            response.put("status","Error not updated");
        }
        response.put("pelicula",peliculaFromRepo);
        return  response;


    }









}
