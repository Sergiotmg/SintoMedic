package com.example.sintomedicCasa.viewmodel.model;

public class EnfermedadPacienteId {

    private long enfermedadId;
    private long pacienteId;

    public EnfermedadPacienteId(long enfermedadId, long pacienteId) {
        this.enfermedadId = enfermedadId;
        this.pacienteId = pacienteId;
    }

    public long getEnfermedadId() {
        return enfermedadId;
    }

    public void setEnfermedadId(long enfermedadId) {
        this.enfermedadId = enfermedadId;
    }

    public long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(long pacienteId) {
        this.pacienteId = pacienteId;
    }

}
