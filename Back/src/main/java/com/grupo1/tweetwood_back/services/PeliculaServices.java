package com.grupo1.tweetwood_back.services;


import com.grupo1.tweetwood_back.modules.Pelicula;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/peliculas")
public class PeliculaServices {

    @Autowired
    private PeliculaRepository peliculaRepository;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Pelicula> getPeliculas(){
        return this.peliculaRepository.findAll();
    }

    @RequestMapping(value = "/{id}/get")
    @ResponseBody
    public Pelicula getPelicula(@PathVariable Long id){
        return this.peliculaRepository.findPeliculaById(id);
    }

    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    @ResponseBody
    public Pelicula createPeliculas(@RequestBody Pelicula pelicula){
        return this.peliculaRepository.save(pelicula);
    }







}
