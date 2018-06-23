package com.grupo1.tweetwood_back.services;


import com.grupo1.tweetwood_back.modules.Genero;
import com.grupo1.tweetwood_back.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public Genero updateGenero(@RequestBody Genero genero){
        return this.generoRepository.save(genero);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteGenero(@RequestBody Map<String,String> gen){
        System.out.println(gen);
        this.generoRepository.deleteGeneroById(Long.parseLong(gen.get("id_genero")));
    }




}
