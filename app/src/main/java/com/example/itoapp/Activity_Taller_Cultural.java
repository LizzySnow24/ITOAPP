package com.example.itoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Activity_Taller_Cultural extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AlertDialog alertDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    final Map<String, Object> textos2 = new HashMap<>();
    private Uri selectedImageUri;
    private List<DatosPublicacionTalleres> lista_datos2;
    private PublicacionTalleresAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String newTitulo;
    private String newNombre;
    private String newTel;
    private String newLugar;
    private String newHorario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taller_cultural);

        FloatingActionButton boton_editar3 = findViewById(R.id.boton_editar3);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar3.setVisibility(View.VISIBLE);
            //ABRIR VENTANA EDITAR
            boton_editar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HacerPublicacion();
                }
            });
        } else {
            boton_editar3.setVisibility(View.GONE);
        }
        lista_datos2 = new ArrayList<>();

        //TRAER TODOS LOS DATOS
        db.collection("Talleres")
                .document("culturales")
                .collection("taller1")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        DatosPublicacionTalleres publicacion = document.toObject(DatosPublicacionTalleres.class);
                        lista_datos2.add(publicacion);
                        //Log.d("DATO CIVICO",publicacion.getHorario());
                    }
                    mAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error al obtener publicaciones", e);
                });
        // Configurar RecyclerView y su adaptador
        mRecyclerView =findViewById(R.id.recycle_taller_cultural);
        mAdapter = new PublicacionTalleresAdapter(Activity_Taller_Cultural.this, lista_datos2);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Activity_Taller_Cultural.this));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void HacerPublicacion(){
        // Infla el diseño personalizado para el cuadro de diálogo
        View dialogView = getLayoutInflater().inflate(R.layout.pantalla_editar_civicos, null);

        // Crea el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Taller_Cultural.this);
        builder.setView(dialogView);

        // ESTOS SON MIS TEXTOS DE MI CUADRO PARA INGRESAR LOS DATOS
        EditText editTitu = dialogView.findViewById(R.id.editTitulo);
        EditText editTel = dialogView.findViewById(R.id.editTel);
        EditText editNombre = dialogView.findViewById(R.id.editinstructor);
        EditText editLugar = dialogView.findViewById(R.id.editLugar);
        EditText editHorario = dialogView.findViewById(R.id.editHorario);

        Button mostrarCambiar = dialogView.findViewById(R.id.mostrarCambiar);

        //desplegar el cambio de imagen
        mostrarCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout contentLayout = dialogView.findViewById(R.id.contentLayout);
                contentLayout.setVisibility(View.VISIBLE);
            }
        });

        //boton cambiar
        Button change = dialogView.findViewById(R.id.btnChangeImage);
        change.setOnClickListener(new View.OnClickListener() {
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
            newTitulo = editTitu.getText().toString();
            newNombre = editNombre.getText().toString();
            newTel = editTel.getText().toString();
            newLugar = editLugar.getText().toString();
            newHorario = editHorario.getText().toString();

            if (!newTitulo.isEmpty()) {
                textos2.put("titulo", newTitulo);
            }
            if (!newNombre.isEmpty()) {
                textos2.put("instructor", newNombre);
            }
            if (!newTel.isEmpty()) {
                textos2.put("telefono", newTel);
            }
            if (!newHorario.isEmpty()) {
                textos2.put("horario", newHorario);
            }
            if (!newLugar.isEmpty()) {
                textos2.put("lugar", newLugar);
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
            StorageReference imagenRef = storageRef.child("imagenes_talleres/imagenes_culturales/" + UUID.randomUUID().toString());

            // Sube la imagen seleccionada a Firebase Storage
            imagenRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Obtiene la URL de la imagen recién subida
                        imagenRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            // Notificar al adaptador sobre el cambio en los datos
                            mAdapter.notifyDataSetChanged();//esto
                            textos2.put("url_imagen", imageUrl);

                            //Referencia de la base de datos donde tengo almacenados las imagenes y texto
                            DocumentReference documentoRef = db.collection("Talleres")
                                    .document("culturales");

                            // Cargamos los datos
                            CollectionReference publicacionesRef = documentoRef.collection("taller1");

                            // Después de agregar la publicación a la base de datos con éxito
                            publicacionesRef.add(textos2)
                                    .addOnSuccessListener(documentReference -> {
                                        // Crear un objeto DatosPublicacionTalleres con los datos ingresados
                                        DatosPublicacionTalleres nuevaPublicacion = new DatosPublicacionTalleres();
                                        nuevaPublicacion.setTitulo(newTitulo);
                                        nuevaPublicacion.setInstructor(newNombre);
                                        nuevaPublicacion.setTelefono(newTel);
                                        nuevaPublicacion.setHorario(newHorario);
                                        nuevaPublicacion.setLugar(newLugar);

                                        // Agregar la nueva publicación a la lista
                                        lista_datos2.add(nuevaPublicacion);

                                        // Notificar al adaptador sobre el cambio en los datos
                                        mAdapter.notifyDataSetChanged();

                                        // Mostrar un mensaje de éxito
                                        Toast.makeText(Activity_Taller_Cultural.this, "Publicación agregada correctamente", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Mostrar un mensaje de error en caso de falla
                                        Toast.makeText(Activity_Taller_Cultural.this, "Error al agregar la publicación", Toast.LENGTH_SHORT).show();
                                        //Log.e("Firestore", "Error al agregar la publicación", e);
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Error al cargar la imagen a Firebase Storage
                        Log.e(TAG, "Error al cargar la imagen", e);
                        Toast.makeText(Activity_Taller_Cultural.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}