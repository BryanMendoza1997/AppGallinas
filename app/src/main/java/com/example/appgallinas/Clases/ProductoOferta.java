package com.example.appgallinas.Clases;

public class ProductoOferta {
    private Integer id_producto;
    private String descripcion, nombre, raza, url_foto;

    public ProductoOferta() {
    }

    public ProductoOferta( String descripcion, String nombre,Integer id_producto, String raza, String url_foto) {
        this.id_producto = id_producto;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.raza = raza;
        this.url_foto = url_foto;
    }

    public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}
