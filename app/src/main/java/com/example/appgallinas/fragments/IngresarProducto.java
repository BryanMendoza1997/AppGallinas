package com.example.appgallinas.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appgallinas.Adaptadores.MyAdapter;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngresarProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngresarProducto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog progreso;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IngresarProducto() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngresarProducto.
     */
    // TODO: Rename and change types and number of parameters
    public static IngresarProducto newInstance(String param1, String param2) {
        IngresarProducto fragment = new IngresarProducto();
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
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_inicio, container, false);
        recyclerView=(RecyclerView) vista.findViewById(R.id.Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        products=new ArrayList<>();
        agregardatos();
        MyAdapter adapter=new MyAdapter(products);
        recyclerView.setAdapter(adapter);
        return vista;

    }
    public  void  agregardatos(){
        products.add( new Producto(0.93,"Pareja de Guineas pequeñas tienen mes y medio a 20 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:0995645141","Viva o muerta",R.drawable.gallina_0,"Venta de gallinas de campo",R.drawable.userm,"7-15 libras"));
        products.add( new Producto(0.93,"Pareja de Guineas pequeñas tienen mes y medio a 20 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:0995645141","Viva o muerta",R.drawable.gallina_0,"Venta de gallinas de campo",R.drawable.userm,"7-15 libras"));
        products.add( new Producto(0.93,"Pareja de Guineas pequeñas tienen mes y medio a 20 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:0995645141","Viva o muerta",R.drawable.gallina_0,"Venta de gallinas de campo",R.drawable.userm,"7-15 libras"));
        products.add( new Producto(0.93,"Pareja de Guineas pequeñas tienen mes y medio a 20 la pareja También se vende Guineas adultas, precio 35 la pareja. Tele:0995645141","Viva o muerta",R.drawable.gallina_0,"Venta de gallinas de campo",R.drawable.userm,"7-15 libras"));
    }
    class CargarDatos implements Runnable {
        @Override
        public void run() {
            progreso.setMessage("Consultando del ws...");
            progreso.show();
        }
    }
}