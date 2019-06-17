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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sintomedic.R;
import com.example.sintomedic.adapters.PacienteAdapter;
import com.example.sintomedic.adapters.PacienteAdapterListener;
import com.example.sintomedic.controllers.Resource;
import com.example.sintomedic.databinding.ActivityMainDoctorBinding;
import com.example.sintomedic.model.Usuario;
import com.example.sintomedic.viewmodel.MainDoctorViewModel;

import java.util.List;

public class MainDoctorActivity extends AppCompatActivity implements PacienteAdapterListener {

    private MainDoctorViewModel vm;
    private ActivityMainDoctorBinding binding;

    private PacienteAdapter pacientesRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_doctor);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(MainDoctorViewModel.class);
        binding.setVm(vm);

        pacientesRecyclerAdapter = new PacienteAdapter(this);
        RecyclerView recycler = binding.recyclerListaPacientes;
        recycler.setAdapter(pacientesRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        vm.loadDoctor.setValue(null);

        vm.pacientes.observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(@Nullable List<Usuario> pacientes) {
                pacientesRecyclerAdapter.setPacientes(pacientes);
            }
        });
        vm.pacientesResource.observe(this, new Observer<Resource<List<Usuario>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Usuario>> resource) {

            }
        });
        vm.addPaciente.observe(this, new Observer<Resource<Void>>() {
            @Override
            public void onChanged(@Nullable Resource<Void> resource) {
                if (resource.isError()) {
                    onAddPacienteError(resource);
                }
            }
        });
        vm.deletePaciente.observe(this, new Observer<Resource<Void>>() {
            @Override
            public void onChanged(@Nullable Resource<Void> resource) {
                if (resource.isError()) {
                    onDeletePacienteError(resource);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doctor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_close_session:
                closeSession();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startShowFichaPacienteActivity(Long pacienteId) {
        Intent intent = new Intent(this, ShowFichaPacienteActivity.class);
        intent.putExtra(ShowFichaPacienteActivity.PACIENTE_ID_EXTRA, pacienteId);
        startActivity(intent);
    }

    @Override
    public void onShowFichaPaciente(Usuario paciente) {
        startShowFichaPacienteActivity(paciente.getId());
    }

    @Override
    public void onDeletePaciente(Usuario paciente) {
        vm.deletePaciente(paciente.getId());
    }

    public void searchPacienteByDniNie(View view) {
        vm.searchPacienteByDniNie();
    }

    public void addPacienteDniNie(View view) {
        vm.addPacienteDniNie();
    }

    private void onAddPacienteError(Resource<Void> resource) {
        Toast.makeText(this, "Error añadiendo paciente", Toast.LENGTH_SHORT).show();
    }

    private void onDeletePacienteError(Resource<Void> resource) {
        Toast.makeText(this, "Error eliminando paciente", Toast.LENGTH_SHORT).show();
    }

    private void closeSession() {
        vm.logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
