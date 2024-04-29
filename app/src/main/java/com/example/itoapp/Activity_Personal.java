package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity_Personal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        llamar_taller();
    }
    public void llamar_taller(){

        CardView card_direccion= findViewById(R.id.card_direccion);
        CardView card_administrativo = findViewById(R.id.card_administrativos);
        CardView card_academicos = findViewById(R.id.card_academicos);
        CardView card_planeacion = findViewById(R.id.card_planeacion);

        setOnClickListener(card_direccion, Activity_Direccion.class);
        setOnClickListener(card_administrativo, Activity_administrativos.class);
        setOnClickListener(card_academicos, Activity_Academicos.class);
        setOnClickListener(card_planeacion, Activity_Planeacion.class);
    }
    private void setOnClickListener(CardView cardView, final Class<?> activityClass) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Personal.this, activityClass);
                startActivity(i);
            }
        });
    }
}