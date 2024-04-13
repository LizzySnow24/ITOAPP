package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Activity_PlanEstudios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_estudios);

        Spinner lista_Carreras = (Spinner) findViewById(R.id.lista_carreras);
        String[] datosCarreras = new String[]{"Seleccionar Carrera","Sistemas Computacionales", "Electromecánica",
                "Industrial", "Contador Público", "Gestión Empresarial", "Logística"};
        ArrayAdapter adapterCarreras = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, datosCarreras);
        adapterCarreras.setDropDownViewResource(R.layout.spinner_item);
        lista_Carreras.setAdapter(adapterCarreras);
    }
}