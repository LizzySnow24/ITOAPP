package com.example.itoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class Fragment_Perfil_Admin extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton editButton;
    private ImageView perfil ;
    public Fragment_Perfil_Admin() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__perfil__admin, container, false);

        editButton = view.findViewById(R.id.edit_button);
        perfil = view.findViewById(R.id.FotoPerfil);

        TextView textViewCerrarSesion = view.findViewById(R.id.textViewCerrarSesion);
        Switch switchNotificaciones = view.findViewById(R.id.SwitchNotificaciones);

        textViewCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Regresar a la pantalla principal de tu proyecto
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        // Configurar OnCheckedChangeListener para el Switch de notificaciones
        switchNotificaciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Implementar la lógica para habilitar/deshabilitar las notificaciones según el estado del Switch
                if (isChecked) {
                    // Habilitar las notificaciones
                } else {
                    // Deshabilitar las notificaciones
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            // Aquí puedes obtener la Uri de la imagen seleccionada y manejarla según tus necesidades
            Uri uri = data.getData();
            // Por ejemplo, puedes cargar la imagen en tu ImageView
            perfil.setImageURI(uri);
        }
    }
}