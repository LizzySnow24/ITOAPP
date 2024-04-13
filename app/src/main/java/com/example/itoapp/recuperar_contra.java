package com.example.itoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class recuperar_contra extends AppCompatActivity {

    FirebaseAuth auth;
    private TextInputEditText email;
    MaterialButton boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);
        email =(TextInputEditText) findViewById(R.id.email2);
        auth = FirebaseAuth.getInstance();
    }

    public boolean correoValido(String correo){
        Pattern verificar_correo = Pattern.compile("^\\d{8}@itocotlan\\.com$");
        if(correo.isEmpty()){
            Toast.makeText(recuperar_contra.this,
                    "Ingrese un correo.",
                    Toast.LENGTH_SHORT).show();
        }
        if(!verificar_correo.matcher(correo).matches()){
            email.setError("Correo electrónico inválido");
        }else{
            email.setError(null);
        }
        return true;
    }

    public void recuperar(View v){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String correo = email.getText().toString();

            if (correoValido(correo) == true) {
                auth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(recuperar_contra.this,
                                    "Te hemos enviado instrucciones para restablecer tu contraseña.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(recuperar_contra.this,
                                    "Error al enviar correo para restablecer tu contraseña.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
    }
}