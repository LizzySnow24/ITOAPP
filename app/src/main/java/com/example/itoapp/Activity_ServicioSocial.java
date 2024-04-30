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

public class Activity_ServicioSocial extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AlertDialog alertDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    final Map<String, Object> textos_servicios = new HashMap<>();
    private Uri selectedImageUri;
    private List<PublicacionDatosServicio> lista_datos_servicio;
    private PublicacionServicioAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String newNombre;
    private String newDepa;
    private String newCorreo;
    private String newTel;
    private String newRequisitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_social);

        FloatingActionButton boton_editar2 = findViewById(R.id.boton_editar2);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar2.setVisibility(View.VISIBLE);
            boton_editar2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HacerPublicacion();
                }
            });
        } else {
            boton_editar2.setVisibility(View.GONE);
        }
        lista_datos_servicio = new ArrayList<>();

        //TRAER TODOS LOS DATOS
        db.collection("Servicio")
                .document("lista")
                .collection("publicaciones")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        PublicacionDatosServicio publicacion = document.toObject(PublicacionDatosServicio.class);
                        lista_datos_servicio.add(publicacion);
                        //Log.d("DATO CIVICO",publicacion.getHorario());
                    }
                    mAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al obtener publicaciones", e);
                });
        // Configurar RecyclerView y su adaptador
        mRecyclerView = findViewById(R.id.recycle_servicioSocial);
        mAdapter = new PublicacionServicioAdapter(Activity_ServicioSocial.this, lista_datos_servicio);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Activity_ServicioSocial.this));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void HacerPublicacion() {
        // Infla el diseño personalizado para el cuadro de diálogo
        View dialogView = getLayoutInflater().inflate(R.layout.pantalla_crear_servicio, null);

        // Crea el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ServicioSocial.this);
        builder.setView(dialogView);

        // ESTOS SON MIS TEXTOS DE MI CUADRO PARA INGRESAR LOS DATOS
        EditText editNombre = dialogView.findViewById(R.id.editNombre);
        EditText editDepa = dialogView.findViewById(R.id.editdepartamento);
        EditText editTel = dialogView.findViewById(R.id.editTelefono);
        EditText editCorreo = dialogView.findViewById(R.id.editCorreo);
        EditText editRequisito = dialogView.findViewById(R.id.editRequisitos);

        Button mostrarCargar = dialogView.findViewById(R.id.mostrarCargar);

        //desplegar el cambio de imagen
        mostrarCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout contentLayout = dialogView.findViewById(R.id.contentLayoutServicio);
                contentLayout.setVisibility(View.VISIBLE);
            }
        });

        //boton cambiar
        Button cargar = dialogView.findViewById(R.id.mostrarCargar);
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
            newDepa = editDepa.getText().toString();
            newTel = editTel.getText().toString();
            newCorreo = editCorreo.getText().toString();
            newRequisitos = editRequisito.getText().toString();

            if (!newNombre.isEmpty()) {
                textos_servicios.put("nombre", newNombre);
            }
            if (!newDepa.isEmpty()) {
                textos_servicios.put("departamento", newDepa);
            }
            if (!newTel.isEmpty()) {
                textos_servicios.put("telefono", newTel);
            }
            if (!newCorreo.isEmpty()) {
                textos_servicios.put("correo", newCorreo);
            }
            if (!newRequisitos.isEmpty()) {
                textos_servicios.put("requisitos", newRequisitos);
            }

            if(selectedImageUri!=null) {
                // Obtén una referencia a la ubicación donde se guardará la imagen en Storage
                StorageReference imagenRef = storageRef.child("imagenes_servicio/" + UUID.randomUUID().toString());

                // Sube la imagen seleccionada a Firebase Storage
                imagenRef.putFile(selectedImageUri)
                        .addOnSuccessListener(taskSnapshot -> {
                            // Obtiene la URL de la imagen recién subida
                            imagenRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                String imageUrl = uri.toString();
                                // Notificar al adaptador sobre el cambio en los datos
                                textos_servicios.put("url_imagen", imageUrl);

                                //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                                DocumentReference documentoRef = db.collection("Servicio")
                                        .document("lista");
                                // Cargamos los datos
                                CollectionReference publicacionesRef = documentoRef.collection("publicaciones");

                                // Después de agregar la publicación a la base de datos con éxito
                                publicacionesRef.add(textos_servicios)
                                        .addOnSuccessListener(documentReference -> {
                                            // Crear un objeto DatosPublicacionTalleres con los datos ingresados
                                            PublicacionDatosServicio nuevaPublicacion = new PublicacionDatosServicio();
                                            nuevaPublicacion.setNombre(newNombre);
                                            nuevaPublicacion.setDepartamento(newDepa);
                                            nuevaPublicacion.setCorreo(newCorreo);
                                            nuevaPublicacion.setTelefono(newTel);
                                            nuevaPublicacion.setRequisitos(newRequisitos);

                                            // Agregar la nueva publicación a la lista
                                            lista_datos_servicio.add(nuevaPublicacion);

                                            // Notificar al adaptador sobre el cambio en los datos
                                            mAdapter.notifyDataSetChanged();

                                            // Mostrar un mensaje de éxito
                                            Toast.makeText(Activity_ServicioSocial.this, "Publicación agregada correctamente", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            // Mostrar un mensaje de error en caso de falla
                                            Toast.makeText(Activity_ServicioSocial.this, "Error al agregar la publicación", Toast.LENGTH_SHORT).show();
                                            //Log.e("Firestore", "Error al agregar la publicación", e);
                                        });
                            });
                        });
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

        }
    }
}