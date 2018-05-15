package com.grupo1.tweetwood_back.services;

import com.grupo1.tweetwood_back.modules.Estadistica;
import com.grupo1.tweetwood_back.repositories.EstadisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/estadisticas")
public class EstadisticaService {

    @Autowired
    private EstadisticaRepository estadisticaRepository;



    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Estadistica> getEstadistica(){
        return this.estadisticaRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Estadistica getEstadisticaById(@PathVariable Long id){
        return this.getEstadisticaById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Estadistica createEstadistica(Estadistica estadistica){
        return this.estadisticaRepository.save(estadistica);
    }

}
