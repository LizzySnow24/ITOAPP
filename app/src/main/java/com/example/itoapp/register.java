package com.example.itoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class register extends AppCompatActivity {
    private TextInputEditText numText;
    private TextInputEditText contra;
    private TextInputEditText confirmcontra;
    private TextInputEditText email;
    private Button registerButton;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        numText = (TextInputEditText) findViewById(R.id.numText);
        contra = (TextInputEditText) findViewById(R.id.editTextPassword);
        confirmcontra = (TextInputEditText) findViewById(R.id.editTextConfirmPassword);
        registerButton = findViewById(R.id.registerButton);
        email = (TextInputEditText)findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    public void abrirLogin(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public boolean VerificarNumero(String num_control){
        boolean error = false;
        Pattern patron = Pattern.compile("^\\d{8}$");
        if(!(num_control.matches(patron.pattern()))){
            numText.setError("Numero de control inválido :(");
            error = true;
        }else{
            numText.setError(null);
        }
        return error;
    }
    public boolean VerificarContraseña(String contra1, String contra2){
        boolean error=false;
        Pattern patron = Pattern.compile("^[a-zA-Z0-9_]{8,}$");
        if(!(patron.matcher(contra1).find())){
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres [A-Z,a-z,1-9,_]", Toast.LENGTH_LONG).show();
            error=true;
        }else{
            if(!(contra1.equals(contra2))){
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                error=true;
            }
        }
        return error;
    }

    public String Direccion(){
        String direccion = email.getText().toString();
        Pattern validar = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        if(!(validar.matcher(direccion).find())) {
            Toast.makeText(this, "Correo no válido.", Toast.LENGTH_LONG).show();
        }
        return direccion;
    }
    public void mandarCorreo(){
        FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification();
    }
    public void Ejecutar(View v){
        Registrar();
    }
    public void Registrar(){
        String num_control = numText.getText().toString();
        String contra1 = contra.getText().toString();
        String contra2 = confirmcontra.getText().toString();

        if(!num_control.isEmpty()) {
            if(!contra1.isEmpty()||!contra2.isEmpty()) {
                boolean numValido = VerificarNumero(num_control);
                boolean contrasenaValida = VerificarContraseña(contra1, contra2);
                if (numValido==false && contrasenaValida==false) {
                    crearUsuario(Direccion(),num_control,contra1);
                }
            }else{
                contra.setError("Ingrese contraseña");
                Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_LONG).show();
            }
        }else{
            numText.setError("Ingrese numero de control");
        }
    }

    private void crearUsuario(final String direccion, final String num_control, final String contra) {

        // Verificar si el número de control ya existe
        mFirestore.collection("Users").document(num_control).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // El número de control ya existe
                        Toast.makeText(register.this, "El número de control ya está en uso.", Toast.LENGTH_LONG).show();
                    } else {
                        // El número de control no existe, proceder a crear el usuario
                        mAuth.createUserWithEmailAndPassword(direccion, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("email", direccion);
                                    map.put("contraseña", contra);
                                    map.put("rol", "usuario");
                                    mFirestore.collection("Users").document(num_control).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent i = new Intent(register.this, MainActivity.class);
                                                startActivity(i);
                                                Toast.makeText(register.this, "El usuario se registró correctamente.", Toast.LENGTH_LONG).show();
                                                mandarCorreo();
                                            } else {
                                                Toast.makeText(register.this, "Error al registrar el usuario en Firestore: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(register.this, "Error al registrar el usuario: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(register.this, "Error al verificar el número de control: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}