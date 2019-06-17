package com.example.sintomedicCasa.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.databinding.ActivityShowSintomaPacienteBinding;
import com.example.sintomedicCasa.viewmodel.ShowSintomaPacienteViewModel;
import com.example.sintomedicCasa.viewmodel.model.SintomaPacienteId;

public class ShowSintomaPacienteActivity extends AppCompatActivity {

    public static final String SINTOMA_ID_EXTRA = "SINTOMA_ID_EXTRA";
    public static final String PACIENTE_ID_EXTRA = "PACIENTE_ID_EXTRA";

    private ShowSintomaPacienteViewModel vm;
    private ActivityShowSintomaPacienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sintoma_paciente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_sintoma_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(ShowSintomaPacienteViewModel.class);
        binding.setVm(vm);

        vm.id.setValue(new SintomaPacienteId(
                getIntent().getLongExtra(SINTOMA_ID_EXTRA, 0),
                getIntent().getLongExtra(PACIENTE_ID_EXTRA, 0)
        ));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
