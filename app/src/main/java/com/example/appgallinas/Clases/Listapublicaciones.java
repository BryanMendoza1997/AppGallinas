package com.example.appgallinas.Clases;

public class Listapublicaciones {
    private String precio;
    private String descripción;
    private String estado;
    private String fotogallina;
    private String titulo;
    private  String peso;
    private int idoferta;
    private  String ciudad;
    private  String correo;
    private  String direccion;
    private  String telefono;
    private  String nombre;

    public Listapublicaciones(String precio, String descripción, String estado, String fotogallina, String titulo, String peso, int idoferta, String ciudad, String correo, String direccion, String telefono, String nombre) {
        this.precio = precio;
        this.descripción = descripción;
        this.estado = estado;
        this.fotogallina = fotogallina;
        this.titulo = titulo;
        this.peso = peso;
        this.idoferta = idoferta;
        this.ciudad = ciudad;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        estado = estado;
    }

    public String getFotogallina() {
        return fotogallina;
    }

    public void setFotogallina(String fotogallina) {
        this.fotogallina = fotogallina;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public int getIdoferta() {
        return idoferta;
    }

    public void setIdoferta(int idoferta) {
        this.idoferta = idoferta;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
