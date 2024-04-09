package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    //el numero es la casilla que seleccione, en total tenemos 3, podemos elegir entre las 3
    private int seleccion = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final LinearLayout HomeLayout = findViewById(R.id.homeLayout);
        final LinearLayout OptionsLayout = findViewById(R.id.OptionsLayout);
        final LinearLayout ProfilLayout = findViewById(R.id.ProfilLayout);

        final ImageView HomeImage = findViewById(R.id.HomeImage);
        final ImageView OptionsImage = findViewById(R.id.OptionsImage);
        final ImageView ProfilImage = findViewById(R.id.ProfilImage);

        final TextView HomeText = findViewById(R.id.HomeText);
        final TextView OptionsText = findViewById(R.id.OptionsText);
        final TextView ProfilText = findViewById(R.id.ProfilText);

        HomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        OptionsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ProfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}