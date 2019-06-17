package com.example.sintomedic.view;

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

import com.example.sintomedic.R;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.databinding.ActivityRegistroPacienteBinding;
import com.example.sintomedic.model.Usuario;
import com.example.sintomedic.viewmodel.RegisterPacienteViewModel;

import java.util.Calendar;
import java.util.Date;

public class RegisterPacienteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RegisterPacienteViewModel vm;
    private ActivityRegistroPacienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paciente);

        // setTitle("Registro de paciente");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        vm.createPaciente();
    }

    private void onCreatePacienteSuccess(Resource<Usuario> resource) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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

}
