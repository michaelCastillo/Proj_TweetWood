package com.grupo1.tweetwood_back.repositories;

import com.grupo1.tweetwood_back.modules.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    public Pelicula findPeliculaById(Long id);
    public Pelicula deletePeliculaById(Long id);
    public Pelicula findPeliculaByNombre(String nombre);


}
