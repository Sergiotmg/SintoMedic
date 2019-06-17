package com.example.sintomedicCasa.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedicCasa.controllers.Controller;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.model.Sintoma;
import com.example.sintomedicCasa.viewmodel.model.SintomaPacienteId;

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
