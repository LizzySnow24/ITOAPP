package com.example.itoapp;

import java.util.List;

public class Datos_Publicacion {
    private String texto;
    private long fecha;
    private List<String> imageId;

    public Datos_Publicacion(String texto, long fecha, List<String> imageId) {
        this.texto = texto;
        this.fecha = fecha;
        this.imageId = imageId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public List<String> getImageId() {
        return imageId;
    }

    public void setImageId(List<String> imageId) {
        this.imageId = imageId;
    }
}
