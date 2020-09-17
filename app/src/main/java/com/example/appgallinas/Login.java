package com.example.appgallinas;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgallinas.Clases.Usuario;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements Asynchtask {

    private EditText usuario;
    private  EditText contrasenia;
    private ProgressDialog progreso;
    public static Usuario user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario=(EditText)findViewById(R.id.txtcorreo);
        contrasenia=(EditText)findViewById(R.id.txtcontrasenia);

    }
    public void iniciar(View view){

        //startActivity(new Intent(this, Vendedor.class));
        if(usuario.getText().toString().trim().length()>0 && contrasenia.getText().toString().trim().length()>0) {
            if (validarEmail(usuario.getText().toString().trim())) {
                progreso = new ProgressDialog(this);
                progreso.setMessage("Verificando...");
                progreso.show();
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("correo", usuario.getText().toString().trim());
                datos.put("clave", contrasenia.getText().toString().trim());
                WebService ws = new WebService("https://gallinas-force.000webhostapp.com/login.php",
                        datos, Login.this, Login.this);
                ws.execute("POST");
            } else {
                Toast.makeText(this, "Correo Incorrecto", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Por favor llene todos los campos...", Toast.LENGTH_SHORT).show();
        }

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
             lista.add(obj.getString("nombre"));
             lista.add(obj.getString("ROL"));

           if (lista.get(2).trim().equals("Cliente")) {

          Intent in = new Intent(this, Cliente.class);
          in.putExtra("Idusuario",lista.get(0));
          in.putExtra("Nombre",lista.get(1));
          startActivity(in);

        } else {
            Intent in = new Intent(this, Vendedor.class);
            in.putExtra("Idusuario",lista.get(0));
            in.putExtra("Nombre",lista.get(1));
            user=new Usuario();
            user.setId_usuario(Integer.parseInt(lista.get(0)));
            user.setNombre(lista.get(1));
           startActivity(in);
          }
        }
        else {
          Toast.makeText(this,"Credenciales incorrectas",Toast.LENGTH_LONG).show();
        }


    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public  void registrarse(View view){

        Intent in = new Intent(this, RegistraUsuario.class);
        startActivity(in);
    }
}