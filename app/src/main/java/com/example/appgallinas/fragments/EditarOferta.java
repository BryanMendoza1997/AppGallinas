package com.example.appgallinas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appgallinas.R;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;
import com.google.android.material.textfield.TextInputEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import static com.example.appgallinas.Login.user;


public class EditarOferta extends Fragment implements View.OnClickListener, Asynchtask {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText descripcion, peso_maximo, peso_minimo, costo_xmayor, costo_xmenor;
    private String id_producto, id_oferta, url_foto, valoracion, fecha;
    private MaterialBetterSpinner estado;
    private Button btn_editar_oferta;

    private String[] estados={"En pie","Pelada", "En pie o Pelada"};

    public EditarOferta() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarOferta.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarOferta newInstance(String param1, String param2) {
        EditarOferta fragment = new EditarOferta();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_editar_oferta, container, false);
        descripcion=vista.findViewById(R.id.txt_descripcion);
        //estado=vista.findViewById(R.id.cmb_estado);
        peso_maximo= vista.findViewById(R.id.txt_pesomaximo);
        peso_minimo=vista.findViewById(R.id.txt_pesominimo);
        costo_xmayor = vista.findViewById(R.id.txt_costoxmayor);
        costo_xmenor = vista.findViewById(R.id.txt_costoxmenor);
        btn_editar_oferta=vista.findViewById(R.id.btn_editar_oferta);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, estados);
        estado = (MaterialBetterSpinner) vista.findViewById(R.id.cmb_estado);
        estado.setAdapter(arrayAdapter);

        descripcion.setText(getArguments().getString("descripcion"));
        peso_maximo.setText(getArguments().getString("peso_maximo"));
        peso_minimo.setText(getArguments().getString("peso_minimo"));
        costo_xmayor.setText(getArguments().getString("costo_xmayor"));
        costo_xmenor.setText(getArguments().getString("costo_xmenor"));

        url_foto=getArguments().getString("url_foto");
        id_oferta=getArguments().getString("id_oferta");
        id_producto=getArguments().getString("id_producto");
        valoracion=getArguments().getString("valoracion");
        fecha=getArguments().getString("fecha");
        btn_editar_oferta.setOnClickListener(this);
        //Toast.makeText(getContext(),getArguments().getString("estado") , Toast.LENGTH_LONG).show();
        return vista;
    }


    @Override
    public void onClick(View v) {
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("idoferta", id_oferta);
        datos.put("idusuario", String.valueOf(user.getId_usuario()));
        datos.put("Id_Producto", id_producto);
        datos.put("Rango_min_Peso", peso_minimo.getText().toString());
        datos.put("Rango_max_Peso", peso_maximo.getText().toString());
        datos.put("Tipo", estado.getText().toString());
        datos.put("foto", url_foto);
        datos.put("descripcion", descripcion.getText().toString());
        datos.put("PrecioMayor", costo_xmayor.getText().toString());
        datos.put("PrecioMenor", costo_xmenor.getText().toString());
        datos.put("valoracion", valoracion);
        datos.put("fecha", fecha);

        WebService ws = new WebService("https://gallinas-force.000webhostapp.com/actualizarOferta.php",
                datos, this.getContext(), this);
        ws.execute("POST");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
    }
}
