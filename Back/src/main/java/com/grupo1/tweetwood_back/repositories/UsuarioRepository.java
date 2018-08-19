package com.grupo1.tweetwood_back.repositories;

import com.grupo1.tweetwood_back.modules.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findUsuarioById(Long id);

    @Query(value = "Select * From usuarios u where u.correo = :usuario AND u.contrasena = :password", nativeQuery = true)
    Usuario findByCorreo(@Param("usuario")String usuario, @Param("password") String password);
}
