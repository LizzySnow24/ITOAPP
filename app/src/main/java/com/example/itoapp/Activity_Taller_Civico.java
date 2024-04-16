package com.example.itoapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_Taller_Civico extends AppCompatActivity {

    String instructor;
    String telefono;
    String horario;
    String lugar;
    String inicio_taller;
    TextView instru;
    TextView tel;
    TextView hora;
    TextView place;
    TextView inicioTaller;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference documentoRef;

    public void MandarTextoBD(){
        instru = findViewById(R.id.instructor);
        tel = findViewById(R.id.textTel);
        hora = findViewById(R.id.textHorario);
        place = findViewById(R.id.textLugar);
        inicioTaller = findViewById(R.id.textInicioTaller);

        //Referencia de la base de datos donde tengo almacenados las imagenes y texto
         documentoRef = db.collection("Talleres")
                .document("civicos");

        documentoRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String textInstructor = documentSnapshot.getString("instructor");
                    String textHorario = documentSnapshot.getString("horario");
                    String textLugar = documentSnapshot.getString("lugar");
                    String textTelefono = documentSnapshot.getString("telefono");
                    String textInicio_talleres = documentSnapshot.getString("inicio_talleres");

                    instru.setText(textInstructor);
                    hora.setText(textHorario);
                    place.setText(textLugar);
                    tel.setText(textTelefono);
                    inicioTaller.setText(textInicio_talleres);
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
        instru = findViewById(R.id.instructor);
        tel = findViewById(R.id.textTel);
        hora = findViewById(R.id.textHorario);
        place = findViewById(R.id.textLugar);
        inicioTaller = findViewById(R.id.textInicioTaller);

        String rol = Menu.getRol();

        // Verificar si el rol es "admin" para mostrar u ocultar el botón flotante
        if (rol != null && rol.equals("admin")) {
            boton_editar2.setVisibility(View.VISIBLE);

            MandarTextoBD();
            boton_editar2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModificarTextos();
                }
            });
        } else {
            boton_editar2.setVisibility(View.GONE);
        }
    }

    public void ModificarTextos(){
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
                            MandarTextoBD();
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
        AlertDialog alertDialog = builder.create();
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

}