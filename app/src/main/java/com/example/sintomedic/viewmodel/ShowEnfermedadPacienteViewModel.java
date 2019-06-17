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
import com.example.sintomedic.viewmodel.model.EnfermedadPacienteId;

import java.util.Date;

public class ShowEnfermedadPacienteViewModel extends ViewModel {

    public final MutableLiveData<EnfermedadPacienteId> id = new MutableLiveData<>();

    public final MediatorLiveData<Enfermedad> enfermedad = new MediatorLiveData<>();
    public final LiveData<Resource<Enfermedad>> enfermedadResource = Transformations.switchMap(
            id, new Function<EnfermedadPacienteId, LiveData<Resource<Enfermedad>>>() {
                @Override
                public LiveData<Resource<Enfermedad>> apply(EnfermedadPacienteId input) {
                    return Controller.getEnfermedad(input.getPacienteId(), input.getEnfermedadId());
                }
            }
    );

    private final MutableLiveData<Enfermedad> _enfermedadToUpdate = new MutableLiveData<>();
    public final LiveData<Resource<Enfermedad>> updateEnfermaedad = Transformations.switchMap(
            _enfermedadToUpdate, new Function<Enfermedad, LiveData<Resource<Enfermedad>>>() {
                @Override
                public LiveData<Resource<Enfermedad>> apply(Enfermedad input) {
                    return Controller.updateEnfermedad(
                            id.getValue().getPacienteId(), id.getValue().getEnfermedadId(), input
                    );
                }
            }
    );

    private final MutableLiveData<EnfermedadPacienteId> _deleteEnfermedadId = new MutableLiveData<>();
    public final LiveData<Resource<Void>> deleteEnfermedad = Transformations.switchMap(
            _deleteEnfermedadId, new Function<EnfermedadPacienteId, LiveData<Resource<Void>>>() {
                @Override
                public LiveData<Resource<Void>> apply(EnfermedadPacienteId input) {
                    return Controller.deleteEnfermedad(input.getPacienteId(), input.getEnfermedadId());
                }
            }
    );

    public ShowEnfermedadPacienteViewModel() {
        enfermedad.addSource(enfermedadResource, new Observer<Resource<Enfermedad>>() {
            @Override
            public void onChanged(@Nullable Resource<Enfermedad> resource) {
                if (resource.isSuccess()) {
                    enfermedad.setValue(resource.getData());
                }
            }
        });
    }

    public void finalizeEnfermedad() {
        if (enfermedad.getValue() != null) {
            enfermedad.getValue().setFechaFin(new Date());
            enfermedad.setValue(enfermedad.getValue());
            _enfermedadToUpdate.setValue(enfermedad.getValue());
        }
    }

    public void deleteEnfermedad() {
        _deleteEnfermedadId.setValue(id.getValue());
    }

}
