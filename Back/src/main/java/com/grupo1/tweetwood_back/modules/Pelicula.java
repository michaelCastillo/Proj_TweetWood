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

    private String fiveTweets;
    private Long numTweets;
    private Long idApi;
    private String nombre;
    private String restriccion;
    private String img;
    private boolean disponible;
    private Double value;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    public Pelicula(){
        this.value = new Double(0);
        this.numTweets = new Long(0);
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getFiveTweets() {
        return this.fiveTweets;
    }
    public void setFiveTweets(String fiveTweets){
        this.fiveTweets = fiveTweets;
    }

    public void fiveTweets(List<String> fiveTweets) {
        String aux = "";
        for(int x = 0; x<fiveTweets.size(); x++){
            if(x==(fiveTweets.size()-1)){
                aux+=fiveTweets.get(x);
            }else{
                System.out.println("fiveTweet: "+fiveTweets.get(x));
                aux+=fiveTweets.get(x)+"|";
            }
        }
        this.fiveTweets = aux;
        System.out.println(aux);
    }

    public void tweetSet(int index, String id) {
        String[] ids = this.fiveTweets.split("|");
        ids[index] = id;
        String aux = "";
        for (int x = 0; x < ids.length; x++) {
            if (x == (ids.length-1)) {
                aux += ids[x];
            } else {
                aux += ids[x] + "|";
            }
        }
        this.fiveTweets = aux;
        System.out.println(aux);
    }

    public Long getNumTweets() {
        return numTweets;
    }

    public void setNumTweets(Long numTweets) {
        this.numTweets = numTweets;
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

    public Long getIdApi() {
        return idApi;
    }

    public void setIdApi(Long idApi) {
        this.idApi = idApi;
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
