package com.example.sintomedic.viewmodel.model;

import com.example.sintomedic.model.Enfermedad;

public class NewEnfermedadPaciente {

    private Long pacienteId;
    private Enfermedad enfermedad;

    public NewEnfermedadPaciente(Long pacienteId, Enfermedad enfermedad) {
        this.pacienteId = pacienteId;
        this.enfermedad = enfermedad;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Enfermedad getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(Enfermedad enfermedad) {
        this.enfermedad = enfermedad;
    }

}
