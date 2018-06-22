package com.grupo1.tweetwood_back.services;


import com.grupo1.tweetwood_back.modules.KeyWord;
import com.grupo1.tweetwood_back.repositories.KeyWordRepository;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/keywords")
public class KeyWordServices {

    @Autowired
    public KeyWordRepository keyWordRepository;

    private PeliculaRepository peliculaRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<KeyWord> getGeneros(){
        return this.keyWordRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public KeyWord createKeyWord(@RequestBody KeyWord keyword){
        return this.keyWordRepository.save(keyword);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public KeyWord getKeyWord(@PathVariable Long id){
        return this.keyWordRepository.findKeyWordById(id);
    }


    @CrossOrigin
    @RequestMapping (value = "/createMany")
    @ResponseBody
    public List<KeyWord> createMany(@RequestBody List<KeyWord> keywords){
        return this.keyWordRepository.saveAll(keywords);
    }


}
