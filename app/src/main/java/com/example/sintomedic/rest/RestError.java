package com.example.sintomedic.rest;

import java.io.Serializable;
import java.util.Date;

public class RestError implements Serializable {

    private Date fecha;
    private String mensaje;
    private int codigo;

    public RestError() {

    }

    public RestError(Date fecha, String mensaje, int codigo) {
        this.fecha = fecha;
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

}
