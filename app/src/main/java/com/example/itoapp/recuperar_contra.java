package com.example.itoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class recuperar_contra extends AppCompatActivity {

    private TextInputEditText email;
    MaterialButton boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);
        email =(TextInputEditText) findViewById(R.id.email2);

    }
}