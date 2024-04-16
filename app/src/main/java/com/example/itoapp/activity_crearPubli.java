package com.example.itoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class activity_crearPubli extends AppCompatActivity {

    //variable para saber si eligio una foto o no
    private static final int PICK_IMAGE_REQUEST = 1;

    // Instancia de FirebaseFirestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Obtén una instancia de FirebaseStorage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    // Lista para almacenar la URI de las imagenes seleccionadas
    private List<Uri> selectedImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_publi);

        // Obtener referencia al botón de selección de imagen
        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);
        // Configurar el listener de clic para el botón de selección de imagen
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para seleccionar una imagen de la galería
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Iniciar la actividad para seleccionar una imagen
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        //Establece lista del spinner
        Spinner lista_Semestres = (Spinner) findViewById(R.id.lista);
        String[] datosSemestres = new String[]{"Seleccionar semestre","1er. Semestre", "2do. Semestre", "3er. Semestre", "4to. Semestre", "5to. Semestre", "6to. Semestre", "7mo. Semestre", "8vo. Semestre", "9no. Semestre"};
        ArrayAdapter adapterSemestres = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, datosSemestres);
        adapterSemestres.setDropDownViewResource(R.layout.spinner_item);
        lista_Semestres.setAdapter(adapterSemestres);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificar si el resultado es para seleccionar una imagen y si se seleccionó una imagen correctamente
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data.getClipData() != null) {
                // Si se seleccionaron múltiples imágenes
                int count = data.getClipData().getItemCount();
                selectedImages.clear();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
            } else if (data.getData() != null) {
                // Si se seleccionó solo una imagen
                Uri imageUri = data.getData();
                selectedImages.clear();
                selectedImages.add(imageUri);
            }
            // Actualiza la interfaz para mostrar las imágenes seleccionadas
            mostrarImagenesSeleccionadas();
        }
    }

    private void mostrarImagenesSeleccionadas() {
        // Referencia a tu GridLayout
        GridLayout layoutImagenes = findViewById(R.id.layoutImagenes);

        // Calcula la cantidad de filas necesarias
        int numFilas = (int) Math.ceil((double) selectedImages.size() / 2);

        // Configura la cantidad de columnas y filas en el GridLayout
        layoutImagenes.setRowCount(numFilas);

        // Añade cada imagen al layout
        layoutImagenes.getColumnCount();

        // Añade cada imagen seleccionada al GridLayout
        for (Uri imageUri : selectedImages) {
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(imageUri);

            // Configura el tamaño y otras propiedades para cada imagen según tus necesidades
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            // Establece la posición de la imagen en la cuadrícula
            params.rowSpec = GridLayout.spec(1);
            params.columnSpec = GridLayout.spec(1);
            //params.setMargins(8, 8, 8, 8); // Ajusta los márgenes de las imágenes

            imageView.setLayoutParams(new GridLayout.LayoutParams(
                    new ViewGroup.LayoutParams(getResources().getDisplayMetrics().widthPixels / layoutImagenes.getColumnCount(),
                            getResources().getDisplayMetrics().heightPixels/2)
            ));

            // Añade la imagen al GridLayout
            layoutImagenes.addView(imageView);

        }
    }

    public void publicar(View v){
        // Obtener referencias al EditText y al TextView dentro del CardView
        EditText editTextTexto = findViewById(R.id.editTextTexto);

        // Obtener el texto ingresado en el EditText
        String texto = editTextTexto.getText().toString().trim();

        // Verificar si se han seleccionado imágenes
        boolean imagenSeleccionada = !selectedImages.isEmpty();

        // Obtener el semestre seleccionado del Spinner
        Spinner lista_semestres = findViewById(R.id.lista);
        String semestreSeleccionado = lista_semestres.getSelectedItem().toString();

        if (!texto.isEmpty() || imagenSeleccionada) {
            if(semestreSeleccionado.equals("Seleccionar semestre")) {
                Toast.makeText(this, "Por favor, seleccione un semestre para continuar", Toast.LENGTH_SHORT).show();
                return;
            }
            // Mostrar el ProgressBar
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            if (imagenSeleccionada) {
                //Arreglo que va a almacenar los url de todas las imagenes
                List<String> imageUrls = new ArrayList<>();
                //variable para saber cuantas imagenes hay en mi lista
                int imageCount = selectedImages.size();
                AtomicInteger uploadedCount = new AtomicInteger(0);

                for (Uri imageUri : selectedImages) {
                    StorageReference imagenRef = storageRef.child("imagenes/" + UUID.randomUUID().toString());
                    String nombreImagen = imagenRef.getName();
                    imagenRef.putFile(imageUri)
                            .addOnSuccessListener(taskSnapshot -> {
                                imagenRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    String urlDeLaImagen = uri.toString();
                                    imageUrls.add(urlDeLaImagen);
                                    uploadedCount.incrementAndGet();

                                    if (uploadedCount.get() == imageCount) {
                                        // Ocultar el ProgressBar antes de guardar la publicación
                                        progressBar.setVisibility(View.GONE);
                                        guardarPublicacion(texto, imageUrls, nombreImagen, semestreSeleccionado);
                                    }
                                });
                            })
                            .addOnFailureListener(e -> {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(activity_crearPubli.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                                Log.e("FirebaseStorage", "Error al subir la imagen", e);
                            });
                }
            } else {
                progressBar.setVisibility(View.GONE);
                guardarPublicacion(texto, Collections.emptyList(), null, semestreSeleccionado);
            }
        } else {
            Toast.makeText(this, "Por favor, ingrese texto o seleccione una imagen.", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarPublicacion(String texto, List<String> imageUrls, String nombreImagen, String semestreSeleccionado) {
        DocumentReference semestreRef = db.collection("Semestre").document(semestreSeleccionado);
        CollectionReference publicacionesRef = semestreRef.collection("publicaciones");

        Map<String, Object> publicacion = new HashMap<>();
        if (!texto.isEmpty()) {
            publicacion.put("texto", texto);
        }
        if (!imageUrls.isEmpty()) {
            publicacion.put("imagenes", imageUrls);
        }
        if (nombreImagen != null) {
            publicacion.put("nombreImagen", nombreImagen);
        }

        publicacionesRef.add(publicacion)
                .addOnSuccessListener(documentReference -> {
                    String referenciaDocumento = documentReference.getId();
                    String semestre = semestreSeleccionado;
                    Toast.makeText(activity_crearPubli.this, "Publicación agregada correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_crearPubli.this, Menu.class);
                    intent.putExtra("semestre",semestre);
                    intent.putExtra("documento",referenciaDocumento);
                    startActivity(intent);
                    //finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(activity_crearPubli.this, "Error al agregar la publicación", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "Error al agregar la publicación", e);
                });
    }
    public void cancelar(View v){
        finish();
    }

}