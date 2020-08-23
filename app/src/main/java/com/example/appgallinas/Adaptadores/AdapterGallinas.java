package com.example.appgallinas.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.R;

import java.util.ArrayList;

public class AdapterGallinas extends RecyclerView.Adapter<AdapterGallinas.ViewHolder> {
    private ArrayList<Producto> names;
    public AdapterGallinas(ArrayList<Producto> names)
    {
        this.names=names;
    }

    @NonNull
    @Override
    public AdapterGallinas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recicler_view_gallinas,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGallinas.ViewHolder holder, int position) {
        holder.titulo.setText(names.get(position).getTitulo());
        holder.descripcion.setText(names.get(position).getDescripción());
        holder.fotoproducto.setImageResource(names.get(position).getFotogallina());
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView descripcion;
        ImageView fotoproducto;
        TextView estado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.txttitulo);
            descripcion=(TextView) itemView.findViewById(R.id.txtdescripcion);
            fotoproducto=(ImageView) itemView.findViewById(R.id.imageproducto);
            estado=(TextView) itemView.findViewById(R.id.txtEstado);
        }

    }
}