package com.example.appgallinas.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appgallinas.Adaptadores.MyAdapter;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicacionesCliente #newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicacionesCliente extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PublicacionesCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inicio.
     */
    // TODO: Rename and change types and number of parameters
    public static PublicacionesCliente newInstance(String param1, String param2) {
        PublicacionesCliente fragment = new PublicacionesCliente();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    ArrayList<Producto> products;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_inicio, container, false);
        recyclerView=(RecyclerView) vista.findViewById(R.id.Recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        products=new ArrayList<>();
        agregardatos();
        MyAdapter adapter=new MyAdapter(products);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Toast.makeText(getContext(),"Publicación Añadida",Toast.LENGTH_LONG).show();
            }
        });
        return vista;
    }
    public  void  agregardatos(){
        products.add( new Producto(1,0.93,"Pareja de Guineas pequeñas tienen mes y medio a 20 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:0995645141","Viva o muerta",R.drawable.gallina_0,"Venta de gallinas de campo guinea","7-15 libras"));
        products.add( new Producto(2,0.95,"Pareja de Alsaciana tienen mes y medio a 30 la pareja También se vende Guineas adultas, precio 35 la pareja.","Viva o muerta",R.drawable.gallinapiroca,"Venta de gallinas piroca","7-10 libras"));
        products.add( new Producto(3,0.98,"Pareja de Polaca pequeñas tienen mes y medio a 10 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:0995645141","Viva o muerta",R.drawable.gallinapolaca,"Venta de gallinas polaca","6-15 libras"));
        products.add( new Producto(4,0.94,"Pareja de Plymouth  tienen tres meses y medio a 16 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:095864515","Viva o muerta",R.drawable.gallinaplymouth,"Venta de gallinas aplymouth","8-10 libras"));
        products.add( new Producto(5,1.50,"Pareja de Piroca pequeñas tienen mes y medio a 18 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:0995845142","Viva o muerta",R.drawable.gallinawyandotte,"Venta de gallinas wyandotte","9-13 libras"));
        products.add( new Producto(6,1.70,"Pareja de Guineas pequeñas tienen mes y medio a 20 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:0995645141","Viva o muerta",R.drawable.gallinaalsaciana,"Venta de gallinas alsiana","5-15 libras"));
    }
}