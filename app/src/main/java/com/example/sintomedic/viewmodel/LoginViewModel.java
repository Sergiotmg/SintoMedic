package com.example.sintomedic.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Usuario;
import com.example.sintomedic.viewmodel.model.Login;

public class LoginViewModel extends AndroidViewModel {

    public final MutableLiveData<String> username = new MutableLiveData<>();
    public final MutableLiveData<String> password = new MutableLiveData<>();

    private final MutableLiveData<Login> login = new MutableLiveData<>();
    public final LiveData<Resource<Usuario>> usuario;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        usuario = Transformations.switchMap(login, new Function<Login, LiveData<Resource<Usuario>>>() {
            @Override
            public LiveData<Resource<Usuario>> apply(Login input) {
                return Controller.login(
                        PreferenceManager.getDefaultSharedPreferences(getApplication().getApplicationContext()),
                        input.getUsername(), input.getPassword()
                );
            }
        });
    }

    public void login(String username, String password) {
        if (username != null && password != null) {
            login.setValue(new Login(username, password));
        }
    }

}
