package com.example.sintomedicCasa.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.sintomedicCasa.controllers.Controller;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class MainDoctorViewModel extends AndroidViewModel {

    public final MutableLiveData<Void> loadDoctor = new MutableLiveData<>();
    public final LiveData<Usuario> doctor;

    public final MediatorLiveData<List<Usuario>> pacientes = new MediatorLiveData<>();
    public final LiveData<Resource<List<Usuario>>> pacientesResource;

    private final MutableLiveData<Long> _addPacienteId = new MutableLiveData<>();
    public final LiveData<Resource<Void>> addPaciente = Transformations.switchMap(
            _addPacienteId, new Function<Long, LiveData<Resource<Void>>>() {
                @Override
                public LiveData<Resource<Void>> apply(Long input) {
                    return Controller.addPaciente(input);
                }
            }
    );

    private final MutableLiveData<Long> _deletePacienteId = new MutableLiveData<>();
    public final LiveData<Resource<Void>> deletePaciente = Transformations.switchMap(
            _deletePacienteId, new Function<Long, LiveData<Resource<Void>>>() {
                @Override
                public LiveData<Resource<Void>> apply(Long input) {
                    return Controller.deletePaciente(input);
                }
            }
    );

    public final MutableLiveData<String> dniNiePaciente = new MutableLiveData<>();
    private final MutableLiveData<String> _dniNiePaciente = new MutableLiveData<>();

    public final LiveData<Resource<Usuario>> pacienteDniNie = Transformations.switchMap(
            _dniNiePaciente, new Function<String, LiveData<Resource<Usuario>>>() {
                @Override
                public LiveData<Resource<Usuario>> apply(String input) {
                    return Controller.getUsuarioByNif(input);
                }
            }
    );

    public MainDoctorViewModel(@NonNull Application application) {
        super(application);
        doctor = Transformations.switchMap(
                loadDoctor, new Function<Void, LiveData<Usuario>>() {
                    @Override
                    public LiveData<Usuario> apply(Void input) {
                        return Controller.getUsuario(getSharedPreferences());
                    }
                }
        );
        pacientesResource = Transformations.switchMap(
                doctor, new Function<Usuario, LiveData<Resource<List<Usuario>>>>() {
                    @Override
                    public LiveData<Resource<List<Usuario>>> apply(Usuario input) {
                        return Controller.getPacientes();
                    }
                }
        );
        pacientes.addSource(pacientesResource, new Observer<Resource<List<Usuario>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Usuario>> resource) {
                if (resource.isSuccess()) {
                    pacientes.setValue(resource.getData());
                }
            }
        });
        pacientes.addSource(deletePaciente, new Observer<Resource<Void>>() {
            @Override
            public void onChanged(@Nullable Resource<Void> resource) {
                if (resource.isSuccess()) {
                    pacientes.getValue().remove(findPacienteById(_deletePacienteId.getValue()));
                    pacientes.setValue(pacientes.getValue());
                }
            }
        });
        pacientes.addSource(addPaciente, new Observer<Resource<Void>>() {
            @Override
            public void onChanged(@Nullable Resource<Void> resource) {
                if (resource.isSuccess()
                        && pacienteDniNie.getValue() != null
                        && pacienteDniNie.getValue().isSuccess()) {
                    Usuario paciente = findPacienteById(pacienteDniNie.getValue().getData().getId());
                    if (paciente == null) {
                        pacientes.getValue().add(pacienteDniNie.getValue().getData());
                        pacientes.setValue(pacientes.getValue());
                    }
                }
            }
        });
        pacientes.setValue(new ArrayList<Usuario>());
    }

    public void deletePaciente(Long pacienteId) {
        _deletePacienteId.setValue(pacienteId);
    }

    public void searchPacienteByDniNie() {
        _dniNiePaciente.setValue(dniNiePaciente.getValue());
    }

    public void addPacienteDniNie() {
        if (pacienteDniNie.getValue() != null && pacienteDniNie.getValue().getData() != null) {
            _addPacienteId.setValue(pacienteDniNie.getValue().getData().getId());
        }
    }

    private Usuario findPacienteById(Long pacienteId) {
        for (Usuario paciente : pacientes.getValue()) {
            if (paciente.getId() == pacienteId) {
                return paciente;
            }
        }

        return null;
    }

    public void logout() {
        Controller.logout(getSharedPreferences());
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(getApplication());
    }

}
