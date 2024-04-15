package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Activity_PlanEstudios extends AppCompatActivity {

    private static String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_estudios);

        FloatingActionButton boton_editar = findViewById(R.id.boton_editar);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar.setVisibility(View.VISIBLE);
            //ABRIR VENTANA EDITAR
        } else {
            boton_editar.setVisibility(View.GONE);
        }

        Spinner lista_Carreras = (Spinner) findViewById(R.id.lista_carreras);
        String[] datosCarreras = new String[]{"Seleccionar Carrera","Sistemas Computacionales", "Electromecánica",
                "Industrial", "Contador Público", "Gestión Empresarial", "Logística"};
        ArrayAdapter adapterCarreras = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, datosCarreras);
        adapterCarreras.setDropDownViewResource(R.layout.spinner_item);
        lista_Carreras.setAdapter(adapterCarreras);
    }
}