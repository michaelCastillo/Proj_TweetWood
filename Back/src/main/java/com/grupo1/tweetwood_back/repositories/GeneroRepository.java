package com.grupo1.tweetwood_back.repositories;

import com.grupo1.tweetwood_back.modules.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    public Genero findGeneroById(Long id);
    public boolean existsGeneroByNombre(String nombre);
}
