package com.example.sintomedic.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Usuario;

import java.util.List;

public class ShowDoctoresViewModel extends ViewModel {

    public final MutableLiveData<Void> loadDoctores = new MutableLiveData<>();

    public final LiveData<Resource<List<Usuario>>> doctores = Transformations.switchMap(
            loadDoctores, new Function<Void, LiveData<Resource<List<Usuario>>>>() {
                @Override
                public LiveData<Resource<List<Usuario>>> apply(Void input) {
                    return Controller.getDoctores();
                }
            }
    );

}
