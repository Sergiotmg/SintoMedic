package com.example.sintomedicCasa.adapters;

import com.example.sintomedicCasa.model.Usuario;

public interface PacienteAdapterListener {

    void onShowFichaPaciente(Usuario paciente);

    void onDeletePaciente(Usuario paciente);

}
