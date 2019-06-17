package com.example.sintomedicCasa.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.databinding.ListaDoctoresItemBinding;
import com.example.sintomedicCasa.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private List<Usuario> doctores = new ArrayList<>();

    public void setDoctores(List<Usuario> doctores) {
        this.doctores = new ArrayList<>(doctores);
        notifyDataSetChanged();
    }

    public class DoctorViewHolder extends RecyclerView.ViewHolder {

        private ListaDoctoresItemBinding binding;

        public DoctorViewHolder(@NonNull View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

    }

    @NonNull
    @Override
    public DoctorAdapter.DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorAdapter.DoctorViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.lista_doctores_item, viewGroup, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DoctorViewHolder vh, int i) {
        vh.binding.setDoctor(doctores.get(i));
        vh.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return doctores.size();
    }

}
