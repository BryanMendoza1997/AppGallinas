package com.example.appgallinas.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgallinas.Adaptadores.AdapterGallinas;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.R;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;
import com.google.android.material.textfield.TextInputEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.appgallinas.Login.user;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngresarOferta#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngresarOferta extends Fragment implements View.OnClickListener, Asynchtask {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog progreso;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText descripcion, peso_maximo, peso_minimo, costo_xmayor, costo_xmenor;
    private MaterialBetterSpinner estado;
    private Button btn_registrar_oferta;

    private String[] estados={"Viva","Muerta"};
    private Integer id_producto, id_usuario;
    private String url_foto;

    public IngresarOferta() {
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
    public static IngresarOferta newInstance(String param1, String param2) {
        IngresarOferta fragment = new IngresarOferta();
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
    TextView peso;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_blank, container, false);
        descripcion=vista.findViewById(R.id.txt_descripcion);
        //estado=vista.findViewById(R.id.cmb_estado);
        peso_maximo= vista.findViewById(R.id.txt_pesomaximo);
        peso_minimo=vista.findViewById(R.id.txt_pesominimo);
        costo_xmayor = vista.findViewById(R.id.txt_costoxmayor);
        costo_xmenor = vista.findViewById(R.id.txt_costoxmenor);
        btn_registrar_oferta=vista.findViewById(R.id.btn_registrar_oferta);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, estados);
        estado = (MaterialBetterSpinner) vista.findViewById(R.id.cmb_estado);
        estado.setAdapter(arrayAdapter);


        peso=(TextView) vista.findViewById(R.id.pesoM);
        String texto = getArguments().getString("Titulo");

        id_producto = getArguments().getInt("id_producto");
        url_foto = getArguments().getString("url_foto");
        id_usuario = user.getId_usuario();
        Toast.makeText(vista.getContext(), "nada"+id_producto+" "+id_usuario.toString(), Toast.LENGTH_LONG).show();
        //peso.setText(texto);
        btn_registrar_oferta.setOnClickListener(this);
        return vista;
    }

    @Override
    public void onClick(View v) {
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("idusuario", String.valueOf(id_usuario));
        datos.put("Id_Producto", String.valueOf(id_producto));
        datos.put("Rango_min_Peso", peso_minimo.getText().toString());
        datos.put("Rango_max_Peso", peso_maximo.getText().toString());
        datos.put("Tipo", estado.getText().toString());
        datos.put("foto", url_foto);
        datos.put("descripcion", descripcion.getText().toString());
        datos.put("PrecioMayor", costo_xmayor.getText().toString());
        datos.put("PrecioMenor", costo_xmenor.getText().toString());
        datos.put("valoracion", "0");
        datos.put("fecha", "12/08/2020");

        WebService ws = new WebService("https://gallinas-force.000webhostapp.com/ofertainsertar.php",
                datos, this.getContext(), this);
        ws.execute("POST");

        //Toast.makeText(this.getContext(), descripcion.getText().toString()+"nada", Toast.LENGTH_LONG).show();
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Toast.makeText(this.getContext(), result, Toast.LENGTH_LONG).show();
    }
}