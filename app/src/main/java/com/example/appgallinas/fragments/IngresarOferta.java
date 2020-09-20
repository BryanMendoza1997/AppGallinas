package com.example.appgallinas.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.appgallinas.Clases.Producto;
import com.example.appgallinas.R;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;
import com.google.android.material.textfield.TextInputEditText;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private TextView txt_estado_pago;
    private MaterialBetterSpinner estado;
    private Button btn_registrar_oferta;
    private CheckBox chk_suscribirse;
    private ImageView img_gallina;

    private String[] estados={"En pie","Pelada", "En pie o Pelada"};
    private Integer id_producto, id_usuario;
    private String url_foto;
    private Boolean se_suscribe = false, fue_a_procesar = false, no_pago = false;
    private String monto = "5", caso_servicio;
    private String token = "NgvY0-Bbd211lP0bmb3EiU3rDMt7dopilTvo1qX57phktoxMtfa--MXzUVwoUnh1PQBoS_341Yo3KT94ozfXa_FE3aX_BQbkfpjUTITZvw6kC_1TYEcLUuEbEzDQx_rR0I2HeyO7Yt8HsKYZBFLwBz-3epEjS01yKLdML9DjTsn-s6E5dQJp_RMdnpf46uc8w7B_U56cEFXbIGKj98xcSBNPTi2cZPIFE6HQXe1oav_1basZeIbWOmMEW4EbjzemiaAWdvsJynrlJfLktH2CE-U5r78jxLy9eYqoRrh9N318tNyO9hbQi6ySM5tplQ_a-nAsEA";

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
        chk_suscribirse = vista.findViewById(R.id.chk_suscribirse);
        txt_estado_pago = vista.findViewById(R.id.txt_estado_pago);
        img_gallina = vista.findViewById(R.id.img_gallina);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, estados);
        estado = (MaterialBetterSpinner) vista.findViewById(R.id.cmb_estado);
        estado.setAdapter(arrayAdapter);
        peso=(TextView) vista.findViewById(R.id.pesoM);
        String texto = getArguments().getString("Titulo");
        id_producto = getArguments().getInt("id_producto");
        url_foto = getArguments().getString("url_foto");
        Glide.with(getActivity()).load(url_foto).into(img_gallina);
        id_usuario = user.getId_usuario();
        btn_registrar_oferta.setOnClickListener(this);
        chk_suscribirse.setOnClickListener(this);

        return vista;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_registrar_oferta:
                if(se_suscribe){
                    if(txt_estado_pago.getText().toString().trim().equals("¡Pago existoso!"))
                        guardar_oferta();
                    else
                        if(campos_validos()) registrado_en_payphone(user.getCelular().substring(1,user.getCelular().length()));
                }else {
                    guardar_oferta();
                }
                /*if(txt_estado_pago.getText().toString().equals(R.string.text_estado_pago_exitoso))
                    guardar_oferta();*/
                break;
            case R.id.chk_suscribirse:
                cambio_metodo_creacion();
                break;
        }
    }

    public void registrado_en_payphone(String celular){
        AndroidNetworking.get("https://pay.payphonetodoesposible.com/api/Users/"+celular+"/region/593")
                .addHeaders("Authorization","Bearer "+token)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ir_a_pagar();

                    }
                    @Override
                    public void onError(ANError anError) {
                        //dialogo para elegir si registrarse o no....
                        dialogo_registro_payphone();
                        //Toast.makeText(getContext(), "No está registrado",Toast.LENGTH_LONG).show();

                    }
                });
    }

    public void ir_a_pagar(){

        if(!no_pago){
            String tiempo = tiempo_actual();
            Map<String,String> datos=new HashMap<>();
            datos.put("phoneNumber", user.getCelular().substring(1,user.getCelular().length()));
            datos.put("countryCode", "593");
            datos.put("clientUserId", String.valueOf(user.getId_usuario()));
            datos.put("reference", "Pago");
            datos.put("responseUrl", "none");
            datos.put("amount", "001");
            datos.put("amountWithTax", "0");
            datos.put("amountWithoutTax", "001");
            datos.put("tax", "0");
            datos.put("clientTransactionId", tiempo);
            //Toast.makeText(getContext(), tiempo,Toast.LENGTH_LONG).show();
            JSONObject jsn_datos=new JSONObject(datos);
            fue_a_procesar = true;
            AndroidNetworking.post("https://pay.payphonetodoesposible.com/api/Sale")
                    .addJSONObjectBody(jsn_datos)
                    .addHeaders("Authorization","Bearer "+token)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                //descripcion.setText(response.getString("transactionId"));
                                descripcion.setTag(response.getString("transactionId"));
                                abrir_payphone();
                            }catch (JSONException je){}
                        }
                        @Override
                        public void onError(ANError anError) {
                            Log.i("error",anError.toString());
                            //descripcion.setText(anError.toString()); tiene transacciones pendientes.....
                            descripcion.setTag("tp");
                            dialogo_transacciones_pendientes();
                        }
                    });
            no_pago = true;
        }else
            abrir_payphone();
    }

    private String tiempo_actual() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date).toString();
    }

    private String fecha_actual() {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        Integer dia = Integer.valueOf(formattedDate.split("-")[0]) + 1;
        String mes = formattedDate.split("-")[1];
        String anio = formattedDate.split("-")[2];
        return dia+"/"+mes+"/"+anio;
    }

    private String id_tran;
    @Override
    public void onResume(){
        super.onResume();
        if(fue_a_procesar){
            //String id_tran = descripcion.getText().toString();
            id_tran = descripcion.getTag().toString();
            //Toast.makeText(getContext(), id_tran,Toast.LENGTH_LONG).show();
            realizo_el_pago(id_tran);
        }
    }

    private void realizo_el_pago(String id_transaccion) {
        AndroidNetworking.get("https://pay.payphonetodoesposible.com/api/Sale?id="+id_transaccion+"")
                .addHeaders("Authorization","Bearer "+token)
                .setPriority(Priority.LOW)
                .setTag("test")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try { //Approved -- !response.getString("transactionStatus").isEmpty() --response.getString("transactionStatus").equals("Approved")
                            if(response.getString("transactionStatus").equals("Approved")){
                                //MOSTRAR VISTO DE APROBACION
                                btn_registrar_oferta.setText(R.string.btn_publicar_con_suscripcion);
                                txt_estado_pago.setText(R.string.text_estado_pago_exitoso);
                                chk_suscribirse.setClickable(false);

                            }else{
                                //MOSTRAR VISTO DE NO PAGO..
                                txt_estado_pago.setText(R.string.text_estado_pago_fallido);
                                dialogo();
                            }
                            //Toast.makeText(getContext(), response.getString("transactionStatus"),Toast.LENGTH_LONG).show();
                        }catch (JSONException je){}
                    }

                    @Override
                    public void onError(ANError anError) {
                        txt_estado_pago.setText(R.string.text_estado_pago_fallido);
                        dialogo();
                    }
                });
    }

    private void cambio_metodo_creacion() {
        if(((CheckBox)chk_suscribirse).isChecked()) {
            se_suscribe = true;
            btn_registrar_oferta.setText(R.string.btn_registrar_usuario_con_suscripcion);
        }
        else{
            se_suscribe = false;
            btn_registrar_oferta.setText(R.string.btn_registrar_usuario);
            txt_estado_pago.setText("");
            //Cancelar pago...
        }
    }

    private void abrir_payphone() {
        Intent launchIntent = null;
        try{
            launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("payPhone_Android.PayPhone_Android");
        } catch (Exception ignored) {}

        if(launchIntent == null){
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "payPhone_Android.PayPhone_Android")));
        } else {
            startActivity(launchIntent);
        }
    }
    private void guardar_oferta() {
        if(campos_validos()){
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
            datos.put("fecha", fecha_actual());
            caso_servicio = "guardar_oferta";
            WebService ws = new WebService("https://gallinas-force.000webhostapp.com/ofertainsertarfinal.php",
                    datos, this.getContext(), this);
            ws.execute("POST");
        }else{
            Toast.makeText(getContext(), "Campos incompletos", Toast.LENGTH_LONG).show();
        }
    }

    private boolean campos_validos() {
        if(!peso_minimo.getText().toString().isEmpty()&&
                !peso_maximo.getText().toString().isEmpty()&&
                !descripcion.getText().toString().isEmpty()&&
                !costo_xmayor.getText().toString().isEmpty()&&
                !costo_xmenor.getText().toString().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void dialogo(){
        new AlertDialog.Builder(getContext())
                .setTitle("El pago no se realizó")
                .setIcon(R.drawable.ic_error)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Mensaje","Se canceló la acción");
                    }
                })*/
                .show();
    }


    public void dialogo_transacciones_pendientes(){
        new AlertDialog.Builder(getContext())
                .setTitle("Pagos pendientes a Turuleka SA")
                .setIcon(R.drawable.ic_error)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        no_pago = false;
                        dialog.cancel();
                    }
                })
                /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })*/
                .show();
    }
    public void dialogo_registro_payphone(){
        new AlertDialog.Builder(getContext())
                .setTitle("Necesita descargar PayPhone")
                .setIcon(R.drawable.ic_error)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        abrir_payphone();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btn_registrar_oferta.setText(R.string.btn_registrar_usuario);
                        chk_suscribirse.setChecked(false);
                        txt_estado_pago.setText("");
                        se_suscribe = false;
                    }
                })
                .show();
    }

    @Override
    public void processFinish(String result) throws JSONException {

        JSONObject datos = new JSONObject(result);
        String id_oferta = datos.getString("idoferta");
        //Toast.makeText(this.getContext(), id_oferta, Toast.LENGTH_LONG).show();

        //guardar suscripcion
        if(caso_servicio.equals("guardar_oferta")){
            if(txt_estado_pago.getText().toString().trim().equals("¡Pago existoso!")){
                guardar_suscripcion(id_oferta);

                //Toast.makeText(this.getContext(), "Listo guar. suscrip", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this.getContext(), "Publicado", Toast.LENGTH_LONG).show();
            }
        }
        if(caso_servicio.equals("guardar_suscripcion")){
            Toast.makeText(this.getContext(), "Publicado", Toast.LENGTH_LONG).show();
        }

        Fragment fragment = new IngresarProducto();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();

    }



    private void guardar_suscripcion(String id_oferta) {
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("idoferta", id_oferta);
        datos.put("tiempo", "30 días");
        datos.put("Costo", "$"+monto);
        datos.put("estado", "activa");
        datos.put("fechaInicio", fecha_actual());
        datos.put("transactionId", id_tran);
        caso_servicio = "guardar_suscripcion";
        WebService ws = new WebService("https://gallinas-force.000webhostapp.com/InsertarSuscripcion.php",
                datos, this.getContext(), this);
        ws.execute("POST");
    }
}