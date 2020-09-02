package com.example.appgallinas.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<Producto> names;
    private OnItemClickListener mListener;
    private Context contexto;
    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public MyAdapter(ArrayList<Producto> names, Context context)
    {
        this.names=names;
        this.contexto=context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recicler_view_item, parent, false);
        ViewHolder evh = new ViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.titulo.setText(names.get(position).getTitulo());
        holder.descripcion.setText(names.get(position).getDescripción());
        Glide.with(contexto)
                .load(names.get(position).getFotogallina())
                .into(holder.fotoproducto);
        holder.peso.setText(names.get(position).getPeso());
        holder.precio.setText(names.get(position).getPrecio());
        holder.ciudad.setText(names.get(position).getCiudad());
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView descripcion;
        ImageView fotoproducto;
        TextView precio;
        TextView peso;
        TextView estado;
        TextView ciudad;
        Button guardar;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.txttitulo);
            descripcion=(TextView) itemView.findViewById(R.id.txtdescripcion);
            fotoproducto=(ImageView) itemView.findViewById(R.id.imageproducto);
            precio=(TextView) itemView.findViewById(R.id.txtprecio);
            peso=(TextView) itemView.findViewById(R.id.txtpeso);
            estado=(TextView) itemView.findViewById(R.id.txtestado);
            guardar=(Button)itemView.findViewById(R.id.btnañadircli);
            ciudad=(TextView) itemView.findViewById(R.id.txtciudad);

            guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                    guardar.setEnabled(false);
                    guardar.setText("Añadido");
                }
            });
        }

    }
}
