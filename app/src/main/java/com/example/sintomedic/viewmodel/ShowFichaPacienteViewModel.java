package com.example.sintomedic.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Sintoma;
import com.example.sintomedic.model.Tratamiento;
import com.example.sintomedic.model.Usuario;
import com.example.sintomedic.viewmodel.model.UpdateTratamientoPaciente;

import java.util.List;

public class ShowFichaPacienteViewModel extends ViewModel {

    public final MutableLiveData<Long> pacienteId = new MutableLiveData<>();

    public final LiveData<Resource<Usuario>> paciente = Transformations.switchMap(
            pacienteId, new Function<Long, LiveData<Resource<Usuario>>>() {
                @Override
                public LiveData<Resource<Usuario>> apply(Long input) {
                    return Controller.getUsuario(input);
                }
            }
    );

    public final MutableLiveData<String> tratamiento = new MutableLiveData<>();

    public final LiveData<Resource<Tratamiento>> tratamientoResource = Transformations.switchMap(
            pacienteId, new Function<Long, LiveData<Resource<Tratamiento>>>() {
                @Override
                public LiveData<Resource<Tratamiento>> apply(Long input) {
                    return Controller.getTratamiento(input);
                }
            }
    );

    private final MutableLiveData<UpdateTratamientoPaciente> _updateTratamiento = new MutableLiveData<>();
    public final LiveData<Resource<Tratamiento>> tratamientoUpdate = Transformations.switchMap(
            _updateTratamiento, new Function<UpdateTratamientoPaciente, LiveData<Resource<Tratamiento>>>() {
                @Override
                public LiveData<Resource<Tratamiento>> apply(UpdateTratamientoPaciente input) {
                    return Controller.updateTratamiento(input.getPacienteId(), input.getTratamiento());
                }
            }
    );

    public final LiveData<Resource<List<Sintoma>>> sintomas = Transformations.switchMap(
            pacienteId, new Function<Long, LiveData<Resource<List<Sintoma>>>>() {
                @Override
                public LiveData<Resource<List<Sintoma>>> apply(Long input) {
                    return Controller.getSintomas(input);
                }
            }
    );

    public void saveTratamiento() {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setContenido(this.tratamiento.getValue());

        UpdateTratamientoPaciente updateTratamiento = new UpdateTratamientoPaciente(
                pacienteId.getValue(), tratamiento
        );

        // TODO: validaciones
        _updateTratamiento.setValue(updateTratamiento);
    }

}
