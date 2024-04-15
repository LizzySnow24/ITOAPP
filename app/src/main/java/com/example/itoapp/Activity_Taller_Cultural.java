package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Activity_Taller_Cultural extends AppCompatActivity {

    private static String rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taller_cultural);

        FloatingActionButton boton_editar3 = findViewById(R.id.boton_editar3);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar3.setVisibility(View.VISIBLE);
            //ABRIR VENTANA EDITAR
        } else {
            boton_editar3.setVisibility(View.GONE);
        }
    }
}