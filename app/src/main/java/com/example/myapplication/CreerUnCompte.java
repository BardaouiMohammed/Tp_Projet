package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreerUnCompte extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Spinner villeSpinner;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_un_compte);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.Password);
        villeSpinner = findViewById(R.id.spinner);

        String[] villes = {"Agadir", "Rabat", "Casablnca", "Settat"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, villes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        villeSpinner.setAdapter(adapter);


        Button createAccountButton = findViewById(R.id.button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();
                String ville = villeSpinner.getSelectedItem().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(CreerUnCompte.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                long newRowId = new Bdd(CreerUnCompte.this).addUser(email, password, ville);
                if (newRowId != -1) {
                    Toast.makeText(CreerUnCompte.this, "Utilisateur créé avec succès", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreerUnCompte.this, Connexion.class));
                } else {
                    Toast.makeText(CreerUnCompte.this, "Erreur lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
