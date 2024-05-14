package com.example.itoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
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
                        mAuth.signInWithEmailAndPassword(numControl + "@itocotlan.com", password)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Inicio de sesión exitoso, obtener el usuario actual
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            if (user != null) {
                                                // El inicio de sesión fue exitoso, redirigir al menú
                                                // Obtener el rol del usuario desde Firestore
                                                mFirestore.collection("Users").document(numControl)
                                                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                if (documentSnapshot.exists()) {
                                                                    String rol = documentSnapshot.getString("rol");
                                                                    Intent i = new Intent(MainActivity.this, Menu.class);
                                                                    i.putExtra("rol", rol);
                                                                    i.putExtra("num_control",numControl);
                                                                    startActivity(i);
                                                                } else {
                                                                    // El usuario no tiene datos adicionales en Firestore
                                                                    Toast.makeText(MainActivity.this, "El número de control no se encuentra registrado", Toast.LENGTH_LONG).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        } else {
                                            // Error en el inicio de sesión
                                            Toast.makeText(MainActivity.this, "Número de control o contraseña incorrectos", Toast.LENGTH_LONG).show();
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