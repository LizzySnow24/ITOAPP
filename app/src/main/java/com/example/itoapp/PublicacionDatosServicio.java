package com.example.itoapp;

public class PublicacionDatosServicio {
    private String nombre;
    private String departamento;
    private String correo;
    private String telefono;
    private String requisitos;
    private String url_imagen;

    public PublicacionDatosServicio() {
        this.nombre = nombre;
        this.departamento = departamento;
        this.correo = correo;
        this.telefono = telefono;
        this.requisitos = requisitos;
        this.url_imagen = url_imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
