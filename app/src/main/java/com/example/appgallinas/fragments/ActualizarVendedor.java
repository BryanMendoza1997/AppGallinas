package com.example.appgallinas.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.appgallinas.R;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.appgallinas.Login.user;


public class ActualizarVendedor extends Fragment implements Asynchtask, View.OnClickListener {


    private TextInputEditText txt_nombre,txt_apellido, txt_correo, txt_ciudad, txt_celular, txt_direccion, txt_clave, txtConfirma_clave, txt_rol;
    private Button btnActualizarUsuario;
    private String caso;


    public ActualizarVendedor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActualizarVendedor.
     */
    // TODO: Rename and change types and number of parameters
    public static ActualizarVendedor newInstance(String param1, String param2) {
        ActualizarVendedor fragment = new ActualizarVendedor();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_actualizar_vendedor, container, false);
        txt_nombre = vista.findViewById(R.id.txt_nombre_edit);
        txt_apellido = vista.findViewById(R.id.txt_apellido_edit);
        txt_correo = vista.findViewById(R.id.txt_correo_edit);
        txt_rol = vista.findViewById(R.id.txt_rol_edit);
        txt_ciudad = vista.findViewById(R.id.txt_ciudad_edit);
        txt_celular = vista.findViewById(R.id.txt_celular_edit);
        txt_direccion = vista.findViewById(R.id.txt_direccion_edit);
        txt_clave = vista.findViewById(R.id.txt_clave_edit);
        txtConfirma_clave = vista.findViewById(R.id.txt_clave_confirma_edit);

        btnActualizarUsuario = vista.findViewById(R.id.btnEditarUsuario); btnActualizarUsuario.setOnClickListener(this);

        traer_datos();
        return  vista;
    }

    private void traer_datos() {
        Map<String, String> datos = new HashMap<String, String>();
        caso = "traer_datos";
        WebService ws = new WebService("https://gallinas-force.000webhostapp.com/llamausuario.php?idusuario="+user.getId_usuario()+"",
                datos, this.getContext(), this);
        ws.execute("GET");
    }


    @Override
    public void processFinish(String result) throws JSONException {
        if(caso.equals("traer_datos")){
            JSONObject datos = new JSONObject(result);
            JSONArray info = datos.getJSONArray("Producto");
            JSONObject dd = info.getJSONObject(0);
            txt_nombre.setText(dd.getString("nombre"));
            txt_apellido.setText(dd.getString("apellido"));
            txt_correo.setText(dd.getString("correo"));
            txt_ciudad.setText(dd.getString("ciudad"));
            txt_celular.setText(dd.getString("celular"));
            txt_direccion.setText(dd.getString("direccion"));
            txt_clave.setText(dd.getString("clave"));
            txtConfirma_clave.setText(dd.getString("clave"));
            txt_rol.setText(dd.getString("ROL"));
            txt_rol.setEnabled(false);
        }
        if(caso.equals("actualizar_datos")){
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEditarUsuario:
                actualizar_usuario();
                break;
        }
    }

    private void actualizar_usuario() {
        if(campos_llenos()&&claves_iguales()) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("idusuario", String.valueOf(user.getId_usuario()));
            datos.put("nombre", txt_nombre.getText().toString());
            datos.put("apellido", txt_apellido.getText().toString());
            datos.put("correo", txt_correo.getText().toString());
            datos.put("celular", txt_celular.getText().toString());
            datos.put("ciudad", txt_ciudad.getText().toString());
            datos.put("direccion", txt_direccion.getText().toString());
            datos.put("clave", txt_clave.getText().toString());
            datos.put("ROL", txt_rol.getText().toString());
            caso = "actualizar_datos";
            WebService ws = new WebService("https://gallinas-force.000webhostapp.com/actualizarUsuario.php",
                    datos, this.getContext(), this);
            ws.execute("POST");
        }else{
            Toast.makeText(getContext(), "Campos incompletos", Toast.LENGTH_LONG).show();
        }
    }

    private boolean claves_iguales() {
        if(txt_clave.getText().toString().equals(txtConfirma_clave.getText().toString()))
            return true;
        else
            return false;
    }

    Boolean campos_llenos(){
        if(!txt_nombre.getText().toString().equals("")&&
            !txt_apellido.getText().toString().equals("")&&
            !txt_correo.getText().toString().equals("")&&
            !txt_celular.getText().toString().equals("")&&
            !txt_ciudad.getText().toString().equals("")&&
            !txt_direccion.getText().toString().equals("")&&
            !txt_clave.getText().toString().equals("")&&
            !txt_rol.getText().toString().equals("")){
            return true;
        }else
            return false;
    }

}
