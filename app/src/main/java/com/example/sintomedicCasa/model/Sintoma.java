package com.example.sintomedicCasa.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Sintoma implements Serializable {

    private Long id;
    private String descripcion;
    private Date fechaHora;
    private Float temperatura;
    private Float presionArterialAlta;
    private Float presionArterialBaja;
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

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Float getPresionArterialAlta() {
        return presionArterialAlta;
    }

    public void setPresionArterialAlta(Float presionArterialAlta) {
        this.presionArterialAlta = presionArterialAlta;
    }

    public Float getPresionArterialBaja() {
        return presionArterialBaja;
    }

    public void setPresionArterialBaja(Float presionArterialBaja) {
        this.presionArterialBaja = presionArterialBaja;
    }

    public String getPulso() {
        return pulso;
    }

    public void setPulso(String pulso) {
        this.pulso = pulso;
    }

    public static void sortSintomasByDate(List<Sintoma> sintomas) {
        Collections.sort(sintomas, new Comparator<Sintoma>() {
            @Override
            public int compare(Sintoma sintoma, Sintoma s2) {
                return s2.getFechaHora().compareTo(sintoma.getFechaHora());
            }
        });
    }

}
