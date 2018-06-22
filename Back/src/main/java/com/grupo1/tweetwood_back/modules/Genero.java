package com.grupo1.tweetwood_back.modules;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "generos")
public class Genero {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String nombre;
    private int valorizacion;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name="peliculas_generos",joinColumns = @JoinColumn(name = "id_pelicula"), inverseJoinColumns = @JoinColumn(name = "id_genero"))
    private List<Pelicula> peliculas;





    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
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

    public int getValorizacion() {
        return valorizacion;
    }

    public void setValorizacion(int valorizacion) {
        this.valorizacion = valorizacion;
    }
}
