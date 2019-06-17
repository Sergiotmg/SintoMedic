package com.example.sintomedicCasa.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.adapters.EnfermedadAdapter;
import com.example.sintomedicCasa.adapters.EnfermedadAdapterListener;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.databinding.ActivityShowEnfermedadesPacienteBinding;
import com.example.sintomedicCasa.model.Enfermedad;
import com.example.sintomedicCasa.viewmodel.ShowEnfermedadesPacienteViewModel;

import java.util.List;

public class ShowEnfermedadesPacienteActivity extends AppCompatActivity implements EnfermedadAdapterListener {

    public static final String PACIENTE_ID_EXTRA = "PACIENTE_ID_EXTRA";

    private ShowEnfermedadesPacienteViewModel vm;
    private ActivityShowEnfermedadesPacienteBinding binding;

    private EnfermedadAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_enfermedades_paciente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_enfermedades_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(ShowEnfermedadesPacienteViewModel.class);
        binding.setVm(vm);
        vm.pacienteId.setValue(getIntent().getLongExtra(PACIENTE_ID_EXTRA, 0));

        recyclerAdapter = new EnfermedadAdapter(this);
        RecyclerView recycler = findViewById(R.id.recyclerEnfermedadesPaciente);
        recycler.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        vm.enfermedades.observe(this, new Observer<List<Enfermedad>>() {
            @Override
            public void onChanged(@Nullable List<Enfermedad> enfermedades) {
                recyclerAdapter.setEnfermedades(enfermedades);
            }
        });

        vm.enfermedadesResource.observe(this, new Observer<Resource<List<Enfermedad>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Enfermedad>> resource) {

            }
        });
    }

    public void startAddEnfermedadActivity(View view) {
        Intent intent = new Intent(this, AddEnfermedadPacienteActivity.class);
        intent.putExtra(
                AddEnfermedadPacienteActivity.PACIENTE_ID_EXTRA,
                vm.pacienteId.getValue()
        );
        startActivityForResult(intent, AddEnfermedadPacienteActivity.REQUEST_CODE);
    }

    public void startShowEnfermedadActivity(Long enfermedadId, Long pacienteId) {
        Intent intent = new Intent(this, ShowEnfermedadPacienteActivity.class);
        intent.putExtra(ShowEnfermedadPacienteActivity.ENFERMEDAD_ID_EXTRA, enfermedadId);
        intent.putExtra(ShowEnfermedadPacienteActivity.PACIENTE_ID_EXTRA, pacienteId);
        startActivityForResult(intent, ShowEnfermedadPacienteActivity.REQUEST_CODE);
    }

    @Override
    public void onShowEnfermedad(Enfermedad enfermedad) {
        startShowEnfermedadActivity(enfermedad.getId(), vm.pacienteId.getValue());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AddEnfermedadPacienteActivity.REQUEST_CODE:
                    vm.addEnfermedad((Enfermedad) data.getExtras().getSerializable(AddEnfermedadPacienteActivity.ENFERMEDAD_CREADA_EXTRA));
                    Toast.makeText(this, "Enfermedad registrada correctamente", Toast.LENGTH_SHORT).show();
                    break;
                case ShowEnfermedadPacienteActivity.REQUEST_CODE:
                    vm.deleteEnfermedad(data.getExtras().getLong(ShowEnfermedadPacienteActivity.ENFERMEDAD_ID_EXTRA, 0));
                    Toast.makeText(this, "Enfermedad eliminada correctamente", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
