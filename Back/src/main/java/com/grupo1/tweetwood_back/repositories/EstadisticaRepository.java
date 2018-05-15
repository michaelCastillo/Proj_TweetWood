package com.grupo1.tweetwood_back.repositories;

import com.grupo1.tweetwood_back.modules.Estadistica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadisticaRepository extends JpaRepository<Estadistica,Long> {

    public Estadistica getEstadisticaById(Long id);


}
