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
import com.example.sintomedic.databinding.ActivityShowEnfermedadPacienteBinding;
import com.example.sintomedic.model.Enfermedad;
import com.example.sintomedic.viewmodel.ShowEnfermedadPacienteViewModel;
import com.example.sintomedic.viewmodel.model.EnfermedadPacienteId;

public class ShowEnfermedadPacienteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 2;

    public static final String PACIENTE_ID_EXTRA = "PACIENTE_ID_EXTRA";
    public static final String ENFERMEDAD_ID_EXTRA = "ENFERMEDAD_ID_EXTRA";

    private ShowEnfermedadPacienteViewModel vm;
    private ActivityShowEnfermedadPacienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_enfermedad_paciente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_enfermedad_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(ShowEnfermedadPacienteViewModel.class);
        binding.setVm(vm);
        vm.id.setValue(new EnfermedadPacienteId(
                getIntent().getLongExtra(ENFERMEDAD_ID_EXTRA, 0),
                getIntent().getLongExtra(PACIENTE_ID_EXTRA, 0)
        ));

        vm.deleteEnfermedad.observe(this, new Observer<Resource<Void>>() {
            @Override
            public void onChanged(@Nullable Resource<Void> resource) {
                if (resource.isSuccess()) {
                    onDeleteEnfermedadSuccess(resource);
                } else if (resource.isError()) {
                    onDeleteEnfermedadError(resource);
                }
            }
        });

        vm.updateEnfermaedad.observe(this, new Observer<Resource<Enfermedad>>() {
            @Override
            public void onChanged(@Nullable Resource<Enfermedad> resource) {
                if (resource.isSuccess()) {
                    onFinalizeEnfermedadSuccess(resource);
                } else if (resource.isError()) {
                    onFinalizeEnfermedadError(resource);
                }
            }
        });
    }

    public void finalizeEnfermedad(View view) {
        vm.finalizeEnfermedad();
    }

    private void onFinalizeEnfermedadSuccess(Resource<Enfermedad> resource) {
        Toast.makeText(this, "Enfermedad finalizada correctamente", Toast.LENGTH_SHORT).show();
    }

    private void onFinalizeEnfermedadError(Resource<Enfermedad> resource) {
        Toast.makeText(this, "Error finalizando la enfermedad", Toast.LENGTH_SHORT).show();
    }

    public void deleteEnfermedad(View view) {
        vm.deleteEnfermedad();
    }

    private void onDeleteEnfermedadSuccess(Resource<Void> resource) {
        setResult(Activity.RESULT_OK, getIntent());
        finish();
    }

    private void onDeleteEnfermedadError(Resource<Void> resource) {
        Toast.makeText(this, resource.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
