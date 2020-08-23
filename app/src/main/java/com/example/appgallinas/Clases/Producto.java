package com.example.appgallinas.Clases;

public class Producto {
    private Double Precio;
    private String descripción;
    private String Estado;
    private int fotogallina;
    private String titulo;
    private int fotoprovedor;
    private  String peso;

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }



    public Producto(Double precio, String descripción, String estado, int fotogallina, String titulo, int fotoprovedor,String peso) {
        Precio = precio;
        this.descripción = descripción;
        Estado = estado;
        this.fotogallina = fotogallina;
        this.titulo = titulo;
        this.fotoprovedor = fotoprovedor;
        this.peso=peso;
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

    public int getFotoprovedor() {
        return fotoprovedor;
    }

    public void setFotoprovedor(int fotoprovedor) {
        this.fotoprovedor = fotoprovedor;
    }


}
