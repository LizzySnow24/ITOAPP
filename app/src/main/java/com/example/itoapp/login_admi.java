package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class login_admi extends AppCompatActivity {

    private TextInputEditText codigo_acceso;
    private TextInputEditText contraseña;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admi);
        codigo_acceso = (TextInputEditText) findViewById(R.id.codigo_acceso);
        contraseña = (TextInputEditText) findViewById(R.id.editTextPassword);
        mFirestore = FirebaseFirestore.getInstance();
    }

    public void verificar_admi(View v){
        String codigo = codigo_acceso.getText().toString();
        String contra = contraseña.getText().toString();

        if(!codigo.isEmpty()) {
            if (!contra.isEmpty()){
                mFirestore.collection("Admin").document(codigo).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Comparar el codigo del documento con el codigo de acceso
                            String documentId = documentSnapshot.getString("codigo");
                            if (documentId.equals(codigo)) {
                                // El codigo coincide, verificar la contraseña
                                String contra2 = documentSnapshot.getString("contraseña");
                                if (contra.equals(contra2)) {
                                    // Contraseña correcta, redirigir al menú
                                    Intent i = new Intent(login_admi.this, Menu.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(login_admi.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(login_admi.this, "Código no válido", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(login_admi.this, "Solicite su código", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }else {
                Toast.makeText(login_admi.this, "Ingrese contraseña", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(login_admi.this, "Ingrese el código", Toast.LENGTH_LONG).show();
        }
    }
}