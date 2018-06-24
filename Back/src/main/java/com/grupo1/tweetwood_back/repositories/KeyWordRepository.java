package com.grupo1.tweetwood_back.repositories;

import com.grupo1.tweetwood_back.modules.KeyWord;
import com.grupo1.tweetwood_back.modules.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyWordRepository extends JpaRepository<KeyWord, Long> {
    public KeyWord findKeyWordById(Long id);
    public boolean existsKeyWordByPalabra(String palabra);
    public boolean existsKeyWordByPalabraAndAndPelicula(String keyword, Pelicula pelicula);
}
