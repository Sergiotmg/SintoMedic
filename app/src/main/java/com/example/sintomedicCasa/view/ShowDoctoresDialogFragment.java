package com.example.sintomedicCasa.view;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.adapters.DoctorAdapter;
import com.example.sintomedicCasa.controllers.Resource;
import com.example.sintomedicCasa.databinding.ShowDoctoresDialogBinding;
import com.example.sintomedicCasa.model.Usuario;
import com.example.sintomedicCasa.viewmodel.ShowDoctoresViewModel;

import java.util.List;

public class ShowDoctoresDialogFragment extends DialogFragment {

    private ShowDoctoresViewModel vm;
    private ShowDoctoresDialogBinding binding;

    private DoctorAdapter doctorRecyclerAdapter;

    public static ShowDoctoresDialogFragment newInstance() {
        return new ShowDoctoresDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()), R.layout.show_doctores_dialog, null, false
        );
        binding.setLifecycleOwner(this);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Doctores")
                .setView(binding.getRoot())
                .create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = ViewModelProviders.of(this).get(ShowDoctoresViewModel.class);
        binding.setVm(vm);

        binding.buttonDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        doctorRecyclerAdapter = new DoctorAdapter();
        binding.recyclerDoctores.setAdapter(doctorRecyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerDoctores.setLayoutManager(layoutManager);

        vm.loadDoctores.setValue(null);
        vm.doctores.observe(this, new Observer<Resource<List<Usuario>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Usuario>> resource) {
                if (resource.isSuccess()) {
                    doctorRecyclerAdapter.setDoctores(resource.getData());
                }
            }
        });
    }

}
