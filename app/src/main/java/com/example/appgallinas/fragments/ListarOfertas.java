package com.example.appgallinas.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.appgallinas.Adaptadores.AdapterOferta;
import com.example.appgallinas.Clases.Oferta;
import com.example.appgallinas.R;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListarOfertas extends Fragment implements Asynchtask {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String accion="";

    public ListarOfertas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarOfertas.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarOfertas newInstance(String param1, String param2) {
        ListarOfertas fragment = new ListarOfertas();
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

    RecyclerView recyclerView;
    Bundle args = new Bundle();
    Oferta oferta;
    ArrayList<Oferta> lista_ofertas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_listar_ofertas, container, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.rv_ofertas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        traer_ofertas();


        return vista;
    }

    private void traer_ofertas() {
        accion="traer_ofertas";
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("https://gallinas-force.000webhostapp.com/ofertasuscripcion.php", datos, this.getContext(), this);
        ws.execute("POST");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        if(accion.equals("traer_ofertas")){
            JSONObject s = new JSONObject(result);
            JSONArray as= s.getJSONArray("Producto");
            lista_ofertas = new ArrayList<>();
            for(int i=0;i<as.length();i++){
                JSONObject d = as.getJSONObject(i);
                oferta = new Oferta();
                oferta.setRaza(d.getString("raza"));
                oferta.setTipo(d.getString("tipo"));
                oferta.setDescripcion(d.getString("descripcion"));
                oferta.setPeso_minimo(d.getString("Rango_min_Peso"));
                oferta.setPeso_maximo(d.getString("Rango_max_Peso"));
                oferta.setCosto_xmayor(d.getString("PrecioMayor"));
                oferta.setCosto_xmenor(d.getString("PrecioMenor"));
                oferta.setId_oferta(d.getString("idoferta"));
                oferta.setUrl_foto(d.getString("foto_ref"));
                oferta.setValoracion(d.getString("valoracion"));
                oferta.setFecha(d.getString("fecha"));
                oferta.setId_producto(d.getString("Id_Producto"));
                lista_ofertas.add(oferta);
            }
            listar_ofertas();
        }else{
            Toast.makeText(getContext(),result,Toast.LENGTH_LONG).show();
        }

    }

    private void listar_ofertas() {
        AdapterOferta adapterOferta = new AdapterOferta(this.getContext(), lista_ofertas, new AdapterOferta.OnItemClickListener() {
            @Override
            public void onItemClick(String name, final int position) {

                if(name.equals("btn_eliminar")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("¿Está seguro de eliminar?")
                            .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    elimina(position);
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.create();
                    builder.show();
                }else{
                    Fragment fragment = new EditarOferta();
                    args.putString("estado",lista_ofertas.get(position).getTipo());
                    args.putString("descripcion",lista_ofertas.get(position).getDescripcion());
                    args.putString("peso_maximo",lista_ofertas.get(position).getPeso_maximo());
                    args.putString("peso_minimo",lista_ofertas.get(position).getPeso_minimo());
                    args.putString("costo_xmayor",lista_ofertas.get(position).getCosto_xmayor());
                    args.putString("costo_xmenor",lista_ofertas.get(position).getCosto_xmenor());

                    args.putString("id_oferta",lista_ofertas.get(position).getId_oferta());
                    args.putString("id_producto",lista_ofertas.get(position).getId_producto());
                    args.putString("url_foto",lista_ofertas.get(position).getUrl_foto());
                    args.putString("valoracion",lista_ofertas.get(position).getValoracion());
                    args.putString("fecha",lista_ofertas.get(position).getFecha());
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content_frame, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        recyclerView.setAdapter(adapterOferta);
    }

    private void elimina(Integer position){
        accion = "eliminar_oferta";
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("idoferta",lista_ofertas.get(position).getId_oferta());
        WebService ws= new WebService("https://gallinas-force.000webhostapp.com/eliminarOferta.php", datos, this.getContext(), this);
        ws.execute("POST");
    }

    // TODO: Rename method, update argument and hook method into UI event





    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
