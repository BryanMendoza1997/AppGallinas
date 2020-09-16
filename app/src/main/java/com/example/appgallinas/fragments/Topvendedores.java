package com.example.appgallinas.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appgallinas.Adaptadores.AdaptadorPublicacionesguardadas;
import com.example.appgallinas.Adaptadores.AdaptadorTopVendedores;
import com.example.appgallinas.Adaptadores.MyAdapter;
import com.example.appgallinas.Clases.MejorVendedor;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.R;
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
 * Use the {@link Topvendedores#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Topvendedores extends Fragment  implements Asynchtask {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Topvendedores() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Topvendedores.
     */
    // TODO: Rename and change types and number of parameters
    public static Topvendedores newInstance(String param1, String param2) {
        Topvendedores fragment = new Topvendedores();
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

    ArrayList<MejorVendedor> vendedores;
    RecyclerView recyclerViewvendedores;
    AdaptadorTopVendedores adaptervendedores;
    private ProgressDialog progresoven;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vistatop = inflater.inflate(R.layout.fragment_topvendedores, container, false);
        recyclerViewvendedores = (RecyclerView) vistatop.findViewById(R.id.Recyclerviewltopvendedores);
        recyclerViewvendedores.setHasFixedSize(true);
        recyclerViewvendedores.setLayoutManager(new LinearLayoutManager(getContext()));
        vendedores = new ArrayList<>();
        addDatos();
        return vistatop;
    }

    public void addDatos() {

        progresoven = new ProgressDialog(getContext());
        progresoven.setMessage("Cargando...");
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("https://gallinas-force.000webhostapp.com/mayorvaloracion.php", datos, this.getContext(), this);
        ws.execute("GET");
        progresoven.show();
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject s = new JSONObject(result);
        JSONArray as = s.getJSONArray("Producto");
        for (int i = 0; i < as.length(); i++) {
            JSONObject d = as.getJSONObject(i);
            vendedores.add(new MejorVendedor(R.drawable.restodepuestos,
                    d.getString("nombre") + "  " + d.getString("apellido"),
                    d.getString("ciudad"),
                    d.getString("correo"),
                    Float.parseFloat(d.getString("Valoracion"))));
        }
        for(int i=0;i<vendedores.size();i++){
            if(i==0){
                vendedores.get(i).setFoto(R.drawable.primerpuesto);
            }
            if(i==1){
                vendedores.get(i).setFoto(R.drawable.segundopuesto);
            }
            if(i==2){
                vendedores.get(i).setFoto(R.drawable.tercerpuesto);
            }
        }
        listar_vendedores();
        progresoven.hide();
    }

    private void listar_vendedores() {
        adaptervendedores = new AdaptadorTopVendedores(vendedores, this.getContext());
        recyclerViewvendedores.setAdapter(adaptervendedores);
    }
}