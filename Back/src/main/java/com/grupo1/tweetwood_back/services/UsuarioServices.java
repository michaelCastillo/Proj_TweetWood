package com.grupo1.tweetwood_back.services;

import com.grupo1.tweetwood_back.modules.Usuario;
import com.grupo1.tweetwood_back.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioServices {
    @Autowired
    public UsuarioRepository usuarioRepository;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Usuario> getUsuarios(){
        return this.usuarioRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Usuario createUsuario(@RequestBody Usuario usuario){

        System.out.println("asd");
        return this.usuarioRepository.save(usuario);
    }


    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Usuario getUsuarioById(@PathVariable Long id){
        return this.usuarioRepository.findUsuarioById(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/login/{usuario}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public Long isUser(@PathVariable String usuario, @PathVariable String password){
        Usuario user = usuarioRepository.findByCorreo(usuario, password);
        if(user != null)
            return user.getId();
        else
            return Long.valueOf(-1);
    }
}
