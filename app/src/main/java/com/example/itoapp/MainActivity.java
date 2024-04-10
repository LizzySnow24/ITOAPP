package com.example.itoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore mFirestore;
    private TextInputEditText num_control;
    private TextInputEditText contraseña;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num_control = (TextInputEditText) findViewById(R.id.numText);
        contraseña = (TextInputEditText) findViewById(R.id.editTextPassword);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }
    public void startActivity(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void abrirRegister(View v) {
        Intent i = new Intent(this, register.class);
        startActivity(i);
    }
    public void abrirLoginAdmi(View v) {
        Intent i = new Intent(this, login_admi.class);
        startActivity(i);
    }
    public void recuperarContra(View v) {
        Intent i = new Intent(this, recuperar_contra.class);
        startActivity(i);
    }

    public void Verificar(View v) {
        String numControl = num_control.getText().toString();
        String password = contraseña.getText().toString();

                if (!numControl.isEmpty()) {
                    if (!password.isEmpty()) {
                        // Consultar Firestore para obtener el documento con el número de control proporcionado
                        mFirestore.collection("Users").document(numControl).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    // Comparar el ID del documento con el número de control
                                    String documentId = documentSnapshot.getId();
                                    if (documentId.equals(numControl)) {
                                        // El número de control coincide, verificar la contraseña
                                        String contra = documentSnapshot.getString("contraseña");
                                        if (contra.equals(password)) {
                                            //paso el rol del usuario a un string
                                             String rol = documentSnapshot.getString("rol");
                                            // Contraseña correcta, redirigir al menú
                                            Intent i = new Intent(MainActivity.this, Menu.class);
                                            i.putExtra("rol",rol);
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, "El número de control no se encuentra registrado", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Realice su registro", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Ingrese contraseña", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Ingrese número de control", Toast.LENGTH_LONG).show();
                }
    }
}