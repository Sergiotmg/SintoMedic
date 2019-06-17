package com.example.sintomedic.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sintomedic.R;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.databinding.ActivityAddEnfermedadPacienteBinding;
import com.example.sintomedic.model.Enfermedad;
import com.example.sintomedic.viewmodel.AddEnfermedadPacienteViewModel;

public class AddEnfermedadPacienteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    public static final String PACIENTE_ID_EXTRA = "PACIENTE_ID_EXTRA";
    public static final String ENFERMEDAD_CREADA_EXTRA = "ENFERMEDAD_CREADA_EXTRA";

    private AddEnfermedadPacienteViewModel vm;
    private ActivityAddEnfermedadPacienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_enfermedad_paciente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_enfermedad_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(AddEnfermedadPacienteViewModel.class);
        binding.setVm(vm);

        vm.enfermedad.observe(this, new Observer<Resource<Enfermedad>>() {
            @Override
            public void onChanged(@Nullable Resource<Enfermedad> resource) {
                if (resource.isSuccess()) {
                    onSaveEnfermedadSuccess(resource);
                } else if (resource.isError()) {
                    onSaveEnfermedadError(resource);
                }
            }
        });
    }

    public void saveEnfermedad(View view) {
        vm.saveEnfermedad(getIntent().getLongExtra(PACIENTE_ID_EXTRA, 0));
    }

    private void onSaveEnfermedadSuccess(Resource<Enfermedad> resource) {
        Intent intent = new Intent();
        intent.putExtra(ENFERMEDAD_CREADA_EXTRA, resource.getData());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void onSaveEnfermedadError(Resource<Enfermedad> resource) {
        Toast.makeText(this, "Error registrando enfermedad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
