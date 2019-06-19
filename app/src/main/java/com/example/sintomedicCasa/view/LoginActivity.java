package com.example.sintomedicCasa.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.databinding.ActivityLoginBinding;
import com.example.sintomedicCasa.model.Usuario;
import com.example.sintomedicCasa.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginVm;

    private int checkedHowToEnterItem = 0;
    private EditText passwordEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setLifecycleOwner(this);
        loginVm = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setVm(loginVm);

        // UI elements
        passwordEditText = findViewById(R.id.password);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.button_login || id == EditorInfo.IME_NULL) {
                    login(textView);
                    return true;
                }
                return false;
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login(v);
                }
                return false;
            }
        });

        loginVm.usuario.observe(this, new Observer<Resource<Usuario>>() {
            @Override
            public void onChanged(@Nullable Resource<Usuario> resource) {
                if (resource.isSuccess()) {
                    onLoginSuccess(resource.getData());
                } else if (resource.isError()) {
                    String message = resource.getHttpCode() == 401 ? "Usuario o contraseña incorrectos" : resource.getMessage();
                    showLoginError(message);
                }
            }
        });
    }

    public void login(View view) {
        loginVm.login(loginVm.username.getValue(), loginVm.password.getValue());
    }

    public void onLoginSuccess(Usuario usuario) {
        if (usuario.isDoctor() && usuario.isPaciente()) {
            showHowEnter();
        } else if (usuario.isPaciente()) {
            startMainPacienteActivity();
        } else {
            startMainDoctorActivity();
        }
    }

    private void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public void startDoctorRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterDoctorActivity.class);
        startActivityForResult(intent, RegisterDoctorActivity.REQUEST_CODE);
    }

    public void startPacienteRegisterActivity(View view) {
        Intent intent = new Intent(this, RegisterPacienteActivity.class);
        startActivityForResult(intent, RegisterPacienteActivity.REQUEST_CODE);
    }

    private void startMainDoctorActivity() {
        startActivity(new Intent(this, MainDoctorActivity.class));
        finish();
    }

    private void startMainPacienteActivity() {
        startActivity(new Intent(this, MainPacienteActivity.class));
        finish();
    }

    private void showHowEnter() {
        String[] types = {"Doctor", "Paciente"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("¿Cómo quieres entrar?");

        builder.setSingleChoiceItems(types, checkedHowToEnterItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkedHowToEnterItem = which;
            }
        });

        builder.setPositiveButton("Entrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (checkedHowToEnterItem) {
                    case 0:
                        startMainDoctorActivity();
                        break;
                    default:
                        startMainPacienteActivity();
                        finish();
                }
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RegisterPacienteActivity.REQUEST_CODE:
                    Toast.makeText(this, "Registro completado", Toast.LENGTH_LONG).show();
                    break;
                case RegisterDoctorActivity.REQUEST_CODE:
                    Toast.makeText(this, "Registro completado. Verificación pendiente", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}
