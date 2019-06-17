package com.example.sintomedicCasa.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedicCasa.controllers.Controller;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.model.Sintoma;
import com.example.sintomedicCasa.viewmodel.model.NewSintomaPaciente;

import java.util.Date;

public class AddSintomaPacienteViewModel extends ViewModel {

    public final MutableLiveData<String> descripcion = new MutableLiveData<>();
    public final MutableLiveData<String> temperatura = new MutableLiveData<>();
    public final MutableLiveData<String> presionArterialAlta = new MutableLiveData<>();
    public final MutableLiveData<String> presionArterialBaja = new MutableLiveData<>();
    public final MutableLiveData<String> pulso = new MutableLiveData<>();

    private final MutableLiveData<NewSintomaPaciente> _newSintoma = new MutableLiveData<>();

    public final LiveData<Resource<Sintoma>> sintoma = Transformations.switchMap(
            _newSintoma, new Function<NewSintomaPaciente, LiveData<Resource<Sintoma>>>() {
                @Override
                public LiveData<Resource<Sintoma>> apply(NewSintomaPaciente input) {
                    return Controller.createSintoma(input.getPacienteId(), input.getSintoma());
                }
            }
    );

    public void saveSintoma(Long pacienteId) {
        Sintoma sintoma = new Sintoma();
        sintoma.setFechaHora(new Date());
        sintoma.setDescripcion(descripcion.getValue());
        if (temperatura.getValue() != null) {
            sintoma.setTemperatura(Float.valueOf(temperatura.getValue()));
        }
        if (presionArterialAlta.getValue() != null) {
            sintoma.setPresionArterialAlta(Float.valueOf(presionArterialAlta.getValue()));
        }
        if (presionArterialBaja.getValue() != null) {
            sintoma.setPresionArterialBaja(Float.valueOf(presionArterialBaja.getValue()));
        }

        sintoma.setPulso(pulso.getValue());

        NewSintomaPaciente newSintomaPaciente = new NewSintomaPaciente(pacienteId, sintoma);

        _newSintoma.setValue(newSintomaPaciente);
    }

}
