package com.example.itoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class Fragment_Perfil extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private ImageButton editButton;
    private ImageView perfil ;
    private String num_control;
    private TextView name;
    public Fragment_Perfil() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_fragment_perfil, container, false);

        name = view.findViewById(R.id.editName);
        editButton = view.findViewById(R.id.edit_button);
        perfil = view.findViewById(R.id.FotoPerfil);

        TextView textViewPersonalData = view.findViewById(R.id.textViewPersonalData);
        TextView textViewCerrarSesion = view.findViewById(R.id.textViewCerrarSesion);
        Switch switchNotificaciones = view.findViewById(R.id.SwitchNotificaciones);

        num_control = Menu.getNum_control();

        // Obtener una referencia al documento existente
        DocumentReference docRef = db.collection("Users").document(num_control);

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("FIRESTORE", "Error al escuchar el documento", e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Verificar si el campo "nombre" existe en el documento
                    if (documentSnapshot.contains("nombre")) {
                        // Si el campo "nombre" existe, obtener su valor
                        String nombreDB = documentSnapshot.getString("nombre");
                        // Actualizar el contenido del TextView con el nombre del usuario
                        name.setText(nombreDB);
                    } else {
                        // Si el campo "nombre" no existe, mostrar un mensaje alternativo
                        name.setText("");
                    }
                    if(documentSnapshot.contains("url_imagen")){
                        String imagenBD = documentSnapshot.getString("url_imagen");
                        Glide.with(getActivity())
                                .load(imagenBD)
                                .into(perfil);
                    }else{
                        // Si el campo "nombre" no existe, mostrar un mensaje alternativo
                        Log.d("FIRESTORE","No existe el campo imagen");
                    }
                } else {
                    Log.d("FIRESTORE", "No existe el documento");
                }
            }
        });

            textViewPersonalData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DatosPersonales.class);
                    startActivity(intent);
                }
            });


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
            Uri selectedImage = data.getData();

            //Cargamos la imagen en Storage
            if(selectedImage!=null){
                // Obtén una referencia a la ubicación donde se guardará la imagen en Storage
                StorageReference imagenRef = storageRef.child("foto_perfil/" + UUID.randomUUID().toString());

                // Sube la imagen seleccionada a Firebase Storage
                imagenRef.putFile(selectedImage)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Obtiene la URL de la imagen recién subida
                            imagenRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();

                                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                                DocumentReference documentoRef = db.collection("Users")
                                        .document(num_control);

                                documentoRef.update("url_imagen", imageUrl);

                            });
                        });
            }
        }
    }

}