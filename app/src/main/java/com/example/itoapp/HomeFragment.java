package com.example.itoapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
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
    List <Datos_Publicacion> lista_datos;
    private RecyclerView mRecyclerView;
    private noticiasAdapter mAdapter;
    private String rol;
    private String roldef;

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
        boton_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "Valor de rol: y si jalo el onclic " + rol);
                abrir_crearPubli(); // Método para abrir la nueva actividad
            }
        });

        // Obtener el rol desde la actividad Menu
        if (getArguments() != null) {
            rol = getArguments().getString("rol");

            // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
            if (rol != null && rol.equals("admin")) {
                Toast.makeText(getActivity(), "Rol es: " + rol, Toast.LENGTH_SHORT).show();
                boton_publicar.setVisibility(View.VISIBLE);

            } else{
                boton_publicar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "Este es rol: " + rol, Toast.LENGTH_SHORT).show();
            }
        }



        lista_datos = new ArrayList<>();

        // Referencia de la colección "Semestre" en la base de datos Firestore
        CollectionReference semestreRef = db.collection("Semestre");
        semestreRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Iterar sobre los documentos de la colección "Semestre"
                    for (QueryDocumentSnapshot semestreDoc : task.getResult()) {
                        // Obtener la referencia de la subcolección "publicaciones" dentro del documento "Semestre"
                        CollectionReference publicacionesRef = semestreDoc.getReference().collection("publicaciones");

                        // Obtener todos los documentos de la subcolección "publicaciones"
                        publicacionesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Iterar sobre los documentos obtenidos
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Datos_Publicacion data = new Datos_Publicacion();
                                        data.setTexto(document.getString("texto"));
                                        data.setFecha(document.getLong("fecha"));
                                        data.setImageId((List<String>) document.get("imagenes"));
                                        lista_datos.add(data);
                                    }

                                    // Notificar al adaptador que se han agregado nuevos datos
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    Log.e(TAG, "Error al obtener documentos de la subcolección 'publicaciones'", task.getException());
                                }
                            }
                        });
                    }
                    // Configurar RecyclerView y su adaptador
                    mRecyclerView = view.findViewById(R.id.recycle_Publicacion_Noticias);
                    mAdapter = new noticiasAdapter(getActivity(), lista_datos);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.e(TAG, "Error al obtener documentos de la colección 'Semestre'", task.getException());
                }
            }
        });

        return view;
    }

    // Método para abrir la nueva actividad
    private void abrir_crearPubli() {
        Intent intent = new Intent(getActivity(), activity_crearPubli.class);
        startActivity(intent);
    }
}