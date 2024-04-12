package com.example.itoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    String rol = "";


    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //este es mi boton flotante
        FloatingActionButton boton_publicar = view.findViewById(R.id.boton_publicar);

        // Obtener el rol desde la actividad Menu
        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_publicar.setVisibility(View.VISIBLE);
            // Agregar el listener al botón flotante si es necesario
            boton_publicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    abrir_crearPubli(); // Método para abrir la nueva actividad
                }
            });
        } else {
            boton_publicar.setVisibility(View.GONE);
        }

        Toast.makeText(getActivity(), "rol: " + rol, Toast.LENGTH_LONG).show();

        return view;
    }
    // Método para abrir la nueva actividad
    private void abrir_crearPubli() {
        Intent intent = new Intent(getActivity(), activity_crearPubli.class);
        startActivity(intent);
    }
}