package com.example.sintomedic.view;

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

import com.example.sintomedic.R;
import com.example.sintomedic.adapters.SintomaAdapter;
import com.example.sintomedic.adapters.SintomaAdapterListener;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.databinding.ActivityShowFichaPacienteBinding;
import com.example.sintomedic.model.Sintoma;
import com.example.sintomedic.model.Tratamiento;
import com.example.sintomedic.viewmodel.ShowFichaPacienteViewModel;

import java.util.List;

public class ShowFichaPacienteActivity extends AppCompatActivity implements SintomaAdapterListener {

    public static final String PACIENTE_ID_EXTRA = "PACIENTE_ID_EXTRA";

    private ShowFichaPacienteViewModel vm;
    private ActivityShowFichaPacienteBinding binding;

    private SintomaAdapter sintomaRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ficha_paciente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_ficha_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(ShowFichaPacienteViewModel.class);
        binding.setVm(vm);

        vm.pacienteId.setValue(getIntent().getLongExtra(PACIENTE_ID_EXTRA, 0));

        vm.tratamientoResource.observe(this, new Observer<Resource<Tratamiento>>() {
            @Override
            public void onChanged(@Nullable Resource<Tratamiento> resource) {
                if (resource.isSuccess()) {
                    vm.tratamiento.setValue(resource.getData().getContenido());
                }
            }
        });

        sintomaRecyclerAdapter = new SintomaAdapter(this);
        RecyclerView recycler = binding.recyclerListaSintomas;
        recycler.setAdapter(sintomaRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        vm.sintomas.observe(this, new Observer<Resource<List<Sintoma>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Sintoma>> resource) {
                if (resource.isSuccess()) {
                    sintomaRecyclerAdapter.setSintomas(resource.getData());
                }
            }
        });

        vm.tratamientoUpdate.observe(this, new Observer<Resource<Tratamiento>>() {
            @Override
            public void onChanged(@Nullable Resource<Tratamiento> resource) {
                if (resource.isSuccess()) {
                    onSaveTratamientoSuccess(resource);
                } else if (resource.isError()) {
                    onSaveTratamientoError(resource);
                }
            }
        });
    }

    public void startEnfermedadesPacienteActivity(View view) {
        Intent intent = new Intent(this, ShowEnfermedadesPacienteActivity.class);
        intent.putExtra(ShowEnfermedadesPacienteActivity.PACIENTE_ID_EXTRA, vm.pacienteId.getValue());
        startActivity(intent);
    }

    public void startShowSintomaPacienteActivity(Long sintomaId, Long pacienteId) {
        Intent intent = new Intent(this, ShowSintomaPacienteActivity.class);
        intent.putExtra(ShowSintomaPacienteActivity.SINTOMA_ID_EXTRA, sintomaId);
        intent.putExtra(ShowSintomaPacienteActivity.PACIENTE_ID_EXTRA, pacienteId);
        startActivity(intent);
    }

    public void saveTratamiento(View view) {
        vm.saveTratamiento();
    }

    private void onSaveTratamientoSuccess(Resource<Tratamiento> resource) {
        Toast.makeText(this, "Tratamiento guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void onSaveTratamientoError(Resource<Tratamiento> resource) {
        Toast.makeText(this, resource.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowSintoma(Sintoma sintoma) {
        startShowSintomaPacienteActivity(sintoma.getId(), vm.pacienteId.getValue());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
