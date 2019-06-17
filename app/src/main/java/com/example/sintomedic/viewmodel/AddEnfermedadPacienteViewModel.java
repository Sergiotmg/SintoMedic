package com.example.sintomedic.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Enfermedad;
import com.example.sintomedic.viewmodel.model.NewEnfermedadPaciente;

import java.util.Date;

public class AddEnfermedadPacienteViewModel extends ViewModel {

    public final MutableLiveData<String> nombre = new MutableLiveData<>();
    public final MutableLiveData<String> descripcion = new MutableLiveData<>();

    private final MutableLiveData<NewEnfermedadPaciente> _newEnfermedad = new MutableLiveData<>();

    public final LiveData<Resource<Enfermedad>> enfermedad = Transformations.switchMap(
            _newEnfermedad, new Function<NewEnfermedadPaciente, LiveData<Resource<Enfermedad>>>() {
                @Override
                public LiveData<Resource<Enfermedad>> apply(NewEnfermedadPaciente input) {
                    return Controller.createEnfermedad(input.getPacienteId(), input.getEnfermedad());
                }
            }
    );

    public void saveEnfermedad(Long pacienteId) {
        Enfermedad enfermedad = new Enfermedad();
        enfermedad.setNombre(nombre.getValue());
        enfermedad.setDescripcion(descripcion.getValue());
        enfermedad.setFechaInicio(new Date());

        // TODO: validaciones
        NewEnfermedadPaciente newEnfermedadPaciente = new NewEnfermedadPaciente(
                pacienteId, enfermedad
        );
        _newEnfermedad.setValue(newEnfermedadPaciente);
    }

}
