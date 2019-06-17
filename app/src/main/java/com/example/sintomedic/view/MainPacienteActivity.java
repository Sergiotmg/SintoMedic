package com.example.sintomedic.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sintomedic.R;
import com.example.sintomedic.databinding.ActivityMainPacienteBinding;
import com.example.sintomedic.model.Usuario;
import com.example.sintomedic.viewmodel.MainPacienteViewModel;

public class MainPacienteActivity extends AppCompatActivity {

    private MainPacienteViewModel vm;
    private ActivityMainPacienteBinding binding;

    private ShowDoctoresDialogFragment showDoctoresDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paciente);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_paciente);
        binding.setLifecycleOwner(this);

        vm = ViewModelProviders.of(this).get(MainPacienteViewModel.class);
        binding.setVm(vm);

        vm.loadPaciente.setValue(null);
        vm.paciente.observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(@Nullable Usuario usuario) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.paciente_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_close_patient_session:
                closeSession();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDoctoresDialog(View view) {
        if (showDoctoresDialogFragment == null) {
            showDoctoresDialogFragment = ShowDoctoresDialogFragment.newInstance();
        }
        showDoctoresDialogFragment.show(getSupportFragmentManager(), "DOCTORES_DIALOG");
    }

    public void startAddSintomaPacienteActivity(View view) {
        Intent intent = new Intent(this, AddSintomaPacienteActivity.class);
        intent.putExtra(
                AddSintomaPacienteActivity.PACIENTE_ID_EXTRA,
                vm.paciente.getValue().getId()
        );
        startActivityForResult(intent, AddSintomaPacienteActivity.REQUEST_CODE);
    }

    public void startShowSintomasPacienteActivity(View view) {
        Intent intent = new Intent(this, ShowSintomasPacienteActivity.class);
        intent.putExtra(
                ShowSintomasPacienteActivity.PACIENTE_ID_EXTRA,
                vm.paciente.getValue().getId()
        );
        startActivity(intent);
    }

    private void closeSession() {
        vm.logout();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AddSintomaPacienteActivity.REQUEST_CODE:
                    Toast.makeText(this, "Síntoma registrado correctamente", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
