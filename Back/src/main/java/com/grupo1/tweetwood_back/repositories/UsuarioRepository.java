package com.grupo1.tweetwood_back.repositories;

import com.grupo1.tweetwood_back.modules.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findUsuarioById(Long id);
}
