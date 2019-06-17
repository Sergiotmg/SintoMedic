package com.example.sintomedicCasa.model;

import java.io.Serializable;

public class Role implements Serializable {

    public static final Long DOCTOR_ROLE_ID = 1L;
    public static final Long PACIENTE_ROLE_ID = 2L;

    private Long id;
    private String nombre;

    public Role() {

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

}
