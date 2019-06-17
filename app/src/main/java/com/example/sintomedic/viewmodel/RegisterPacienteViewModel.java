package com.example.sintomedic.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.sintomedic.ValidationsUtil;
import com.example.sintomedic.controllers.Controller;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.model.Usuario;

import java.util.Date;

public class RegisterPacienteViewModel extends ViewModel {

    public final MutableLiveData<String> nombre = new MutableLiveData<>();
    public final MutableLiveData<String> apellidos = new MutableLiveData<>();
    public final MutableLiveData<Date> fechaNacimiento = new MutableLiveData<>();
    public final MutableLiveData<String> companiaAseguradora = new MutableLiveData<>();
    public final MutableLiveData<String> dniNie = new MutableLiveData<>();
    public final MutableLiveData<String> localidad = new MutableLiveData<>();
    public final MutableLiveData<String> correo = new MutableLiveData<>();
    public final MutableLiveData<String> telefono = new MutableLiveData<>();
    public final MutableLiveData<String> contrasenia = new MutableLiveData<>();

    private final MutableLiveData<Usuario> usuarioToCreate = new MutableLiveData<>();
    public final LiveData<Resource<Usuario>> usuario = Transformations.switchMap(
            usuarioToCreate, new Function<Usuario, LiveData<Resource<Usuario>>>() {
                @Override
                public LiveData<Resource<Usuario>> apply(Usuario input) {
                    return Controller.createUsuario(input);
                }
            }
    );

    public void createPaciente() {
        if (isRegisterValid()) {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre.getValue());
            usuario.setApellidos(apellidos.getValue());
            usuario.setFechaNacimiento(fechaNacimiento.getValue());
            usuario.setDniNie(dniNie.getValue());
            usuario.setLocalidad(localidad.getValue());
            usuario.setCorreo(correo.getValue());
            usuario.setTelefono(telefono.getValue());
            usuario.setCompaniaAseguradora(companiaAseguradora.getValue());
            usuario.setContrasenia(contrasenia.getValue());

            usuarioToCreate.setValue(usuario);
        }
    }

    private boolean isRegisterValid() {
        return isCorreoValid() && isNombreValid() && isApellidosValid()
                && isDniNieValid() && isContraseniaValid();
    }

    private boolean isCorreoValid() {
        return correo.getValue() == null || ValidationsUtil.validateEmail(correo.getValue());
    }

    private boolean isNombreValid() {
        return ValidationsUtil.notEmpty(nombre.getValue());
    }

    private boolean isApellidosValid() {
        return ValidationsUtil.notEmpty(apellidos.getValue());
    }

    private boolean isDniNieValid() {
        return ValidationsUtil.notEmpty(dniNie.getValue());
    }

    private boolean isContraseniaValid() {
        return contrasenia.getValue() != null && contrasenia.getValue().trim().length() > 5;
    }

    public String getCorreoError() {
        if (!isCorreoValid()) {
            return "Correo electrónico no válido";
        }

        return null;
    }

    public String getContraseniaError() {
        if (!isContraseniaValid()) {
            return "La contraseña ha de tener al menos 6 caracteres";
        }

        return null;
    }

    public String getNombreError() {
        if (!isNombreValid()) {
            return "El nombre no puede estar en blanco";
        }

        return null;
    }

    public String getApellidosError() {
        if (!isApellidosValid()) {
            return "Los apellidos no pueden estar en blanco";
        }

        return null;
    }

    public String getDniNieError() {
        if (!isDniNieValid()) {
            return "El DNI/NIE no puede estar en blanco";
        }

        return null;
    }

}
