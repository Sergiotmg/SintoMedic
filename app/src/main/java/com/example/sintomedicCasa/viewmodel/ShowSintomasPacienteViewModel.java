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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShowSintomasPacienteViewModel extends ViewModel {

    public final MutableLiveData<Long> pacienteId = new MutableLiveData<>();

    public final LiveData<Resource<List<Sintoma>>> sintomas = Transformations.switchMap(
            pacienteId,
            new Function<Long, LiveData<Resource<List<Sintoma>>>>() {
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

}
