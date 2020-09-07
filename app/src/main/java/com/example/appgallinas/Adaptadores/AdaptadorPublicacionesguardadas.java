package com.example.appgallinas.Adaptadores;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.appgallinas.Clases.Listapublicaciones;
import com.example.appgallinas.R;
import java.util.ArrayList;

public class AdaptadorPublicacionesguardadas extends RecyclerView.Adapter<AdaptadorPublicacionesguardadas.ViewHolder> {

    private ArrayList<Listapublicaciones> names;
    private OnItemClickListener mListener;
    private Context contexto;

    public interface OnItemClickListener {
        void onEliminarClick(int position);
        void onContactarClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public AdaptadorPublicacionesguardadas(ArrayList<Listapublicaciones> names, Context context)
    {
        this.names=names;
        this.contexto=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recicler_publicaciones_guardadas, parent, false);
        ViewHolder evh = new ViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titulo.setText(names.get(position).getTitulo());
        holder.descripcion.setText(names.get(position).getDescripción());
        Glide.with(contexto)
                .load(names.get(position).getFotogallina())
                .into(holder.fotoproducto);
        holder.peso.setText(names.get(position).getPeso());
        holder.precio.setText(names.get(position).getPrecio());
        holder.ciudad.setText(names.get(position).getCiudad());
        holder.estado.setText(names.get(position).getEstado());
        holder.correo.setText(names.get(position).getCorreo());
        holder.nombre.setText(names.get(position).getNombre());
        holder.direccion.setText(names.get(position).getDireccion());
        holder.telefono.setText(names.get(position).getTelefono());
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
        TextView correo;
        TextView direccion;
        TextView telefono;
        TextView nombre;
        Button contactar;
        Button eliminar;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            titulo=(TextView) itemView.findViewById(R.id.txttitulo2);
            descripcion=(TextView) itemView.findViewById(R.id.txtdescripcion2);
            fotoproducto=(ImageView) itemView.findViewById(R.id.imageproducto2);
            precio=(TextView) itemView.findViewById(R.id.txtprecio2);
            peso=(TextView) itemView.findViewById(R.id.txtpeso2);
            estado=(TextView) itemView.findViewById(R.id.txtestado2);
            contactar=(Button)itemView.findViewById(R.id.btncontactar);
            ciudad=(TextView) itemView.findViewById(R.id.txtciudad2);
            eliminar=(Button)itemView.findViewById(R.id.btneliminarv);

            correo=(TextView) itemView.findViewById(R.id.txtcorreov);
            direccion=(TextView) itemView.findViewById(R.id.txtdireccionv);
            telefono=(TextView) itemView.findViewById(R.id.txtcelularv);
            nombre=(TextView) itemView.findViewById(R.id.txtnombrev);

            eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEliminarClick(position);
                        }
                    }
                }
            });
            contactar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onContactarClick(position);
                        }
                    }
                }
            });
        }

    }
}


