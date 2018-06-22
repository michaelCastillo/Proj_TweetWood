package com.grupo1.tweetwood_back.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String nombre;
    private String restriccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;


    @ManyToMany( mappedBy = "peliculas", cascade = CascadeType.ALL)
    private List<Genero> generos;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<KeyWord> keywords;

    @OneToMany(mappedBy = "pelicula")
    private List<Estadistica> estadisticas;

    public List<Estadistica> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(List<Estadistica> estadisticas) {
        this.estadisticas = estadisticas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<KeyWord> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<KeyWord> keywords) {
        this.keywords = keywords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRestriccion() {
        return restriccion;
    }

    public void setRestriccion(String restriccion) {
        this.restriccion = restriccion;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }
}
