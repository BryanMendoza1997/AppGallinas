package com.example.appgallinas.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appgallinas.Adaptadores.AdapterGallinas;
import com.example.appgallinas.Adaptadores.MyAdapter;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.Clases.ProductoOferta;
import com.example.appgallinas.Login;
import com.example.appgallinas.R;
import com.example.appgallinas.Vendedor;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngresarProducto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngresarProducto extends Fragment implements Asynchtask  {

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


    ArrayList<ProductoOferta> products=null;
    ProductoOferta p = new ProductoOferta();
    RecyclerView recyclerView;
    Bundle args = new Bundle();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_ingresar_producto, container, false);
        recyclerView=(RecyclerView) vista.findViewById(R.id.RecyclerviewLstGallinas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        products=new ArrayList<>();
        agregardatos();
        return vista;
    }
    public  void  agregardatos(){

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://gallinas-force.000webhostapp.com/listarProducto.php", datos, this.getContext(), this);
        ws.execute("POST");
    }

    @Override
    public void processFinish(String result) throws JSONException {

        JSONObject s = new JSONObject(result);
        JSONArray as= s.getJSONArray("Producto");
        for(int i=0;i<as.length();i++){
            JSONObject d = as.getJSONObject(i);
            products.add(new ProductoOferta(d.getString("descripcion"),d.getString("nombre") , Integer.parseInt(d.getString("Id_Producto")), d.getString("raza"), d.getString("foto_ref")));
        }
        listar_gallinas();
    }

    private void listar_gallinas() {
        AdapterGallinas adapter=new AdapterGallinas(products,getContext(), new AdapterGallinas.OnItemClickListener() {
            @Override
            public void onItemClick(String name, int position) {
                Fragment seguir= new IngresarOferta();
                args.putInt("id_producto", products.get(position).getId_producto());
                args.putString("url_foto", products.get(position).getUrl_foto());
                seguir.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, seguir);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}