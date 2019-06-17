package com.example.sintomedicCasa.viewmodel.model;

public class SintomaPacienteId {

    private long sintomaId;
    private long pacienteId;

    public SintomaPacienteId(long sintomaId, long pacienteId) {
        this.sintomaId = sintomaId;
        this.pacienteId = pacienteId;
    }

    public long getSintomaId() {
        return sintomaId;
    }

    public void setSintomaId(long sintomaId) {
        this.sintomaId = sintomaId;
    }

    public long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(long pacienteId) {
        this.pacienteId = pacienteId;
    }

}
