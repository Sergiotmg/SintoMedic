package com.example.sintomedicCasa.controllers;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.example.sintomedicCasa.model.Enfermedad;
import com.example.sintomedicCasa.model.Sintoma;
import com.example.sintomedicCasa.model.Tratamiento;
import com.example.sintomedicCasa.model.Usuario;
import com.example.sintomedicCasa.rest.AuthenticationInterceptor;
import com.example.sintomedicCasa.rest.RestError;
import com.example.sintomedicCasa.rest.SintoMedicAPI;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Controller {

    private static final String USUARIO_LOGUEADO = "USUARIO_LOGUEADO";
    private static final String BASE_URL = "http://192.168.1.94:8080/api/"; //IP local + puerto API

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new AuthenticationInterceptor())
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .client(okHttpClient)
            .build();

    private static final SintoMedicAPI api = retrofit.create(SintoMedicAPI.class);

    private static Usuario usuarioLogueado;

    // ############### USUARIOS ###########################

    private static void saveUsuarioLogueado(SharedPreferences sharedPreferences, Usuario usuario) {
        if (usuario != null && usuarioLogueado != null) {
            usuario.setContrasenia(usuarioLogueado.getContrasenia());
        }
        usuarioLogueado = usuario;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = null;
        if (usuario != null) {
            try {
                json = objectMapper.writeValueAsString(usuario);
            } catch (JsonProcessingException e) {

            }
        }
        editor.putString(USUARIO_LOGUEADO, json);
        editor.commit();
    }

    public static Usuario getUsuarioLogueado(SharedPreferences sharedPreferences) {
        if (usuarioLogueado == null && sharedPreferences != null) {
            try {
                usuarioLogueado = objectMapper.readValue(sharedPreferences.getString(USUARIO_LOGUEADO, null), Usuario.class);
            } catch (IOException e) {

            }
        }

        return usuarioLogueado;
    }

    public static void logout(SharedPreferences sharedPreferences) {
        saveUsuarioLogueado(sharedPreferences, null);
    }

    public static LiveData<Resource<Usuario>> login(
            final SharedPreferences sharedPreferences,
            String username,
            final String password
    ) {
        final MediatorLiveData<Resource<Usuario>> mediator = new MediatorLiveData<>();
        mediator.addSource(
                getLiveDataFromCall(api.getUsuario(Credentials.basic(username, password))),
                new Observer<Resource<Usuario>>() {
                    @Override
                    public void onChanged(@Nullable Resource<Usuario> resource) {
                        if (resource.isSuccess()) {
                            resource.getData().setContrasenia(password);
                            saveUsuarioLogueado(sharedPreferences, resource.getData());
                        }
                        mediator.setValue(resource);
                    }
                }
        );

        return mediator;
    }

    public static LiveData<Usuario> getUsuario(final SharedPreferences sharedPreferences) {
        final MediatorLiveData<Usuario> mediator = new MediatorLiveData<>();
        mediator.addSource(
                getLiveDataFromCall(api.getUsuario(null)), new Observer<Resource<Usuario>>() {
                    @Override
                    public void onChanged(@Nullable Resource<Usuario> resource) {
                        if (resource.isSuccess()) {
                            saveUsuarioLogueado(sharedPreferences, resource.getData());
                        }
                        mediator.setValue(getUsuarioLogueado(sharedPreferences));
                    }
                });

        return mediator;
    }

    public static LiveData<Resource<Usuario>> getUsuarioByNif(String dniNie) {
        return getLiveDataFromCall(api.getUsuarioByNif(dniNie));
    }

    public static LiveData<Resource<Usuario>> getUsuario(Long id) {
        return getLiveDataFromCall(api.getUsuarioById(id));
    }

    public static LiveData<Resource<Usuario>> createUsuario(Usuario usuario) {
        return getLiveDataFromCall(api.createUsuario(usuario));
    }

    // ######################## DOCTORES ########################

    public static LiveData<Resource<List<Usuario>>> getDoctores() {
        return getLiveDataFromCall(api.getDoctores());
    }

    // ######### ENFERMEDADES #############

    public static LiveData<Resource<List<Enfermedad>>> getEnfermedades(Long userId) {
        return getLiveDataFromCall(api.getEnfermedades(userId));
    }

    public static LiveData<Resource<Enfermedad>> getEnfermedad(Long userId, Long id) {
        return getLiveDataFromCall(api.getEnfermedad(userId, id));
    }

    public static LiveData<Resource<Enfermedad>> createEnfermedad(long userId, Enfermedad enfermedad) {
        return getLiveDataFromCall(api.createEnfermedad(userId, enfermedad));
    }

    public static LiveData<Resource<Enfermedad>> updateEnfermedad(long userId, long id, Enfermedad enfermedad) {
        return getLiveDataFromCall(api.updateEnfermedad(userId, id, enfermedad));
    }

    public static LiveData<Resource<Void>> deleteEnfermedad(long userId, long id) {
        return getLiveDataFromCall(api.deleteEnfermedad(userId, id));
    }

    // ##################### PACIENTES ########################

    public static LiveData<Resource<List<Usuario>>> getPacientes() {
        return getLiveDataFromCall(api.getPacientes());
    }

    public static LiveData<Resource<Void>> addPaciente(long userId) {
        return getLiveDataFromCall(api.addPaciente(userId));
    }

    public static LiveData<Resource<Void>> deletePaciente(long userId) {
        return getLiveDataFromCall(api.deletePaciente(userId));
    }

    // ################ SINTOMAS ###############################

    public static LiveData<Resource<List<Sintoma>>> getSintomas(long userId) {
        return getLiveDataFromCall(api.getSintomas(userId));
    }

    public static LiveData<Resource<Sintoma>> createSintoma(long userId, Sintoma sintoma) {
        return getLiveDataFromCall(api.createSintoma(userId, sintoma));
    }

    public static LiveData<Resource<Sintoma>> getSintoma(long userId, long id) {
        return getLiveDataFromCall(api.getSintoma(userId, id));
    }

    // ######### TRATAMIENTOS #############

    public static LiveData<Resource<Tratamiento>> getTratamiento(long userId) {
        return getLiveDataFromCall(api.getTratamiento(userId));
    }

    public static LiveData<Resource<Tratamiento>> updateTratamiento(
            long userId,
            Tratamiento tratamiento
    ) {
        return getLiveDataFromCall(api.updateTratamiento(userId, tratamiento));
    }

    private static <T> LiveData<Resource<T>> getLiveDataFromCall(Call<T> call) {
        final MutableLiveData<Resource<T>> liveData = new MutableLiveData<>();
        liveData.setValue(new Resource<T>(Status.LOADING, null, null, null, -1));

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(new Resource<>(Status.SUCCESS, response.body(), null, null, response.code()));
                } else {
                    try {
                        RestError error = objectMapper.readValue(response.errorBody().string(), RestError.class);
                        liveData.setValue(new Resource<T>(Status.ERROR, null, error.getMensaje(), error, response.code()));
                    } catch (Exception e) {
                        this.onFailure(call, e);
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                liveData.setValue(new Resource<T>(Status.ERROR, null, "Algo ha salido mal", null, -1));
            }
        });

        return liveData;
    }

}
