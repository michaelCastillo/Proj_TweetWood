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
    private Double valNeo4j;
    private String fiveMostFollowedTwitters;

    @Transient
    private Long numLikes;
    @Transient
    private Long numRetweets;
    @Transient
    private Long numFollowers;
    @Transient
    private Long numTweets;
    @ManyToMany
    @JsonIgnore
    @Nullable
    @JoinTable(name="peliculas_generos",joinColumns = @JoinColumn(name = "id_genero"), inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private List<Pelicula> peliculas;


    public Long getNumTweets() {
        return numTweets;
    }

    public void setNumTweets(Long numTweets) {
        this.numTweets = numTweets;
    }

    public Double getValNeo4j() {
        return valNeo4j;
    }

    public void setValNeo4j(Double valNeo4j) {
        this.valNeo4j = valNeo4j;
    }

    public String getFiveMostFollowedTwitters() {
        return fiveMostFollowedTwitters;
    }

    public void setFiveMostFollowedTwitters(String fiveMostFollowedTwitters) {
        this.fiveMostFollowedTwitters = fiveMostFollowedTwitters;
    }

    public Long getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(Long numFollowers) {
        this.numFollowers = numFollowers;
    }

    public Long getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(Long numLikes) {
        this.numLikes = numLikes;
    }

    public Long getNumRetweets() {
        return numRetweets;
    }

    public void setNumRetweets(Long numRetweets) {
        this.numRetweets = numRetweets;
    }

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
