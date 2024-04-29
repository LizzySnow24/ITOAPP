package com.example.itoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Activity_Academicos extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AlertDialog alertDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    final Map<String, Object> textos_academicos = new HashMap<>();
    private Uri selectedImageUri;
    private List<PublicacionDatosPersonal> lista_datos_academicos;
    private PublicacionPersonalAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String newNombre;
    private String newCargo;
    private String newCorreo;
    private String newTelefono;
    private String newExtension;
    private String newUbicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academicos);

        FloatingActionButton botonCrearAcademico = findViewById(R.id.botonCrearAcademico);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            botonCrearAcademico.setVisibility(View.VISIBLE);
            botonCrearAcademico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HacerPublicacion();
                }
            });
        } else {
            botonCrearAcademico.setVisibility(View.GONE);
        }

        lista_datos_academicos = new ArrayList<>();

        //TRAER TODOS LOS DATOS
        db.collection("Personal_Academico")
                .document("academico")
                .collection("publicaciones")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        PublicacionDatosPersonal publicacion = document.toObject(PublicacionDatosPersonal.class);
                        lista_datos_academicos.add(publicacion);
                        //Log.d("DATO CIVICO",publicacion.getHorario());
                    }
                    mAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al obtener publicaciones", e);
                });
        // Configurar RecyclerView y su adaptador
        mRecyclerView =findViewById(R.id.recycleAcademico);
        mAdapter = new PublicacionPersonalAdapter(Activity_Academicos.this, lista_datos_academicos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Activity_Academicos.this));
        mRecyclerView.setAdapter(mAdapter);
    }
    public void HacerPublicacion(){
        // Infla el diseño personalizado para el cuadro de diálogo
        View dialogView = getLayoutInflater().inflate(R.layout.pantalla_crear_personal, null);

        // Crea el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Academicos.this);
        builder.setView(dialogView);

        // ESTOS SON MIS TEXTOS DE MI CUADRO PARA INGRESAR LOS DATOS
        EditText editNombre = dialogView.findViewById(R.id.editNombre);
        EditText editCargo = dialogView.findViewById(R.id.editCargo);
        EditText editCorreo = dialogView.findViewById(R.id.editCorreo);
        EditText editTelefono = dialogView.findViewById(R.id.editTelefono);
        EditText editExtension = dialogView.findViewById(R.id.editExtension);
        EditText editUbicacion = dialogView.findViewById(R.id.editUbicacion);

        Button mostrarCargar = dialogView.findViewById(R.id.mostrarCargar);

        //desplegar el cambio de imagen
        mostrarCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout contentLayout = dialogView.findViewById(R.id.contentLayoutPersonal);
                contentLayout.setVisibility(View.VISIBLE);
            }
        });

        //boton cambiar
        Button cargar = dialogView.findViewById(R.id.CargarImagen);
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para seleccionar una imagen de la galería
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        // Configura los botones del cuadro de diálogo
        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            // Obtén el texto ingresado en los EditText
            newNombre = editNombre.getText().toString();
            newCargo = editCargo.getText().toString();
            newCorreo = editCorreo.getText().toString();
            newTelefono = editTelefono.getText().toString();
            newExtension = editExtension.getText().toString();
            newUbicacion = editUbicacion.getText().toString();

            if (!newNombre.isEmpty()) {
                textos_academicos.put("nombre", newNombre);
            }
            if (!newCargo.isEmpty()) {
                textos_academicos.put("cargo", newCargo);
            }
            if (!newCorreo.isEmpty()) {
                textos_academicos.put("correo", newCorreo);
            }
            if (!newTelefono.isEmpty()) {
                textos_academicos.put("telefono", newTelefono);
            }
            if (!newExtension.isEmpty()) {
                textos_academicos.put("extension", newExtension);
            }
            if (!newUbicacion.isEmpty()) {
                textos_academicos.put("ubicacion", newUbicacion);
            }

        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.cancel();
        });
        // Crea y muestra el cuadro de diálogo
        alertDialog = builder.create();
        alertDialog.show();

        // Personaliza los botones después de mostrar el cuadro de diálogo
        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        // Personaliza el estilo de los botones
        // Por ejemplo, cambia el color del texto a rojo
        positiveButton.setTextColor(Color.RED);
        negativeButton.setTextColor(Color.RED);

        // También puedes cambiar el tamaño del texto, tipo de letra, etc.
        positiveButton.setTextSize(18); // Cambia el tamaño del texto
        negativeButton.setTextSize(18); // Cambia el tamaño del texto
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtener la URI de la imagen seleccionada
            selectedImageUri = data.getData();

            // Obtén una referencia a la ubicación donde se guardará la imagen en Storage
            StorageReference imagenRef = storageRef.child("imagenes_personal/imagenes_academico" + UUID.randomUUID().toString());

            // Sube la imagen seleccionada a Firebase Storage
            imagenRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Obtiene la URL de la imagen recién subida
                        imagenRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            // Notificar al adaptador sobre el cambio en los datos
                            textos_academicos.put("url_imagen", imageUrl);
                            mAdapter.notifyDataSetChanged();//esto

                            //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                            DocumentReference documentoRef = db.collection("Personal_Academico")
                                    .document("academico");
                            // Cargamos los datos
                            CollectionReference publicacionesRef = documentoRef.collection("publicaciones");

                            // Después de agregar la publicación a la base de datos con éxito
                            publicacionesRef.add(textos_academicos)
                                    .addOnSuccessListener(documentReference -> {
                                        // Crear un objeto DatosPublicacionTalleres con los datos ingresados
                                        PublicacionDatosPersonal nuevaPublicacion = new PublicacionDatosPersonal();
                                        nuevaPublicacion.setNombre(newNombre);
                                        nuevaPublicacion.setCargo(newCargo);
                                        nuevaPublicacion.setCorreo(newCorreo);
                                        nuevaPublicacion.setTelefono(newTelefono);
                                        nuevaPublicacion.setExtension(newExtension);
                                        nuevaPublicacion.setUbicacion(newUbicacion);

                                        // Agregar la nueva publicación a la lista
                                        lista_datos_academicos.add(nuevaPublicacion);

                                        // Notificar al adaptador sobre el cambio en los datos
                                        mAdapter.notifyDataSetChanged();

                                        // Mostrar un mensaje de éxito
                                        Toast.makeText(Activity_Academicos.this, "Publicación agregada correctamente", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Mostrar un mensaje de error en caso de falla
                                        Toast.makeText(Activity_Academicos.this, "Error al agregar la publicación", Toast.LENGTH_SHORT).show();
                                        //Log.e("Firestore", "Error al agregar la publicación", e);
                                    });
                        });
                    });
        }
    }
}