package com.example.sintomedic.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sintomedic.R;
import com.example.sintomedic.databinding.ListaEnfermedadesItemBinding;
import com.example.sintomedic.model.Enfermedad;

import java.util.ArrayList;
import java.util.List;

public class EnfermedadAdapter extends RecyclerView.Adapter<EnfermedadAdapter.EnfermedadViewHolder> {

    private List<Enfermedad> enfermedades = new ArrayList<>();
    private EnfermedadAdapterListener listener;

    public EnfermedadAdapter(EnfermedadAdapterListener listener) {
        this.listener = listener;
    }

    public void setEnfermedades(List<Enfermedad> enfermedades) {
        this.enfermedades = new ArrayList<>(enfermedades);
        notifyDataSetChanged();
    }

    public class EnfermedadViewHolder extends RecyclerView.ViewHolder {

        private ListaEnfermedadesItemBinding binding;

        public EnfermedadViewHolder(@NonNull View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            binding.buttonVerEnfermedad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onShowEnfermedad(binding.getEnfermedad());
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public EnfermedadAdapter.EnfermedadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EnfermedadAdapter.EnfermedadViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.lista_enfermedades_item, viewGroup, false)
        );
    }


    @Override
    public void onBindViewHolder(@NonNull EnfermedadViewHolder vh, int i) {
        vh.binding.setEnfermedad(enfermedades.get(i));
        vh.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return enfermedades.size();
    }

}
