package com.example.itoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Activity_Taller_Civico extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AlertDialog alertDialog;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    private Uri selectedImageUri;

    private DocumentReference documentoRef;

    public void MandarTextoBD(int instruId, int telId, int horaId, int placeId, int inicioTallerId, int imagenId){
        TextView instru = findViewById(instruId);
        TextView tel = findViewById(telId);
        TextView hora = findViewById(horaId);
        TextView place = findViewById(placeId);
        TextView inicioTaller = findViewById(inicioTallerId);
        ImageView imagen = findViewById(imagenId);

        //referencia a storage
        StorageReference imagenRef = storageRef.child("imagenes_talleres/" + UUID.randomUUID().toString());

        documentoRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String textInstructor = documentSnapshot.getString("instructor");
                    String textHorario = documentSnapshot.getString("horario");
                    String textLugar = documentSnapshot.getString("lugar");
                    String textTelefono = documentSnapshot.getString("telefono");
                    String textInicio_talleres = documentSnapshot.getString("inicio_talleres");
                    String imagenurl = documentSnapshot.getString("url_imagen");

                    instru.setText(textInstructor);
                    hora.setText(textHorario);
                    place.setText(textLugar);
                    tel.setText(textTelefono);
                    inicioTaller.setText(textInicio_talleres);
                    // Carga la imagen
                    Glide.with(getApplicationContext())
                            .load(imagenurl)
                            .into(imagen);
                   // imagen.setScaleType(ImageView.ScaleType.CENTER_CROP);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taller_civico);

        FloatingActionButton boton_editar2 = findViewById(R.id.boton_editar2);
        //Referencia de la base de datos donde tengo almacenados las imagenes y texto
        documentoRef = db.collection("Talleres")
                .document("civicos");

        String rol = Menu.getRol();
        MandarTextoBD(R.id.instructor, R.id.textTel, R.id.textHorario, R.id.textLugar, R.id.textInicioTaller, R.id.imagen_civico);

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar2.setVisibility(View.VISIBLE);
            boton_editar2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModificarTextos(
                            R.id.instructor, R.id.textTel, R.id.textHorario, R.id.textLugar, R.id.textInicioTaller
                    );
                }
            });
        } else {
            boton_editar2.setVisibility(View.GONE);
        }
    }

    public void ModificarTextos(int instruId, int telId, int horaId, int placeId, int inicioTallerId){
        // Infla el diseño personalizado para el cuadro de diálogo
        View dialogView = getLayoutInflater().inflate(R.layout.pantalla_editar_civicos, null);

        // Crea el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Taller_Civico.this);
        builder.setView(dialogView);

        // Obtén referencias a los EditText en el diseño personalizado
        EditText editTel = dialogView.findViewById(R.id.editTel);
        EditText editNombre = dialogView.findViewById(R.id.editinstructor);
        EditText editLugar = dialogView.findViewById(R.id.editLugar);
        EditText editHorario = dialogView.findViewById(R.id.editHorario);
        EditText editInicio = dialogView.findViewById(R.id.editInicio);

        // Obtén el texto actual de los TextView
        TextView instru = findViewById(instruId);
        TextView tel = findViewById(telId);
        TextView hora = findViewById(horaId);
        TextView place = findViewById(placeId);
        TextView inicioTaller = findViewById(inicioTallerId);
        String currentNombre = instru.getText().toString();
        String currentTel = tel.getText().toString();
        String currentLugar = place.getText().toString();
        String currentHorario = hora.getText().toString();
        String currentInicio = inicioTaller.getText().toString();

        // Establece el texto actual en los EditText
        editNombre.setText(currentNombre);
        editTel.setText(currentTel);
        editLugar.setText(currentLugar);
        editHorario.setText(currentHorario);
        editInicio.setText(currentInicio);
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
            String newNombre = editNombre.getText().toString();
            String newTel = editTel.getText().toString();
            String newLugar = editLugar.getText().toString();
            String newHorario = editHorario.getText().toString();
            String newInicio = editInicio.getText().toString();

            //Referencia de la base de datos donde tengo almacenados las imagenes y texto
            documentoRef = db.collection("Talleres")
                    .document("civicos");

            final Map<String, Object> textos = new HashMap<>();

            if (!newNombre.isEmpty()) {
                textos.put("instructor", newNombre);
            }
            if (!newTel.isEmpty()) {
                textos.put("telefono", newTel);
            }
            if (!newHorario.isEmpty()) {
                textos.put("horario", newHorario);
            }
            if (!newLugar.isEmpty()) {
                textos.put("lugar", newLugar);
            }
            if (!newInicio.isEmpty()) {
                textos.put("inicio_talleres", newInicio);
            }


            // Actualiza los datos en la base de datos
            documentoRef.update(textos)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Manejar el éxito de la actualización
                            Toast.makeText(Activity_Taller_Civico.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

                            // Mostrar los cambios en la aplicación
                            MandarTextoBD(R.id.instructor, R.id.textTel, R.id.textHorario, R.id.textLugar, R.id.textInicioTaller, R.id.imagen_civico);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar el fallo de la actualización
                            Log.e(TAG, "Error al actualizar datos", e);
                            Toast.makeText(Activity_Taller_Civico.this, "Error al actualizar datos", Toast.LENGTH_SHORT).show();
                        }
                    });
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

        ImageView imagen = findViewById(R.id.imagen_civico);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtener la URI de la imagen seleccionada
            selectedImageUri = data.getData();
            if (alertDialog != null) {
                imagen = alertDialog.findViewById(R.id.imageView);

                // Asegúrate de que la referencia del ImageView no sea nula
                if (imagen != null) {
                    // Establece la URI de la imagen seleccionada en el ImageView
                    imagen.setImageURI(selectedImageUri);
                }

            }
            // Obtén una referencia a la ubicación donde se guardará la imagen en Storage
            StorageReference imagenRef = storageRef.child("imagenes_talleres/" + UUID.randomUUID().toString());

            // Sube la imagen seleccionada a Firebase Storage
            imagenRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Obtiene la URL de la imagen recién subida
                        imagenRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();

                            // Actualiza el URL de la imagen en la base de datos
                            Map<String, Object> textos = new HashMap<>();
                            textos.put("url_imagen", imageUrl);

                            documentoRef.update(textos)
                                    .addOnSuccessListener(aVoid -> {
                                        // Actualización exitosa, muestra un mensaje
                                        Toast.makeText(Activity_Taller_Civico.this, "Imagen actualizada correctamente", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Error al actualizar el URL de la imagen en la base de datos
                                        Log.e(TAG, "Error al actualizar URL de imagen", e);
                                        Toast.makeText(Activity_Taller_Civico.this, "Error al actualizar URL de imagen", Toast.LENGTH_SHORT).show();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Error al cargar la imagen a Firebase Storage
                        Log.e(TAG, "Error al cargar la imagen", e);
                        Toast.makeText(Activity_Taller_Civico.this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}