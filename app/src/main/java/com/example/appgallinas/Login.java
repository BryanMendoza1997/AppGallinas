package com.example.appgallinas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity implements Asynchtask {

    private EditText usuario;
    private  EditText contrasenia;
    private ProgressDialog progreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario=(EditText)findViewById(R.id.txtcorreo);
        contrasenia=(EditText)findViewById(R.id.txtcontrasenia);
    }
    public void iniciar(View view){
        progreso=new ProgressDialog(this);
        progreso.setMessage("Verificando...");
        progreso.show();
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("correo",usuario.getText().toString().trim());
        datos.put("clave",contrasenia.getText().toString().trim());
        WebService ws= new WebService("https://fotos-quito-liliana-zambrano.000webhostapp.com/login.php",
                datos, Login.this, Login.this);
        ws.execute("POST");
    }

    @Override
    public void processFinish(String result) throws JSONException {
        boolean bandera=false;
        List<String> lista= new ArrayList<>();
        JSONObject obj = new JSONObject(result);
        bandera=obj.getBoolean("success");
        progreso.hide();
         if(bandera) {

             lista.add(String.valueOf(obj.getInt("idusuario")));
             lista.add(obj.getString("nombre").toString());
             lista.add(obj.getString("ROL").toString());

           if (lista.get(2).toString().trim().equals("Cliente")) {

          Intent in = new Intent(this, Cliente.class);
          in.putExtra("Idusuario",lista.get(0));
          in.putExtra("Nombre",lista.get(1));
          startActivity(in);

        } else {
            Intent in = new Intent(this, Vendedor.class);
            in.putExtra("Idusuario",lista.get(0));
            in.putExtra("Nombre",lista.get(1));
           startActivity(in);
          }
        }
        else {
          Toast.makeText(this,"Credenciales incorrectas",Toast.LENGTH_LONG).show();
        }





    }
}