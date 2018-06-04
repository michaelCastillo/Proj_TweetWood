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

    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    @ResponseBody
    public int getPeliculaByName(@PathVariable("name") String name){
        List<Pelicula> films = getPeliculas();
        //name = name.toLowerCase().replaceAll("\\s+","");
        System.out.println("pelicula casteada: " + name);
        for(Pelicula p : films){
            String film = p.getNombre().toLowerCase().replaceAll("\\s+","");
            System.out.println("pelicula en bd: " + film);
            if(film.equals(name.toLowerCase()))
                return p.getId().intValue();
        }
        return -1;
    }

    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    @ResponseBody
    public Pelicula createPeliculas(@RequestBody Pelicula pelicula){
        return this.peliculaRepository.save(pelicula);
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    @ResponseBody
    public Pelicula deletePelicula(@PathVariable("id") Long id){
        Pelicula film = peliculaRepository.findPeliculaById(id);
        peliculaRepository.delete(film);
        return film;
    }





}
