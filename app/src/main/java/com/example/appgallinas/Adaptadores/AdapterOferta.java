package com.example.appgallinas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgallinas.Clases.Oferta;
import com.example.appgallinas.R;

import java.util.ArrayList;

public class AdapterOferta extends RecyclerView.Adapter<AdapterOferta.ViewHolder> {
    private ArrayList<Oferta> ofertas;
    private OnItemClickListener listener;
    private Context context;
    public AdapterOferta(Context context, ArrayList<Oferta> ofertas, OnItemClickListener listener) {
        this.ofertas=ofertas;
        this.listener=listener;
        this.context= context;
    }

    @NonNull
    @Override
    public AdapterOferta.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recicler_view_oferta,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String val_sus="";
        if(!ofertas.get(position).getSuscripcion().equals("null")){
            val_sus = " (Con Suscripción)";
        }else{
            val_sus = "";
        }

        holder.lbl_raza.setText(ofertas.get(position).getRaza());
        holder.lbl_tipo.setText(ofertas.get(position).getTipo());
        holder.lbl_fecha.setText(ofertas.get(position).getFecha()+val_sus);
        holder.lbl_descripcion.setText(ofertas.get(position).getDescripcion());
        holder.bind(ofertas.get(position).getRaza(), listener);
    }

    @Override
    public int getItemCount() {
        return ofertas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView lbl_raza, lbl_tipo, lbl_fecha, lbl_descripcion;
        private Button btn_editar, btn_eliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lbl_raza=(TextView) itemView.findViewById(R.id.lbl_raza_gallina);
            lbl_tipo=(TextView) itemView.findViewById(R.id.lbl_tipo);
            lbl_fecha=(TextView) itemView.findViewById(R.id.lbl_fecha);
            lbl_descripcion=(TextView) itemView.findViewById(R.id.lbl_descripcion);
            btn_editar=(Button)itemView.findViewById(R.id.btn_editar);
            btn_eliminar=(Button)itemView.findViewById(R.id.btn_eliminar);
        }
        public void bind(final String name, final AdapterOferta.OnItemClickListener listener){
            btn_editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("btn_editar",getAdapterPosition());
                }
            });

            btn_eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick("btn_eliminar",getAdapterPosition());
                }
            });
        }

    }

    public  interface OnItemClickListener{
        void onItemClick(String name, int position);

    }
}
