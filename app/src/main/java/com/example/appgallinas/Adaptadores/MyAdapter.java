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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Producto> names;
    public MyAdapter(ArrayList<Producto> names)
    {
        this.names=names;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recicler_view_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.titulo.setText(names.get(position).getTitulo());
        holder.descripcion.setText(names.get(position).getDescripción());
        holder.fotoproducto.setImageResource(names.get(position).getFotogallina());
        holder.fotoproveedor.setImageResource(names.get(position).getFotoprovedor());
        holder.peso.setText(names.get(position).getPeso());
        holder.precio.setText( String.valueOf(names.get(position).getPrecio()));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView descripcion;
        ImageView fotoproducto;
        ImageView fotoproveedor;
        TextView precio;
        TextView peso;
        TextView estado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.txttitulo);
            descripcion=(TextView) itemView.findViewById(R.id.txtdescripcion);
            fotoproducto=(ImageView) itemView.findViewById(R.id.imageproducto);
            fotoproveedor=(ImageView) itemView.findViewById(R.id.imagenperfil);
            precio=(TextView) itemView.findViewById(R.id.txtprecio);
            peso=(TextView) itemView.findViewById(R.id.txtpeso);
            estado=(TextView) itemView.findViewById(R.id.txtestado);
        }

    }
}
