package com.example.itoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DatosPersonales extends AppCompatActivity {
    private String num_control;
    private TextView nombre;
    private TextView numControl;
    private TextView correo;
    private TextView refBancaria;
    private ImageButton editName;
    private ImageButton editBank;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);
        num_control = Menu.getNum_control();
        //Toast.makeText(DatosPersonales.this, "num_control: " + num_control, Toast.LENGTH_SHORT).show();
        nombre = findViewById(R.id.textNombre);
        numControl = findViewById(R.id.textControl);
        correo = findViewById(R.id.textCorreo);
        refBancaria = findViewById(R.id.textRFB);
        editName = findViewById(R.id.editName);
        editBank = findViewById(R.id.editBank);

        numControl.setText(num_control);
        correo.setText(num_control+"@itocotlan.com");
        // Obtener una referencia al documento existente
        DocumentReference docRef = db.collection("Users").document(num_control);

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflar el layout del cuadro de diálogo personalizado
                View dialogView = LayoutInflater.from(DatosPersonales.this).inflate(R.layout.edit_name, null);

                // Obtener referencia al EditText en el layout del cuadro de diálogo
                EditText editTextName = dialogView.findViewById(R.id.editTextName);

                // Crear el cuadro de diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(DatosPersonales.this);
                builder.setView(dialogView)
                        .setTitle("Editar Nombre")
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Obtener el nombre ingresado por el usuario
                                String Name = editTextName.getText().toString();
                                // Actualizar solo el nuevo campo sin afectar los campos existentes
                                docRef.update("nombre", Name);
                                //nombre.setText(Name);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        editBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflar el layout del cuadro de diálogo personalizado
                View dialogView = LayoutInflater.from(DatosPersonales.this).inflate(R.layout.edit_referencia, null);

                // Obtener referencia al EditText en el layout del cuadro de diálogo
                EditText editTextRF = dialogView.findViewById(R.id.editTextRB);

                // Crear el cuadro de diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(DatosPersonales.this);
                builder.setView(dialogView)
                        .setTitle("Editar Datos")
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Obtener el nombre ingresado por el usuario
                                String RF = editTextRF.getText().toString();
                                // Actualizar el TextView con el nombre ingresado
                                // Actualizar solo el nuevo campo sin afectar los campos existentes
                                docRef.update("referencia", RF);
                                //refBancaria.setText(RF);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("FIRESTORE", "Error al escuchar el documento", e);
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Verificar si el campo "nombre" existe en el documento
                    if (documentSnapshot.contains("nombre")) {
                        // Si el campo "nombre" existe, obtener su valor
                        String nombreDB = documentSnapshot.getString("nombre");
                        // Actualizar el contenido del TextView con el nombre del usuario
                        nombre.setText(nombreDB);
                    } else {
                        // Si el campo "nombre" no existe, mostrar un mensaje alternativo
                        nombre.setText("");
                    }
                    // Verificar si el campo "referencia" existe en el documento
                    if (documentSnapshot.contains("referencia")) {
                        // Si el campo "referencia" existe, obtener su valor
                        String referencia = documentSnapshot.getString("referencia");
                        // Actualizar el contenido del TextView con la referencia bancaria del usuario
                        refBancaria.setText(referencia);
                    } else {
                        // Si el campo "referencia" no existe, mostrar un mensaje alternativo
                        refBancaria.setText("");
                    }
                } else {
                    Log.d("FIRESTORE", "No existe el documento");
                }
            }
        });

    }
}