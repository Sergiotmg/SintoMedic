package com.example.sintomedic.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Tratamiento;
import com.example.sintomedic.model.Usuario;

public class MainPacienteViewModel extends AndroidViewModel {

    public final MutableLiveData<Void> loadPaciente = new MutableLiveData<>();
    public final LiveData<Usuario> paciente;

    public final LiveData<Resource<Tratamiento>> tratamiento;

    public MainPacienteViewModel(@NonNull Application application) {
        super(application);
        paciente = Transformations.switchMap(
                loadPaciente, new Function<Void, LiveData<Usuario>>() {
                    @Override
                    public LiveData<Usuario> apply(Void input) {
                        return Controller.getUsuario(getSharedPreferences());
                    }
                }
        );
        tratamiento = Transformations.switchMap(
                paciente, new Function<Usuario, LiveData<Resource<Tratamiento>>>() {
                    @Override
                    public LiveData<Resource<Tratamiento>> apply(Usuario input) {
                        return Controller.getTratamiento(input.getId());
                    }
                }
        );
    }

    public void logout() {
        Controller.logout(getSharedPreferences());
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getApplication());
    }

}
