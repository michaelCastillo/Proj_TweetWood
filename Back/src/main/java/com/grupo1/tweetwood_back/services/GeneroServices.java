package com.grupo1.tweetwood_back.services;


import com.grupo1.tweetwood_back.modules.Genero;
import com.grupo1.tweetwood_back.modules.Pelicula;
import com.grupo1.tweetwood_back.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import sun.security.x509.GeneralName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/generos")
public class GeneroServices {


    @Autowired
    public GeneroRepository generoRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Genero> getAllGeneros(){
        return this.generoRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/disponibles",method = RequestMethod.GET)
    @ResponseBody
    public List<Genero> getGenerosDisponibles(){
        List<Genero> generos = this.generoRepository.findAll();
        List<Genero> generosDisponibles = new ArrayList<>();
        for(Genero genero: generos){
            if(genero.isDisponible()){
                generosDisponibles.add(genero);
            }
        }
        return generosDisponibles;
    }

    @CrossOrigin
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public Genero createGenero(@RequestBody Genero genero){
        return this.generoRepository.save(genero);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Genero getGenero(@PathVariable Long id){
        return this.generoRepository.findGeneroById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Genero updateGenero(@PathVariable Long id, @RequestBody Genero genero){
        Genero gen = this.generoRepository.findGeneroById(id);
        gen.setNombre(genero.getNombre());
        return this.generoRepository.save(gen);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String,String> deleteGenero(@RequestBody Map<String,Long> gen){
        System.out.println("gen: "+gen);
        Genero genero = this.generoRepository.findGeneroById(gen.get("id_genero"));
        Map<String,String> response = new HashMap<>();
        if(genero != null) {
            genero.setDisponible(false);
            this.generoRepository.save(genero);
            response.put("status","Deleted");
        }else{
            response.put("status","Error: doesn't exist");
        }
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/setDisponible",method = RequestMethod.PUT)
    @ResponseBody
    public Map<String,String> setGeneroDisponible(@RequestBody Map<String,Long> idGen){
        Map<String,String> response = new HashMap<>();
        Genero genero = this.generoRepository.findGeneroById(idGen.get("id_genero"));
        if(genero != null){
            genero.setDisponible(true);
            this.generoRepository.save(genero);
            response.put("status","Disponible "+idGen.get("id_genero"));
        }else{
            response.put("status","doesn't exist");
        }
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/deleteAll",method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteAll(){
        this.generoRepository.deleteAll();
    }


    @CrossOrigin
    @RequestMapping(value = "{id}/getMovies",method = RequestMethod.GET)
    @ResponseBody
    public List<Pelicula> getPeliculas(@PathVariable Long id){
        Genero genero = this.generoRepository.findGeneroById(id);
        return genero.getPeliculas();
    }



}
