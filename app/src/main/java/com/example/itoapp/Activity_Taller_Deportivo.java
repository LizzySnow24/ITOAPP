package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Activity_Taller_Deportivo extends AppCompatActivity {

    private static String rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taller_deportivo);

        FloatingActionButton boton_editar4 = findViewById(R.id.boton_editar4);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar4.setVisibility(View.VISIBLE);
            //ABRIR VENTANA EDITAR
        } else {
            boton_editar4.setVisibility(View.GONE);
        }
    }
}