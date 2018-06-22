package com.grupo1.tweetwood_back.services;

import com.grupo1.tweetwood_back.modules.Estadistica;
import com.grupo1.tweetwood_back.modules.Pelicula;
import com.grupo1.tweetwood_back.repositories.EstadisticaRepository;
import com.grupo1.tweetwood_back.repositories.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/estadisticas")
public class EstadisticaService {

    @Autowired
    private EstadisticaRepository estadisticaRepository;
    @Autowired
    private PeliculaRepository peliculaRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Estadistica> getEstadistica(){
        return this.estadisticaRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Estadistica getEstadisticaById(@PathVariable Long id){
        return this.getEstadisticaById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/create/{id_pelicula}", method = RequestMethod.POST)
    @ResponseBody
    public Estadistica createEstadistica(  @PathVariable Long id_pelicula ,@RequestBody @Valid Estadistica estadistica){

        Pelicula pelicula = peliculaRepository.findPeliculaById(id_pelicula);
        List<Estadistica> estadisticas = pelicula.getEstadisticas();
        estadisticas.add(estadistica);
        pelicula.setEstadisticas(estadisticas);
        estadistica.setPelicula(pelicula);
        return this.estadisticaRepository.save(estadistica);
    }



}
