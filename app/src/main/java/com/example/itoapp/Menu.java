package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    //el numero es la casilla que seleccione, en total tenemos 3, podemos elegir entre las 3
    private int seleccion = 1;
    //la variable que almacena el rol de usuario que ingreso en ese momento
    private static String rol;
    private static String num_control;

    public static String getRol() {
        return rol;
    }
    public static String getNum_control() {
        return num_control;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Obtener el rol de la clase MainActivity (login)
        rol = getIntent().getStringExtra("rol");
        num_control = getIntent().getStringExtra("num_control");

        //Toast.makeText(Menu.this, "Este es el Rol en menu: "+rol, Toast.LENGTH_SHORT).show();

        final LinearLayout HomeLayout = findViewById(R.id.homeLayout);
        final LinearLayout OptionsLayout = findViewById(R.id.OptionsLayout);
        final LinearLayout ProfilLayout = findViewById(R.id.ProfilLayout);

        final ImageView HomeImage = findViewById(R.id.HomeImage);
        final ImageView OptionsImage = findViewById(R.id.OptionsImage);
        final ImageView ProfilImage = findViewById(R.id.ProfilImage);

        final TextView HomeText = findViewById(R.id.HomeText);
        final TextView OptionsText = findViewById(R.id.OptionsText);
        final TextView ProfilText = findViewById(R.id.ProfilText);

        //agregamos la pantalla de home para que aparezca por default
        if(!rol.equals("admin")) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, HomeFragment_User.class, null)
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, HomeFragment.class, null)
                    .commit();
        }
        HomeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificamos que el selector esta en home
                if(seleccion != 1){

                    if(!rol.equals("admin")) {
                        //agregamos la pantalla de home usuario
                        Toast.makeText(Menu.this, "Rol es esto: " + rol, Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, HomeFragment_User.class, null)
                                .commit();
                    }else{
                        Toast.makeText(Menu.this, "Rol es esto admin: " + rol, Toast.LENGTH_SHORT).show();
                        //agregamos la pantalla de home admin
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, HomeFragment.class, null)
                                .commit();
                    }

                    //las otras pestañas esperan a ser seleccionadas
                    OptionsText.setVisibility(View.GONE);
                    ProfilText.setVisibility(View.GONE);

                    OptionsImage.setImageResource(R.drawable.ave_sombreado);
                    ProfilImage.setImageResource(R.drawable.ave_sombreado);

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
                if(seleccion != 2){

                    //agregamos la pantalla de home
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, Fragment_Optiones.class,null)
                            .commit();

                    //las otras pestañas esperan a ser seleccionadas
                    HomeText.setVisibility(View.GONE);
                    ProfilText.setVisibility(View.GONE);

                    HomeImage.setImageResource(R.drawable.home_sombreado);
                    ProfilImage.setImageResource(R.drawable.ave_sombreado);

                    HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    ProfilLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //seleccionamos opciones
                    OptionsText.setVisibility(View.VISIBLE);
                    OptionsImage.setImageResource(R.drawable.ave);
                    OptionsLayout.setBackgroundResource(R.drawable.round_options_100);

                    //creamos animación
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    OptionsLayout.startAnimation(scaleAnimation);
                    //establecemos que la segunda pestaña de seleccion es opciones
                    seleccion = 2;
                }
            }
        });
        ProfilLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seleccion != 3){

                    if(!rol.equals("admin")) {
                        //Toast.makeText(Menu.this, "Rol es: " + rol, Toast.LENGTH_SHORT).show();
                        //agregamos la pantalla de opciones
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, Fragment_Perfil.class, null)
                                .commit();
                    }else{
                        //agregamos la pantalla de opciones
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.fragmentContainer, Fragment_Perfil_Admin.class, null)
                                .commit();
                    }

                    //las otras pestañas esperan a ser seleccionadas
                    OptionsText.setVisibility(View.GONE);
                    HomeText.setVisibility(View.GONE);

                    OptionsImage.setImageResource(R.drawable.ave_sombreado);
                    HomeImage.setImageResource(R.drawable.home_sombreado);

                    OptionsLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    HomeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    //seleccionamos perfil
                    ProfilText.setVisibility(View.VISIBLE);
                    ProfilImage.setImageResource(R.drawable.ave);
                    ProfilLayout.setBackgroundResource(R.drawable.round_profil_100);

                    //creamos animación
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f,1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    ProfilLayout.startAnimation(scaleAnimation);
                    //establecemos que la tercera pestaña de seleccion es perfil
                    seleccion = 3;
                }
            }
        });
    }
}