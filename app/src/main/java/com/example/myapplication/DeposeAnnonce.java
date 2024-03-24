package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class DeposeAnnonce extends AppCompatActivity {

    private Spinner spinnerCategorie, spinnerSecteur, spinnerVille;
    private EditText editTextTitre, editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposeannonce);

        spinnerCategorie = findViewById(R.id.spinner4);
        spinnerSecteur = findViewById(R.id.spinner5);
        spinnerVille = findViewById(R.id.spinner6);
        editTextTitre = findViewById(R.id.editTextText7);
        editTextDescription = findViewById(R.id.editTextText9);

        String[] categories = {"Informatique", "Finance", "Marketing", "Ressources humaines"};
        ArrayAdapter<String> categorieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategorie.setAdapter(categorieAdapter);

        String[] secteurs = {"Public", "Privé"};
        ArrayAdapter<String> secteurAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, secteurs);
        spinnerSecteur.setAdapter(secteurAdapter);

        String[] villes = {"Agadir", "Rabat", "Casablnca", "Settat"};
        ArrayAdapter<String> villeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, villes);
        spinnerVille.setAdapter(villeAdapter);

        Button buttonSuivant = findViewById(R.id.button3);
        buttonSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titre = editTextTitre.getText().toString();
                String description = editTextDescription.getText().toString();
                String categorie = spinnerCategorie.getSelectedItem().toString();
                String secteur = spinnerSecteur.getSelectedItem().toString();
                String ville = spinnerVille.getSelectedItem().toString();

                long result = new Bdd(DeposeAnnonce.this).addAnnonce(titre, categorie, secteur, description, ville);

                Intent intent = new Intent(DeposeAnnonce.this, msg.class);
                intent.putExtra("selectedVille", ville);
                startActivity(intent);
            }
        });
    }
}
