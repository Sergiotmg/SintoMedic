package com.example.sintomedicCasa.viewmodel.model;

import com.example.sintomedicCasa.model.Sintoma;

public class NewSintomaPaciente {

    private Long pacienteId;
    private Sintoma sintoma;

    public NewSintomaPaciente(Long pacienteId, Sintoma sintoma) {
        this.pacienteId = pacienteId;
        this.sintoma = sintoma;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Sintoma getSintoma() {
        return sintoma;
    }

    public void setSintoma(Sintoma sintoma) {
        this.sintoma = sintoma;
    }

}
