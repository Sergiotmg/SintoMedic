package com.example.sintomedicCasa.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.adapters.SintomaAdapter;
import com.example.sintomedicCasa.adapters.SintomaAdapterListener;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.databinding.ActivityShowSintomasPacienteBinding;
import com.example.sintomedicCasa.model.Sintoma;
import com.example.sintomedicCasa.viewmodel.ShowSintomasPacienteViewModel;

import java.util.List;

public class ShowSintomasPacienteActivity extends AppCompatActivity implements SintomaAdapterListener {

    public static final String PACIENTE_ID_EXTRA = "PACIENTE_ID_EXTRA";

    private ShowSintomasPacienteViewModel vm;
    private ActivityShowSintomasPacienteBinding binding;

    private SintomaAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sintomas_paciente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_sintomas_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(ShowSintomasPacienteViewModel.class);
        binding.setVm(vm);

        vm.pacienteId.setValue(getIntent().getLongExtra(PACIENTE_ID_EXTRA, 0));

        recyclerAdapter = new SintomaAdapter(this);
        RecyclerView recycler = findViewById(R.id.recyclerListaSintomasDeActivityPaciente);
        recycler.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        vm.sintomas.observe(this, new Observer<Resource<List<Sintoma>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Sintoma>> resource) {
                if (resource.isSuccess()) {
                    recyclerAdapter.setSintomas(resource.getData());
                }
            }
        });
    }

    public void startShowSintomaPacienteActivity(long sintomaId, long pacienteId) {
        Intent intent = new Intent(this, ShowSintomaPacienteActivity.class);
        intent.putExtra(ShowSintomaPacienteActivity.SINTOMA_ID_EXTRA, sintomaId);
        intent.putExtra(ShowSintomaPacienteActivity.PACIENTE_ID_EXTRA, pacienteId);
        startActivity(intent);
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
