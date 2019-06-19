package com.example.sintomedicCasa.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.example.sintomedicCasa.controllers.Controller;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.model.Sintoma;
import com.example.sintomedicCasa.model.Tratamiento;
import com.example.sintomedicCasa.model.Usuario;
import com.example.sintomedicCasa.viewmodel.model.UpdateTratamientoPaciente;

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
                    final MediatorLiveData<Resource<List<Sintoma>>> mediator = new MediatorLiveData<>();
                    mediator.addSource(Controller.getSintomas(input), new Observer<Resource<List<Sintoma>>>() {
                        @Override
                        public void onChanged(@Nullable Resource<List<Sintoma>> resource) {
                            if (resource.isSuccess()) {
                                Sintoma.sortSintomasByDate(resource.getData());
                            }
                            mediator.setValue(resource);
                        }
                    });

                    return mediator;
                }
            }
    );

    public void saveTratamiento() {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setContenido(this.tratamiento.getValue());

        UpdateTratamientoPaciente updateTratamiento = new UpdateTratamientoPaciente(
                pacienteId.getValue(), tratamiento
        );

        _updateTratamiento.setValue(updateTratamiento);
    }

}
