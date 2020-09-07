package com.example.appgallinas.Clases;

public class Oferta {
    private String raza, tipo, fecha, costo_xmenor, costo_xmayor, peso_minimo, peso_maximo, descripcion;
    private String id_oferta, id_producto, url_foto, valoracion;

    public Oferta() {
    }

    public String getId_oferta() {
        return id_oferta;
    }

    public void setId_oferta(String id_oferta) {
        this.id_oferta = id_oferta;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getCosto_xmenor() {
        return costo_xmenor;
    }

    public void setCosto_xmenor(String costo_xmenor) {
        this.costo_xmenor = costo_xmenor;
    }

    public String getCosto_xmayor() {
        return costo_xmayor;
    }

    public void setCosto_xmayor(String costo_xmayor) {
        this.costo_xmayor = costo_xmayor;
    }

    public String getPeso_minimo() {
        return peso_minimo;
    }

    public void setPeso_minimo(String peso_minimo) {
        this.peso_minimo = peso_minimo;
    }

    public String getPeso_maximo() {
        return peso_maximo;
    }

    public void setPeso_maximo(String peso_maximo) {
        this.peso_maximo = peso_maximo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
