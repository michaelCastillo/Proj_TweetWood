package com.grupo1.tweetwood_back.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentParser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String nombre;
    private String restriccion;
    private boolean disponible;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    public Pelicula(){
        this.disponible = true;
    }

    @ManyToMany( mappedBy = "peliculas")
    private List<Genero> generos;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL)
    private List<KeyWord> keywords;

    @OneToMany(mappedBy = "pelicula")
    private List<Estadistica> estadisticas;

    public List<Estadistica> getEstadisticas() {
        return estadisticas;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
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


        List<Genero> generosDisponibles = new ArrayList<>();
        for(Genero genero: generos){
            if(genero.isDisponible()){
                generosDisponibles.add(genero);
            }
        }
        return generosDisponibles;
    }

    public void addKeyWord(KeyWord keyWord){
        this.keywords.add(keyWord);
    }


    public void setGeneros(List<Genero> generos) {
        //Los generos que entran son los que queremos mantener.

        this.generos = null;
        this.generos = generos;
    }



    public void removeGenero(Genero genero){
        this.generos.remove(genero);
    }

    public void addGenero(Genero genero) {
        for(Genero genero_:this.generos ){
            System.out.println("id: "+genero_.getId());
        }
        if(!this.generos.contains(genero)){
            this.generos.add(genero);
            System.out.println("No contiene el genero");
        }else{
            System.out.println("Ya contiene el genero");
        }
    }

    public void removeKeyWord(KeyWord keyWord) {
        this.keywords.remove(keyWord);
    }
}
