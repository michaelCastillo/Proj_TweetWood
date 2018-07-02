package com.grupo1.tweetwood_back.modules;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "generos")
public class Genero {




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String nombre;
    private Double valorizacion;
    private boolean disponible;

    @ManyToMany
    @JsonIgnore
    @Nullable
    @JoinTable(name="peliculas_generos",joinColumns = @JoinColumn(name = "id_genero"), inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private List<Pelicula> peliculas;


    public Genero(){
        this.disponible = true;
    }

    public void removePelicula(Pelicula pelicula){
        this.peliculas.remove(pelicula);
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

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

    public Double getValorizacion() {
        return valorizacion;
    }

    public void setValorizacion(Double valorizacion) {
        this.valorizacion = valorizacion;
    }

    public void addPelicula(Pelicula pelicula){
        System.out.println("pelicula: "+pelicula.getNombre());
        if(peliculas == null){
            this.peliculas = new ArrayList<>();
        }
        for(Pelicula pelicula_: this.peliculas){
            System.out.println("Pelicula_gen: "+pelicula_.getNombre());
        }
        if(!peliculas.contains(pelicula)){
            System.out.println("No contiene la pelicula");
            this.peliculas.add(pelicula);
        }

    }

}
