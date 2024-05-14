package com.example.itoapp;

import java.util.List;

public class Datos_Publicacion {
    private String texto;
    private long fecha;
    private List<String> imageId;
    private String ID;
    private String Semestre;

    public Datos_Publicacion() {
        this.texto = texto;
        this.fecha = fecha;
        this.imageId = imageId;
        this.ID = ID;
        this.Semestre = Semestre;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSemestre() {
        return Semestre;
    }

    public void setSemestre(String semestre) {
        Semestre = semestre;
    }
}
