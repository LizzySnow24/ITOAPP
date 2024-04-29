package com.example.itoapp;

import android.widget.ImageView;

public class DatosPublicacionTalleres {
    private String titulo;
    private String telefono;
    private String instructor;
    private String horario;
    private String lugar;
    private String url_imagen;

    public DatosPublicacionTalleres() {
        this.titulo = titulo;
        this.telefono = telefono;
        this.instructor = instructor;
        this.horario = horario;
        this.lugar = lugar;
        this.url_imagen = url_imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
