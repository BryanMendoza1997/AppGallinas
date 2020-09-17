package com.example.appgallinas.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appgallinas.Adaptadores.MyAdapter;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.R;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PublicacionesCliente #newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublicacionesCliente extends Fragment  implements Asynchtask {

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
    MyAdapter adapter;
    String idusuarios;
    private ProgressDialog progreso;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_inicio, container, false);
        recyclerView=(RecyclerView) vista.findViewById(R.id.Recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        products=new ArrayList<>();
        usuario();
        addDatos();
        return vista;
    }
    public  void  addDatos(){

        //progreso=new ProgressDialog(getContext());
        //progreso.setMessage("Cargando...");
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://gallinas-force.000webhostapp.com/ofertasuscripcion.php?idusuario="+Integer.parseInt(idusuarios)+"", datos, this.getContext(), this);
        ws.execute("GET");
        //progreso.show();
    }

   /* public void agregardatos(){

        RequestQueue request = Volley.newRequestQueue(this.getContext());
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        StringRequest volley=new StringRequest(Request.Method.POST, "https://gallinas-force.000webhostapp.com/ofertasuscripcion.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray Jarray  = object.getJSONArray("Producto");
                for(int i=0; i< Jarray.length();i++){
                    JSONObject banco= Jarray.getJSONObject(i);
                    products.add(new Producto(Integer.parseInt(banco.getString("idoferta")),
                            banco.getString("PrecioMenor")+ "-" + banco.getString("PrecioMayor"),
                            banco.getString("descripcion"),
                            banco.getString("tipo"),
                            banco.getString("foto_ref"),
                            banco.getString("raza"),
                            banco.getString("Rango_min_Peso")+ "-" + banco.getString("Rango_max_Peso"),
                            banco.getString("ciudad")+", Ecuador"));
                    }
                }
                catch (JSONException e) {
                        progreso.hide();
                        e.printStackTrace();
                    }

            } }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
       request.add(volley);
        progreso.hide();
    }*/

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject s = new JSONObject(result);
        JSONArray as= s.getJSONArray("Producto");
        for(int i=0;i<as.length();i++){
            JSONObject d = as.getJSONObject(i);
            products.add(new Producto(Integer.parseInt(d.getString("idoferta")),
                    "min: "+d.getString("PrecioMenor")+ "$  max: " + d.getString("PrecioMayor")+"$",
                    d.getString("descripcion"),
                    d.getString("tipo"),
                    d.getString("foto_ref"),
                    "Venta de "+d.getString("raza"),
                    d.getString("Rango_min_Peso")+ " - " + d.getString("Rango_max_Peso")+" lb",
                    d.getString("ciudad")+", Ecuador"));
        }
        listar_publicaciones();
        //progreso.hide();
    }

    private void listar_publicaciones() {
        adapter=new MyAdapter(products,getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(final int position) {
                   int oferta=products.get(position).getIdoferta();
                    if(idusuarios.length()>0){
                        RequestQueue request = Volley.newRequestQueue(getContext());
                        StringRequest volley=new StringRequest(Request.Method.GET, "https://gallinas-force.000webhostapp.com/insertpedido2.php?idusuario="+Integer.parseInt(idusuarios)+"&idoferta="+oferta+"&fecha="+getDateTime()+"&valoracion=0.0", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.length()>0){
                                    products.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    Toast.makeText(getContext(),"Publicación Guardada",Toast.LENGTH_SHORT).show();
                                }

                            } }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(),"Publicación no guardada",Toast.LENGTH_SHORT).show();
                            }
                        });
                        request.add(volley);
                    }else {
                        Toast.makeText(getContext(),"Error id usuario",Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }
    public void usuario(){
        SharedPreferences prefe = this.getActivity().getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        String id = prefe.getString("Idusuario", "");
        this.idusuarios = id;
        if (id.equals("")) {
            Toast.makeText(getContext(), "Error id usuario", Toast.LENGTH_SHORT).show();
        }
    }
   /* private boolean band;
    public boolean validar(int posicion) {
        SharedPreferences prefe = this.getActivity().getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        String id = prefe.getString("Idusuario", "");
        this.idusuarios = id;
        if (id.equals("")) {
            Toast.makeText(getContext(), "Error id usuario", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
            int iduser = Integer.parseInt(id);
            RequestQueue request = Volley.newRequestQueue(this.getContext());
            StringRequest volley = new StringRequest(Request.Method.GET, "https://gallinas-force.000webhostapp.com/boleanpedido.php?idoferta=" + posicion + "&idusuario=" + iduser + "", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.length() > 0) {

                    }else {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            request.add(volley);
        }

        return band;
    }*/

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Refresh tab data:

        if (getFragmentManager() != null) {

            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}