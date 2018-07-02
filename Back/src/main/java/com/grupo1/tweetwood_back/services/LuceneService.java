package com.grupo1.tweetwood_back.services;


import com.grupo1.tweetwood_back.lucene.lucene;
import com.grupo1.tweetwood_back.repositories.EstadisticaRepository;
import com.grupo1.tweetwood_back.repositories.GeneroRepository;
import com.grupo1.tweetwood_back.repositories.KeyWordRepository;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/start",method = RequestMethod.POST)
    @ResponseBody
    public void startLucene(){
        lucene l = new lucene(this.keyWordRepository);
        l.exec_lucene(this.peliculaRepository,estadisticaRepository,this.generoRepository);
    }


}
