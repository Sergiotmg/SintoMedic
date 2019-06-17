package com.example.sintomedicCasa.model;

import java.io.Serializable;
import java.util.Date;

public class Sintoma implements Serializable {

    private Long id;
    private String descripcion;
    private Date fechaHora;
    private float temperatura;
    private float presionArterialAlta;
    private float presionArterialBaja;
    private String pulso;

    public Sintoma() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getPresionArterialAlta() {
        return presionArterialAlta;
    }

    public void setPresionArterialAlta(float presionArterialAlta) {
        this.presionArterialAlta = presionArterialAlta;
    }

    public float getPresionArterialBaja() {
        return presionArterialBaja;
    }

    public void setPresionArterialBaja(float presionArterialBaja) {
        this.presionArterialBaja = presionArterialBaja;
    }

    public String getPulso() {
        return pulso;
    }

    public void setPulso(String pulso) {
        this.pulso = pulso;
    }

}
