package com.grupo1.tweetwood_back.services;


import com.grupo1.tweetwood_back.modules.Genero;
import com.grupo1.tweetwood_back.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(method = RequestMethod.POST)
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






}
