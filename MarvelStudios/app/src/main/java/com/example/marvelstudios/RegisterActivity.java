package com.example.marvelstudios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtDate, edtPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtDate = findViewById(R.id.edtDate);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String email = edtEmail.getText().toString();
                String date = edtDate.getText().toString();
                String password = edtPassword.getText().toString();
                if (name.equals("") || email.equals("") || date.equals("") ||password.equals(""))
                    Toast.makeText(RegisterActivity.this, "Por favor llenar los campos ", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(RegisterActivity.this, "Usuario registrado, por favor inicia sesión ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}