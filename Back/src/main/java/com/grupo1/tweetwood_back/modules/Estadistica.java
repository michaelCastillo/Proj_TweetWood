package com.grupo1.tweetwood_back.modules;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "estadisticas")
public class Estadistica {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String tipo;
    private Date fecha;
    private float aprobacion;
    private String localizacion;

    @ManyToOne
    @JoinColumn(name = "id_pelicula_estadistica")
    @JsonIgnore
    private Pelicula pelicula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getAprobacion() {
        return aprobacion;
    }

    public void setAprobacion(float aprobacion) {
        this.aprobacion = aprobacion;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
}
