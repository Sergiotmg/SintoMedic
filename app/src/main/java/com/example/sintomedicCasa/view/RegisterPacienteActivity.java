package com.example.sintomedicCasa.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.databinding.ActivityRegistroPacienteBinding;
import com.example.sintomedicCasa.model.Usuario;
import com.example.sintomedicCasa.viewmodel.RegisterPacienteViewModel;

import java.util.Calendar;
import java.util.Date;

public class RegisterPacienteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, PrivacityFragmentDialog.PrivacityDialogListener {

    public static final int REQUEST_CODE = 4;

    private RegisterPacienteViewModel vm;
    private ActivityRegistroPacienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registro_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(RegisterPacienteViewModel.class);
        binding.setVm(vm);

        vm.usuario.observe(this, new Observer<Resource<Usuario>>() {
            @Override
            public void onChanged(@Nullable Resource<Usuario> resource) {
                if (resource.isSuccess()) {
                    onCreatePacienteSuccess(resource);
                } else if (resource.isError()) {
                    onCreatePacienteError(resource);
                }
            }
        });
        binding.datebornPaciente.setInputType(0);
        binding.datebornPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = vm.fechaNacimiento.getValue() != null ? vm.fechaNacimiento.getValue() : new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                new DatePickerDialog(RegisterPacienteActivity.this, RegisterPacienteActivity.this,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void createPaciente(View view) {
        binding.pacienteName.setError(vm.getNombreError());
        binding.pacienteSurname.setError(vm.getApellidosError());
        binding.dniNiePaciente.setError(vm.getDniNieError());
        binding.mailPaciente.setError(vm.getCorreoError());
        binding.passPaciente.setError(vm.getContraseniaError());
        if (vm.isRegisterValid()) {
            PrivacityFragmentDialog.newInstance().show(getSupportFragmentManager(), "PRIVACITY_DIALOG");
        }
    }

    private void onCreatePacienteSuccess(Resource<Usuario> resource) {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void onCreatePacienteError(Resource<Usuario> resource) {
        Toast.makeText(this, "Error creando cuenta", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        vm.fechaNacimiento.setValue(c.getTime());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onAcceptPrivacity() {
        vm.createPaciente();
    }

}
