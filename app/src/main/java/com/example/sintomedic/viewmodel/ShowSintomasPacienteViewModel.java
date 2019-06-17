package com.example.sintomedic.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Sintoma;

import java.util.List;

public class ShowSintomasPacienteViewModel extends ViewModel {

    public final MutableLiveData<Long> pacienteId = new MutableLiveData<>();

    public final LiveData<Resource<List<Sintoma>>> sintomas = Transformations.switchMap(
            pacienteId,
            new Function<Long, LiveData<Resource<List<Sintoma>>>>() {
                @Override
                public LiveData<Resource<List<Sintoma>>> apply(Long input) {
                    return Controller.getSintomas(input);
                }
            }
    );

}
