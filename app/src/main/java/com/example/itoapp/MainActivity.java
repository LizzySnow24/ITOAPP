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
    String hola;

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

    public void abrirRegister(View v) {
        Intent i = new Intent(this, register.class);
        startActivity(i);
    }
    public void recuperarContra(View v) {
        Intent i = new Intent(this, recuperar_contra.class);
        startActivity(i);
    }

    public void Verificar(View v) {
        String numControl = num_control.getText().toString();
        String password = contraseña.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        String id = mAuth.getCurrentUser().getUid();

        if (!numControl.isEmpty()) {
            if (!(password.isEmpty())) {
                if (user != null && user.isEmailVerified()) {
                    mFirestore.collection("Users").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String NumControl = documentSnapshot.getString("num_control");
                                String contra = documentSnapshot.getString("contraseña");
                                if (NumControl.equals(numControl)) {
                                    if (contra.equals(password)) {
                                        // Toast.makeText(MainActivity.this, ":)", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(MainActivity.this, Menu.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "El numero de control no se encuentra registrado", Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "Realice su registro", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(MainActivity.this, "No se ha verificado el registro del usuario", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(MainActivity.this, "Ingrese contraseña", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "Ingrese numero de control", Toast.LENGTH_LONG).show();
        }
    }
}