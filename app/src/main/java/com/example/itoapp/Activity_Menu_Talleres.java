package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Activity_Menu_Talleres extends AppCompatActivity {
    private static String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_talleres);

        FloatingActionButton boton_editar1 = findViewById(R.id.boton_editar1);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar1.setVisibility(View.VISIBLE);
            //ABRIR VENTANA EDITAR
        } else {
            boton_editar1.setVisibility(View.GONE);
        }

        llamar_taller();
    }

    public void llamar_taller(){

        CardView card_Civicos = findViewById(R.id.card_Civicos);
        CardView card_deportivo = findViewById(R.id.card_deportivo);
        CardView card_culturales = findViewById(R.id.card_culturales);

        setOnClickListener(card_Civicos, Activity_Taller_Civico.class);
        setOnClickListener(card_deportivo, Activity_Taller_Deportivo.class);
        setOnClickListener(card_culturales, Activity_Taller_Cultural.class);
    }
    private void setOnClickListener(CardView cardView, final Class<?> activityClass) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Menu_Talleres.this, activityClass);
                startActivity(i);
            }
        });
    }
}