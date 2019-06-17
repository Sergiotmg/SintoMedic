package com.example.sintomedic.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sintomedic.R;
import com.example.sintomedic.databinding.ListaSintomasItemBinding;
import com.example.sintomedic.model.Sintoma;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SintomaAdapter extends RecyclerView.Adapter<SintomaAdapter.SintomaViewHolder> {

    private List<Sintoma> sintomas = new ArrayList<>();
    private SintomaAdapterListener listener;

    public SintomaAdapter(SintomaAdapterListener listener) {
        this.listener = listener;
    }

    public void setSintomas(List<Sintoma> sintomas) {
        this.sintomas = new ArrayList<>(sintomas);
        notifyDataSetChanged();
    }


    public class SintomaViewHolder extends RecyclerView.ViewHolder {

        private ListaSintomasItemBinding binding;

        public SintomaViewHolder(@NonNull View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
            binding.buttonVerDescripcionSintoma.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onShowSintoma(binding.getSintoma());
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public SintomaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SintomaViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.lista_sintomas_item, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SintomaViewHolder vh, int i) {
        vh.binding.setSintoma(sintomas.get(i));
        vh.binding.horaSintomaPaciente.setText(new SimpleDateFormat("HH:mm").format(vh.binding.getSintoma().getFechaHora()));
        vh.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return sintomas.size();
    }

}
