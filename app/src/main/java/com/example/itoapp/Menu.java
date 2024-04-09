package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
                //verificamos que el selector esta en home
                if(seleccion == 1){
                    //las otras pestañas esperan a ser seleccionadas
                    OptionsText.setVisibility(View.GONE);
                    ProfilText.setVisibility(View.GONE);

                    OptionsImage.setImageResource(R.drawable.ave);
                    ProfilImage.setImageResource(R.drawable.ave);

                    OptionsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    ProfilLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //seleccionamos inicio
                    HomeText.setVisibility(View.VISIBLE);
                    HomeImage.setImageResource(R.drawable.casa);
                    HomeLayout.setBackgroundResource(R.drawable.round_home_100);

                    //creamos animación
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    HomeLayout.startAnimation(scaleAnimation);
                    //establecemos que la primera pestaña de seleccion es home
                    seleccion = 1;
                }
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