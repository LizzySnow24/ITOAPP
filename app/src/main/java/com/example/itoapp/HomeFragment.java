package com.example.itoapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    // Instancia de FirebaseFirestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String semestre;
    String documento;
    List <Datos_Publicacion> lista_datos;

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
        lista_datos = new ArrayList<>();
        semestre = Menu.getSemestre();
        documento = Menu.getDocumento();
        //Referencia de la base de datos donde tengo almacenados las imagenes y texto
        DocumentReference documentoRef = db.collection("Semestre")
                .document(semestre)
                .collection("publicaciones")
                .document(documento);

        documentoRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.e(TAG, "Error al obtener datos del documento", error);
                    return;
                }

                if (value != null && value.exists()) {
                    Datos_Publicacion data = value.toObject(Datos_Publicacion.class);
                    lista_datos.add(data);
                } else {
                    Log.d(TAG, "El documento no existe o no tiene datos");
                }
                //aqui va el adapter
            }
        });

        //este es mi boton flotante
        FloatingActionButton boton_publicar = view.findViewById(R.id.boton_publicar);

        // Obtener el rol desde la actividad Menu
        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_publicar.setVisibility(View.VISIBLE);
            boton_publicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    abrir_crearPubli(); // Método para abrir la nueva actividad
                }
            });
        } else {
            boton_publicar.setVisibility(View.GONE);
        }


        return view;
    }

    // Método para abrir la nueva actividad
    private void abrir_crearPubli() {
        Intent intent = new Intent(getActivity(), activity_crearPubli.class);
        startActivity(intent);
    }
}