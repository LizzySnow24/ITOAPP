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
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements noticiasAdapter.OnDeleteClickListener {
    // Instancia de FirebaseFirestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List <Datos_Publicacion> lista_datos;
    private RecyclerView mRecyclerView;
    private noticiasAdapter mAdapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();

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
                abrir_crearPubli(); // Método para abrir la nueva actividad
            }
        });

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
                                        data.setID(document.getId());
                                        data.setSemestre(document.getString("semestre"));
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
                    mAdapter.setOnClickListener(HomeFragment.this);
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

    public void onDeleteClick(String publicacionId,String semestre){
        DocumentReference semestreDocRef = db.collection("Semestre").document(semestre);

        // Referencia de la colección "Semestre" en la base de datos Firestore
        CollectionReference semestreRef =semestreDocRef.collection("publicaciones");

        // Obtener el URL de la imagen antes de eliminar el documento
        DocumentReference publicacionDocRef = semestreRef.document(publicacionId);
        publicacionDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    // Obtener la lista de URLs de las imágenes del documento
                    List<String> urlsImagenes = (List<String>) documentSnapshot.get("imagenes");

                    if (urlsImagenes != null && !urlsImagenes.isEmpty()) {
                        // Eliminar el documento de Firestore
                        publicacionDocRef.delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // El documento se eliminó exitosamente de Firestore
                                        Log.d(TAG, "Documento eliminado correctamente");

                                        // Ahora, eliminar las imágenes del almacenamiento (Storage)
                                        for (String urlImagen : urlsImagenes) {
                                            eliminarImagenDeStorage(urlImagen);
                                        }
                                        // Mostrar mensaje de publicación eliminada correctamente
                                        Toast.makeText(getContext(), "Publicación eliminada correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Ocurrió un error al intentar eliminar el documento de Firestore
                                        Log.w(TAG, "Error al eliminar documento de Firestore", e);

                                        // Manejar el error de eliminación del documento de Firestore
                                    }
                                });
                    } else {
                        Log.d(TAG, "La lista de URLs de las imágenes es nula o vacía");
                    }
                } else {
                    Log.d(TAG, "El documento no existe");
                }
            }
        });
    }
    public void eliminarImagenDeStorage(String url_imagen){
        // Obtener una referencia al archivo de Storage que deseas eliminar
        StorageReference imagenRef = storage.getReferenceFromUrl(url_imagen);

        // Eliminar el archivo de Storage
        imagenRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // El archivo de Storage se eliminó exitosamente
                        Log.d(TAG, "Imagen eliminada correctamente de Storage");

                        // Aquí puedes realizar cualquier otra acción necesaria después de eliminar la imagen
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Ocurrió un error al intentar eliminar la imagen de Storage
                        Log.w(TAG, "Error al eliminar imagen de Storage", e);

                        // Manejar el error de eliminación de la imagen de Storage
                    }
                });
    }
}