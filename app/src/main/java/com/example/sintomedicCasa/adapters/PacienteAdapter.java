package com.example.sintomedicCasa.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sintomedicCasa.GlideModule.GlideApp;
import com.example.sintomedicCasa.R;
import com.example.sintomedicCasa.databinding.ListaPacientesItemBinding;
import com.example.sintomedicCasa.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder> {

    private List<Usuario> pacientes = new ArrayList<>();
    private PacienteAdapterListener listener;

    public PacienteAdapter(PacienteAdapterListener listener) {
        this.listener = listener;
    }

    public void setPacientes(List<Usuario> pacientes) {
        this.pacientes = new ArrayList<>(pacientes);
        notifyDataSetChanged();
    }

    public class PacienteViewHolder extends RecyclerView.ViewHolder {

        private ListaPacientesItemBinding binding;

        public PacienteViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            binding.buttonFichaPaciente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onShowFichaPaciente(binding.getPaciente());
                    }
                }
            });
            binding.buttonEliminPaciente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onDeletePaciente(binding.getPaciente());
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PacienteViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.lista_pacientes_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder vh, int i) {
        vh.binding.setPaciente(pacientes.get(i));
        /*GlideApp.with(vh.itemView.getContext())
                .load(vh.binding.getPaciente().getLinkFotoPerfil())
                .into(vh.binding.imageViewPaciente);*/
        vh.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return pacientes.size();
    }

}
