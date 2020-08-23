package com.example.appgallinas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistraUsuario extends AppCompatActivity implements Asynchtask {

    private TextInputEditText txt_nombre, txt_apellido, txt_correo, txt_celular, txt_ciudad, txt_direccion, txt_clave, txt_verifica_clave, txt_rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_usuario);
        //Toast.makeText(this, "Llega", Toast.LENGTH_LONG).show();
        txt_nombre = (TextInputEditText) findViewById(R.id.txt_nombre);
        txt_apellido = (TextInputEditText) findViewById(R.id.txt_apellido);
        txt_correo = (TextInputEditText) findViewById(R.id.txt_correo);
        txt_celular = (TextInputEditText) findViewById(R.id.txt_celular);
        txt_ciudad = (TextInputEditText) findViewById(R.id.txt_ciudad);
        txt_direccion = (TextInputEditText) findViewById(R.id.txt_direccion);
        txt_clave = (TextInputEditText) findViewById(R.id.txt_clave);
        txt_verifica_clave = (TextInputEditText) findViewById(R.id.txt_clave_confirma);
        txt_rol = (TextInputEditText) findViewById(R.id.txt_rol);
    }

    public void registrar(View v){
        if(txt_clave.getText().toString().equals(txt_verifica_clave.getText().toString())){
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("nombre",txt_nombre.getText().toString());
            datos.put("apellido",txt_apellido.getText().toString());
            datos.put("correo",txt_correo.getText().toString());
            datos.put("celular",txt_celular.getText().toString());
            datos.put("ciudad",txt_ciudad.getText().toString());
            datos.put("direccion",txt_direccion.getText().toString());
            datos.put("clave",txt_clave.getText().toString());
            datos.put("ROL",txt_rol.getText().toString());
            WebService ws= new WebService("https://fotos-quito-liliana-zambrano.000webhostapp.com/insertarUsuario.php",
                    datos, this, this);
            ws.execute("POST");
        }else{
            Toast.makeText(this, "Las claves deben ser iguales", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void processFinish(String result) throws JSONException {
        if(result.equals("datos_guardados"))
            Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Error al registrar "+result, Toast.LENGTH_LONG).show();
    }
}
