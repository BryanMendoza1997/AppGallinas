package com.example.appgallinas.Clases;

import com.example.appgallinas.Vendedor;
import com.example.appgallinas.WebServices.Asynchtask;
import com.example.appgallinas.WebServices.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Producto {
    private Double Precio;
    private String descripción;
    private String Estado;
    private String nombre;
    private int fotogallina;
    private String titulo;
    private int idProducto;
    private  String peso;
    private  String raza;
    private  String foto;
    private int idoferta;
    public int getIdoferta() {
        return idoferta;
    }

    public void setIdoferta(int idoferta) {
        this.idoferta = idoferta;
    }



    private ArrayList<Producto> products;
    public Producto(String nombre, int idProducto, String raza, String foto) {
        this.nombre = nombre;
        this.idProducto = idProducto;
        this.raza = raza;
        this.foto = foto;
    }

    public Producto(String descripción, String nombre, int idProducto, String raza, String foto) {
        this.descripción = descripción;
        this.nombre = nombre;
        this.idProducto = idProducto;
        this.raza = raza;
        this.foto = foto;
    }

    public Producto() {

    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }




    public Producto(int idoferta,Double precio, String descripción, String estado, int fotogallina, String titulo,String peso) {
        this.Precio = precio;
        this.descripción = descripción;
        this.Estado = estado;
        this.fotogallina = fotogallina;
        this.titulo = titulo;
        this.peso=peso;
        this.idoferta=idoferta;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public int getFotogallina() {
        return fotogallina;
    }

    public void setFotogallina(int fotogallina) {
        this.fotogallina = fotogallina;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


}
