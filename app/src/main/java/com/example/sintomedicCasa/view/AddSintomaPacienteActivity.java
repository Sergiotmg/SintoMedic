package com.example.sintomedicCasa.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.databinding.ActivityAddSintomaPacienteBinding;
import com.example.sintomedicCasa.model.Sintoma;
import com.example.sintomedicCasa.viewmodel.AddSintomaPacienteViewModel;

public class AddSintomaPacienteActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 3;

    public static final String PACIENTE_ID_EXTRA = "PACIENTE_ID_EXTRA";

    private AddSintomaPacienteViewModel vm;
    private ActivityAddSintomaPacienteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sintoma_paciente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_sintoma_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(AddSintomaPacienteViewModel.class);
        binding.setVm(vm);

        vm.sintoma.observe(this, new Observer<Resource<Sintoma>>() {
            @Override
            public void onChanged(@Nullable Resource<Sintoma> resource) {
                if (resource.isSuccess()) {
                    onSaveSintomaSuccess(resource);
                } else if (resource.isError()) {
                    onSaveSintomaError(resource);
                }
            }
        });
    }

    public void saveSintoma(View view) {
        vm.saveSintoma(getIntent().getLongExtra(PACIENTE_ID_EXTRA, 0));
    }

    private void onSaveSintomaSuccess(Resource<Sintoma> resource) {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void onSaveSintomaError(Resource<Sintoma> resource) {
        Toast.makeText(this, "Error registrando síntoma", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
