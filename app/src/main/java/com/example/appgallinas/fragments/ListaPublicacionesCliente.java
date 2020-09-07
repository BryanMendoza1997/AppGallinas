package com.example.appgallinas.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appgallinas.Adaptadores.AdaptadorPublicacionesguardadas;
import com.example.appgallinas.Adaptadores.MyAdapter;
import com.example.appgallinas.Clases.Listapublicaciones;
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

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaPublicacionesCliente#newInstance} factory method to
 */
public class ListaPublicacionesCliente extends Fragment  implements Asynchtask {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaPublicacionesCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPublicacionesCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaPublicacionesCliente newInstance(String param1, String param2) {
        ListaPublicacionesCliente fragment = new ListaPublicacionesCliente();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    ArrayList<Listapublicaciones> publicaciones;
    RecyclerView recyclerViewpublicaciones;
    AdaptadorPublicacionesguardadas adapterpublicaciones;
    String idusuario;
    private ProgressDialog progreso;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_lista_publicaciones_cliente, container, false);
        usuario();
        recyclerViewpublicaciones=(RecyclerView) vista.findViewById(R.id.Recyclerviewlpc);
        recyclerViewpublicaciones.setHasFixedSize(true);
        recyclerViewpublicaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        publicaciones=new ArrayList<>();
        addDatos();
        return  vista;
    }
    public  void  addDatos(){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://gallinas-force.000webhostapp.com/hola.php?idusuario="+idusuario+"", datos, this.getContext(), this);
        ws.execute("POST");
        progreso.show();
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject s = new JSONObject(result);
        JSONArray as= s.getJSONArray("Producto");
        for(int i=0;i<as.length();i++){
            JSONObject d = as.getJSONObject(i);
            publicaciones.add(new Listapublicaciones(
                    "min: "+d.getString("PrecioMenor")+ "$  max: " + d.getString("PrecioMayor")+"$",
                    d.getString("descripcion"),
                    d.getString("Tipo"),
                    d.getString("foto_ref"),
                    "Venta de "+d.getString("raza"),
                    d.getString("Rango_min_Peso")+ " - " + d.getString("Rango_max_Peso")+" lb",
                    Integer.parseInt(d.getString("idoferta")),
                    d.getString("ciudad")+", Ecuador",
                    d.getString("correo"),
                    d.getString("direccion"),
                    d.getString("celular"),
                    d.getString("nombre")+ "  " + d.getString("apellido")));
        }
        listar_publicaciones();
        progreso.hide();
    }
    private void listar_publicaciones() {
        adapterpublicaciones=new AdaptadorPublicacionesguardadas(publicaciones,this.getContext());
        recyclerViewpublicaciones.setAdapter(adapterpublicaciones);
        adapterpublicaciones.setOnItemClickListener(new AdaptadorPublicacionesguardadas.OnItemClickListener() {
            @Override
            public void onEliminarClick(final int position) {
                int oferta=publicaciones.get(position).getIdoferta();
                if(idusuario.length()>0){
                    RequestQueue request = Volley.newRequestQueue(getContext());
                    StringRequest volley=new StringRequest(Request.Method.GET, "https://gallinas-force.000webhostapp.com/eliminarpedido.php?idoferta="+oferta+"&idusuario="+Integer.parseInt(idusuario)+"", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.length()>0){
                                Toast.makeText(getContext(),"Publicación Guardada",Toast.LENGTH_SHORT).show();
                                publicaciones.remove(position);
                                adapterpublicaciones.notifyItemRemoved(position);
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

            @Override
            public void onContactarClick(int position) {
                Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void usuario(){
        SharedPreferences prefe = this.getActivity().getSharedPreferences("MyPREFERENCES", MODE_PRIVATE);
        String id = prefe.getString("Idusuario", "");
        this.idusuario = id;
        if (id.equals("")) {
            Toast.makeText(getContext(), "Error id usuario", Toast.LENGTH_SHORT).show();
        }
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