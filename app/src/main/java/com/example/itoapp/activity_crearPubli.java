package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class activity_crearPubli extends AppCompatActivity {

    //variable para saber si eligio una foto o no
    private static final int PICK_IMAGE_REQUEST = 1;

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
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificar si el resultado es para seleccionar una imagen y si se seleccionó una imagen correctamente
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtener la URI de la imagen seleccionada
            Uri selectedImage = data.getData();

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

        // Verificar si el texto y la imagen están presentes
        // Verificar si el usuario ha ingresado texto o seleccionado una imagen
        if (!texto.isEmpty() || imagenSeleccionada) {
            // Mostrar el texto en el TextView si está presente
            if (!texto.isEmpty()) {
                textView.setText(texto);
                textView.setVisibility(View.VISIBLE);
            } else {
                // Ocultar el TextView si no hay texto
                textView.setVisibility(View.GONE);
            }

            // Mostrar un mensaje indicando si hay una imagen seleccionada
            if (imagenSeleccionada) {
                Toast.makeText(this, "Se ha seleccionado una imagen.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se ha seleccionado ninguna imagen.", Toast.LENGTH_SHORT).show();
            }

            // Aquí puedes realizar otras acciones necesarias para mostrar la publicación,
            // como guardarla en una base de datos o enviarla a algún servicio.

        } else {
            // Mostrar un mensaje de error si el usuario no ha ingresado texto ni seleccionado una imagen
            Toast.makeText(this, "Por favor, ingrese texto o seleccione una imagen.", Toast.LENGTH_SHORT).show();
        }
    }
}