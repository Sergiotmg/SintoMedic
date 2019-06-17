package com.example.sintomedic.model;

import java.io.Serializable;

public class Tratamiento implements Serializable {

    private Long id;
    private String contenido;

    public Tratamiento() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}
