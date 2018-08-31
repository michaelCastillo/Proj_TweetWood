package com.grupo1.tweetwood_back.services;


import com.grupo1.tweetwood_back.lucene.lucene;
import com.grupo1.tweetwood_back.repositories.EstadisticaRepository;
import com.grupo1.tweetwood_back.repositories.GeneroRepository;
import com.grupo1.tweetwood_back.repositories.KeyWordRepository;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/lucene")
public class LuceneService {

    @Autowired
    private PeliculaRepository peliculaRepository;
    @Autowired
    private EstadisticaRepository estadisticaRepository;
    @Autowired
    private KeyWordRepository keyWordRepository;
    @Autowired
    private GeneroRepository generoRepository;

    @CrossOrigin
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    @ResponseBody
    public boolean startLucene(){
        lucene l = new lucene(this.keyWordRepository);
        System.out.println("ejecute");
        l.exec_lucene(this.peliculaRepository,estadisticaRepository,this.generoRepository);
        System.out.println("termine");
        return true;
    }

    @CrossOrigin
    @RequestMapping(value = "/startneofourjay",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> startLuceneNeoFourJay(){
        lucene l = new lucene(this.keyWordRepository);
        System.out.println("ejecute");
        List<List<Map<String,String>>> users = l.getUsers(this.peliculaRepository,estadisticaRepository,this.generoRepository);
        Map<String,Object> response = new HashMap<>();
        response.put("usersData",users);
        response.put("generos",this.generoRepository.findAll());
        System.out.println("termine");
        return response;
    }

    @CrossOrigin
    @RequestMapping(value = "/heatmap/{genero}",method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getHeatMapByGender(@PathVariable String genero){
        lucene l = new lucene(this.keyWordRepository);
        System.out.println("dasdas\n");
        ArrayList<String> res;
        res = l.tweetsbyGender(this.peliculaRepository, genero);
        return res;


    }


}
