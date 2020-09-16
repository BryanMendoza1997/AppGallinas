package com.example.appgallinas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgallinas.Clases.MejorVendedor;
import com.example.appgallinas.R;

import java.util.ArrayList;

public class AdaptadorTopVendedores  extends RecyclerView.Adapter<AdaptadorTopVendedores.ViewHolder> {
    private ArrayList<MejorVendedor> lista;
    private Context contexto;

    public AdaptadorTopVendedores(ArrayList<MejorVendedor> lista, Context contexto) {
        this.lista = lista;
        this.contexto = contexto;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recicler_view_mejores_vendedores,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorTopVendedores.ViewHolder holder, int position) {
        holder.nombre.setText(lista.get(position).getNombre());
        holder.ciudad.setText(lista.get(position).getCiudad());
        holder.correo.setText(lista.get(position).getCorreo());
        holder.image.setImageResource(lista.get(position).getFoto());
        holder.estrellas.setRating(lista.get(position).getCalificacion());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView ciudad;
        TextView correo;
        ImageView image;
        RatingBar estrellas;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre=(TextView) itemView.findViewById(R.id.txtvendedortop);
            image=(ImageView) itemView.findViewById(R.id.imagetop);
            ciudad=(TextView) itemView.findViewById(R.id.txtciudadvendedortop);
            correo=(TextView) itemView.findViewById(R.id.txtcorreovendedortop);
            estrellas=(RatingBar) itemView.findViewById(R.id.ratingBarTop);
            estrellas.setIsIndicator(true);

        }

    }
}
