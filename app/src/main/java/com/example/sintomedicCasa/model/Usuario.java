package com.example.sintomedicCasa.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable {

    private Long id;
    private List<Role> roles = new ArrayList<>();
    private String dniNie;
    private String correo;
    private String contrasenia;
    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private String companiaAseguradora;
    private String telefono;
    private String localidad;
    private String linkFotoPerfil;

    /* Propiedades Doctor */

    private boolean solicitaSerDoctor = false;
    private String centro;
    private String numColegiado;

    public Usuario() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getDniNie() {
        return dniNie;
    }

    public void setDniNie(String dniNie) {
        this.dniNie = dniNie;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCompaniaAseguradora() {
        return companiaAseguradora;
    }

    public void setCompaniaAseguradora(String companiaAseguradora) {
        this.companiaAseguradora = companiaAseguradora;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getLinkFotoPerfil() {
        return linkFotoPerfil;
    }

    public void setLinkFotoPerfil(String linkFotoPerfil) {
        this.linkFotoPerfil = linkFotoPerfil;
    }

    public boolean isSolicitaSerDoctor() {
        return solicitaSerDoctor;
    }

    public void setSolicitaSerDoctor(boolean solicitaSerDoctor) {
        this.solicitaSerDoctor = solicitaSerDoctor;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(String numColegiado) {
        this.numColegiado = numColegiado;
    }

    public boolean isDoctor() {
        for (Role role : roles) {
            if (role.getId() == Role.DOCTOR_ROLE_ID) {
                return true;
            }
        }

        return false;
    }

    public boolean isPaciente() {
        for (Role role : roles) {
            if (role.getId() == Role.PACIENTE_ROLE_ID) {
                return true;
            }
        }

        return false;
    }

}
