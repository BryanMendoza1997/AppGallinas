package com.example.appgallinas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgallinas.Clases.Usuario;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;
import com.google.android.material.textfield.TextInputEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.appgallinas.Login.user;

public class RegistraUsuario extends AppCompatActivity implements Asynchtask {

    private TextInputEditText txt_nombre, txt_apellido, txt_correo, txt_celular, txt_ciudad, txt_direccion, txt_clave, txt_verifica_clave, txt_rol;
    private String ejec_service="";
    private Boolean existe=null;
    private String[] tiposIncidencias={"Cliente","Vendedor"};
    private MaterialBetterSpinner tipo_usuario;
    private ProgressDialog dialog;
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
        //txt_rol = (TextInputEditText) findViewById(R.id.txt_rol);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, tiposIncidencias);
        tipo_usuario = (MaterialBetterSpinner)
                findViewById(R.id.android_material_design_spinner);
        tipo_usuario.setAdapter(arrayAdapter);
    }

    public void registrar(){
        //valida_correeov();
        try {
            if(!existe&&existe!=null) {
                if (txt_clave.getText().toString().equals(txt_verifica_clave.getText().toString())) {
                    Map<String, String> datos = new HashMap<String, String>();
                    datos.put("nombre", txt_nombre.getText().toString());
                    datos.put("apellido", txt_apellido.getText().toString());
                    datos.put("correo", txt_correo.getText().toString());
                    datos.put("celular", txt_celular.getText().toString());
                    datos.put("ciudad", txt_ciudad.getText().toString());
                    datos.put("direccion", txt_direccion.getText().toString());
                    datos.put("clave", txt_clave.getText().toString());
                    datos.put("ROL", tipo_usuario.getText().toString());
                    ejec_service = "inserta_usuario";

                    WebService ws = new WebService("https://gallinas-force.000webhostapp.com/insertarUsuario.php",
                            datos, this, this);
                    ws.execute("POST");
                } else {
                    dialog.hide();
                    Toast.makeText(this, "Las claves deben ser iguales", Toast.LENGTH_LONG).show();
                }
            }else{
                dialog.hide();
                Toast.makeText(this, "Ya existe una cuenta con el correo "+txt_correo.getText().toString(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}
    }

    public void valida_correeo(View v) {
        //registrar();

        Map<String, String> datos = new HashMap<String, String>();
        datos.put("correo",txt_correo.getText().toString());
        ejec_service="consulta_correo";
        dialog = new ProgressDialog(this);
        dialog.setMessage("Registrando..."); dialog.show();
        WebService ws= new WebService("https://gallinas-force.000webhostapp.com/validcorreo.php",
                datos, this, this);
        ws.execute("POST");
    }


    @Override
    public void processFinish(String result) throws JSONException {

        if(ejec_service.equals("inserta_usuario")){
            result = result.replace("\r\n","");
            if(result.equals("datos_guardados")) {
                dialog.hide();
                trae_datos();
                Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_LONG).show();

            }
            else
                Toast.makeText(this, "Error al registrar "+result, Toast.LENGTH_LONG).show();
        }else if(ejec_service.equals("consulta_correo")){
            if(result.length()>0){
                existe=true;
                registrar();
            }else {
                existe = false;
                registrar();

            }
        }else if(ejec_service.equals("traer_datos")){
            JSONObject obj = new JSONObject(result);
            user = new Usuario();
            user.setId_usuario(obj.getInt("idusuario"));
            user.setNombre(obj.getString("nombre"));
            activity_correspondiente(obj.getInt("idusuario"), obj.getString("nombre"));
        }
    }

    private void trae_datos() {
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("correo", txt_correo.getText().toString().trim());
        datos.put("clave", txt_clave.getText().toString().trim());
        ejec_service="traer_datos";
        WebService ws = new WebService("https://gallinas-force.000webhostapp.com/login.php",
                datos, this, this);
        ws.execute("POST");
    }

    private void activity_correspondiente(Integer id_usuario, String nombre_usuario) {
        if(tipo_usuario.getText().toString().equals("Cliente")) {
            startActivity(new Intent(this, Cliente.class));
        }else {
            Intent intent = new Intent(this, Vendedor.class);
            intent.putExtra("id_user",String.valueOf(id_usuario));
            intent.putExtra("Nombre",nombre_usuario);
            startActivity(intent);
        }
    }
}
