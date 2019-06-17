package com.example.sintomedicCasa.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedicCasa.controllers.Controller;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.model.Usuario;

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
