package com.example.sintomedic.adapters;

import com.example.sintomedic.model.Usuario;

public interface PacienteAdapterListener {

    void onShowFichaPaciente(Usuario paciente);

    void onDeletePaciente(Usuario paciente);

}
