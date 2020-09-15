package com.example.appgallinas.Clases;

public class MejorVendedor {
    int foto;
    String nombre;
    String ciudad;
    String correo;
    float calificacion;

    public MejorVendedor(int foto, String nombre, String ciudad, String correo, float calificacion) {
        this.foto = foto;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.correo = correo;
        this.calificacion = calificacion;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

}
