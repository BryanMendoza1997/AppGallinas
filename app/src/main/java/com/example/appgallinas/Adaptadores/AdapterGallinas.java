package com.example.appgallinas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.Clases.ProductoOferta;
import com.example.appgallinas.R;

import java.util.ArrayList;

public class AdapterGallinas extends RecyclerView.Adapter<AdapterGallinas.ViewHolder> {
    private ArrayList<ProductoOferta> names;
    private OnItemClickListener listener;
    private Context context;
    public AdapterGallinas(Context context, ArrayList<ProductoOferta> names, OnItemClickListener listener)
    {
        this.names=names;
        this.listener=listener;
        this.context= context;
    }

    @NonNull
    @Override
    public AdapterGallinas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recicler_view_gallinas,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGallinas.ViewHolder holder, int position) {
        holder.titulo.setText(names.get(position).getRaza());
        holder.descripcion.setText(names.get(position).getDescripcion());
        //holder.fotoproducto.setImageResource(names.get(position).getUrl_foto());
        Glide.with(context)
                .load(names.get(position).getUrl_foto())
                .into(holder.fotoproducto);
        holder.bind(names.get(position).getNombre(), listener);
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
        Button agregar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.txttitulo1);
            descripcion=(TextView) itemView.findViewById(R.id.txtdescripcion1);
            fotoproducto=(ImageView) itemView.findViewById(R.id.imageproducto1);
            estado=(TextView) itemView.findViewById(R.id.txtEstado1);
            agregar=(Button)itemView.findViewById(R.id.btnañadir1);
        }
        public void bind(final String name, final OnItemClickListener listener){
            agregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onItemClick(name,getAdapterPosition());
                }
            });
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = v.getId();
                    switch (id){
                        case R.id.btnañadir1:
                            listener.onItemClick(name,getAdapterPosition());
                            break;
                    }

                }
            });*/
        }

    }
    public  interface OnItemClickListener{
        void onItemClick(String name, int position);

    }
}
