package com.example.itoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class activity_crearPubli extends AppCompatActivity {

    //variable para saber si eligio una foto o no
    private static final int PICK_IMAGE_REQUEST = 1;

    // Instancia de FirebaseFirestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Obtén una instancia de FirebaseStorage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    // Variable para almacenar la URI de la imagen seleccionada
    Uri selectedImage;

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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtener la URI de la imagen seleccionada
            selectedImage = data.getData();

            // Obtener referencia a la ImageView dentro del CardView
            ImageView imagenPubli = findViewById(R.id.imagenPubli);
            imagenPubli.setImageURI(selectedImage);
        }
    }

    public void publicar(View v){

        // Obtener referencias al EditText y al TextView dentro del CardView
        EditText editTextTexto = findViewById(R.id.editTextTexto);
        TextView textView = findViewById(R.id.text_view);

        // Obtener el texto ingresado en el EditText
        String texto = editTextTexto.getText().toString().trim();

        // Obtener referencia a la ImageView dentro del CardView
        ImageView imagenPubli = findViewById(R.id.imagenPubli);

        // Verificar si se ha seleccionado una imagen
        Drawable drawable = imagenPubli.getDrawable();
        boolean imagenSeleccionada = (drawable != null);

        // Obtener el semestre seleccionado del Spinner
        Spinner lista_semestres = findViewById(R.id.lista);
        String semestreSeleccionado = lista_semestres.getSelectedItem().toString();

        // Verificar si el texto y la imagen están presentes
        // Verificar si el usuario ha ingresado texto o seleccionado una imagen
        if (!texto.isEmpty() || imagenSeleccionada) {

            // Clasificar y mostrar contenido según el semestre seleccionado
            if(semestreSeleccionado.equals("Selecciona el semestre")){
                Toast.makeText(this, "Por favor, seleccione un semestre para continuar", Toast.LENGTH_SHORT).show();

            }else{

                if (imagenSeleccionada) {
                    StorageReference imagenRef = storageRef.child("imagenes/" + UUID.randomUUID().toString());
                    String nombreImagen = imagenRef.getName(); // Obtener el nombre único de la imagen
                    imagenRef.putFile(selectedImage)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    imagenRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String urlDeLaImagen = uri.toString();
                                            DocumentReference semestreRef = db.collection("Semestre").document(semestreSeleccionado);
                                            CollectionReference publicacionesRef = semestreRef.collection("publicaciones");
                                            Map<String, Object> publicacion = new HashMap<>();
                                            if (!texto.isEmpty()) {
                                                publicacion.put("texto", texto);
                                            }
                                            publicacion.put("imagen", urlDeLaImagen);
                                            publicacion.put("nombreImagen", nombreImagen); // Guardar el nombre único de la imagen
                                            publicacionesRef.add(publicacion)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(activity_crearPubli.this, "Publicación agregada correctamente", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(activity_crearPubli.this, "Error al agregar la publicación", Toast.LENGTH_SHORT).show();
                                                            Log.e("Firestore", "Error al agregar la publicación", e);
                                                        }
                                                    });
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(activity_crearPubli.this, "Error al subir la imagen", Toast.LENGTH_SHORT).show();
                                    Log.e("FirebaseStorage", "Error al subir la imagen", e);
                                }
                            });
                } else {
                    Toast.makeText(this, "No se ha seleccionado ninguna imagen.", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // Mostrar un mensaje de error si el usuario no ha ingresado texto ni seleccionado una imagen
            Toast.makeText(this, "Por favor, ingrese texto o seleccione una imagen.", Toast.LENGTH_SHORT).show();
        }
    }
}