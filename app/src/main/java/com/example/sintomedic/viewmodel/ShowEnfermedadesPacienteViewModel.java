package com.example.sintomedic.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Enfermedad;

import java.util.ArrayList;
import java.util.List;

public class ShowEnfermedadesPacienteViewModel extends ViewModel {

    public final MutableLiveData<Long> pacienteId = new MutableLiveData<>();

    public final MediatorLiveData<List<Enfermedad>> enfermedades = new MediatorLiveData<>();

    public final LiveData<Resource<List<Enfermedad>>> enfermedadesResource = Transformations.switchMap(
            pacienteId,
            new Function<Long, LiveData<Resource<List<Enfermedad>>>>() {
                @Override
                public LiveData<Resource<List<Enfermedad>>> apply(Long input) {
                    return Controller.getEnfermedades(input);
                }
            }
    );

    public ShowEnfermedadesPacienteViewModel() {
        enfermedades.addSource(enfermedadesResource, new Observer<Resource<List<Enfermedad>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Enfermedad>> resource) {
                if (resource.isSuccess()) {
                    enfermedades.setValue(resource.getData());
                }
            }
        });
        enfermedades.setValue(new ArrayList<Enfermedad>());
    }

    public void addEnfermedad(Enfermedad enfermedad) {
        if (enfermedad != null && findEnfermedadById(enfermedad.getId()) == null) {
            enfermedades.getValue().add(enfermedad);
            enfermedades.setValue(enfermedades.getValue());
        }
    }

    public void deleteEnfermedad(Long id) {
        Enfermedad enfermedad = findEnfermedadById(id);

        if (enfermedad != null) {
            enfermedades.getValue().remove(enfermedad);
            enfermedades.setValue(enfermedades.getValue());
        }
    }

    private Enfermedad findEnfermedadById(Long id) {
        for (Enfermedad e : enfermedades.getValue()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }

        return null;
    }

}
