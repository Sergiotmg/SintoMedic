package com.example.sintomedicCasa.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.databinding.ActivityRegistroDoctorBinding;
import com.example.sintomedicCasa.model.Usuario;
import com.example.sintomedicCasa.viewmodel.RegisterDoctorViewModel;

public class RegisterDoctorActivity extends AppCompatActivity {

    private RegisterDoctorViewModel vm;
    private ActivityRegistroDoctorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_doctor);

        // setTitle("Registro de doctor");
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registro_doctor);
        binding.setLifecycleOwner(this);
        binding.setVm(vm);

        vm = ViewModelProviders.of(this).get(RegisterDoctorViewModel.class);
        binding.setVm(vm);

        vm.usuario.observe(this, new Observer<Resource<Usuario>>() {
            @Override
            public void onChanged(@Nullable Resource<Usuario> resource) {
                if (resource.isSuccess()) {
                    onCreateDoctorSuccess(resource);
                } else if (resource.isError()) {
                    onCreateDoctorError(resource);
                }
            }
        });
    }

    public void createDoctor(View view) {
        binding.doctorName.setError(vm.getNombreError());
        binding.doctorSurname.setError(vm.getApellidosError());
        binding.dniNieDoctor.setError(vm.getDniNieError());
        binding.mailDoctor.setError(vm.getCorreoError());
        binding.passDoctor.setError(vm.getContraseniaError());
        binding.licenseNumber.setError(vm.getNumColegiadoError());
        vm.createDoctor();
    }

    private void onCreateDoctorSuccess(Resource<Usuario> resource) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void onCreateDoctorError(Resource<Usuario> resource) {
        Toast.makeText(this, "Error creando cuenta", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
