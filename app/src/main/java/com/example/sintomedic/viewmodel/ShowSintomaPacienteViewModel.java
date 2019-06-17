package com.example.sintomedic.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Sintoma;
import com.example.sintomedic.viewmodel.model.SintomaPacienteId;

public class ShowSintomaPacienteViewModel extends ViewModel {

    public final MutableLiveData<SintomaPacienteId> id = new MutableLiveData<>();

    public final LiveData<Resource<Sintoma>> sintoma = Transformations.switchMap(
            id, new Function<SintomaPacienteId, LiveData<Resource<Sintoma>>>() {
                @Override
                public LiveData<Resource<Sintoma>> apply(SintomaPacienteId input) {
                    return Controller.getSintoma(input.getPacienteId(), input.getSintomaId());
                }
            }
    );

}
