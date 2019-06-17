package com.example.sintomedic.viewmodel.model;

import com.example.sintomedic.model.Tratamiento;

public class UpdateTratamientoPaciente {

    private Long pacienteId;
    private Tratamiento tratamiento;

    public UpdateTratamientoPaciente(Long pacienteId, Tratamiento tratamiento) {
        this.pacienteId = pacienteId;
        this.tratamiento = tratamiento;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

}
