package com.example.itoapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeFragment extends Fragment {
    // Instancia de FirebaseFirestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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

        //String semestre=Menu.getSemestre();
        //String documento= Menu.getDocumento();
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
        //Toast.makeText(getActivity(), "semestre: " + semestre, Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "documento: " + documento, Toast.LENGTH_LONG).show();


        return view;
    }
    // Método para abrir la nueva actividad
    private void abrir_crearPubli() {
        Intent intent = new Intent(getActivity(), activity_crearPubli.class);
        startActivity(intent);
    }
    private void GuardarCampos(){
        String semestre=Menu.getSemestre();
        String documento= Menu.getDocumento();
        //Referencia de la base de datos donde tengo almacenados las imagenes y texto
        DocumentReference documentoRef = db.collection("Semestre")
                .document(semestre)
                .collection("publicaciones")
                .document(documento);

        // Acceder a los datos del documento
        documentoRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {

                    // El documento existe, puedes acceder a sus datos
                    String texto = documentSnapshot.getString("texto");

                    List<String> imagenes = (List<String>) documentSnapshot.get("imagenes");

                } else {
                    Log.d(TAG, "El documento no existe");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error al obtener el documento", e);
            }
        });
    }
}