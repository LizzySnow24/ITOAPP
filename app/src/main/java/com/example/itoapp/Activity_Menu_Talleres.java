package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity_Menu_Talleres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_talleres);
        llamar_taller();
    }

    public void llamar_taller(){

        CardView card_Civicos = findViewById(R.id.card_Civicos);
        CardView card_deportivo = findViewById(R.id.card_deportivo);
        CardView card_culturales = findViewById(R.id.card_culturales);

        card_Civicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_Menu_Talleres.this, Activity_Taller_Civico.class);
                startActivity(i);
            }
        });
    }
}