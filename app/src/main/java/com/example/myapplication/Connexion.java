package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Bdd;
import com.example.myapplication.DeposeAnnonce;
import com.example.myapplication.R;

public class Connexion extends AppCompatActivity {
    private Bdd bdd;
    private TextView toastTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bdd = new Bdd(this);
        toastTextView = findViewById(R.id.toastmsg);
    }
    public void btnClick(View view) {
        EditText emailEditText = findViewById(R.id.email);
        EditText motEditText = findViewById(R.id.password);

        String email = emailEditText.getText().toString();
        String password = motEditText.getText().toString();


        if (email.isEmpty() || password.isEmpty()) {
            showTextInToastTextView("Veuillez entrer votre email et mot de passe.");
            return;
        }

        boolean verifier = bdd.verifierUtilisateur(email, password);

        if (verifier) {
            showToast("Connexion réussie");
            Intent intent = new Intent(Connexion.this, DeposeAnnonce.class);
            startActivity(intent);
        } else {
            showTextInToastTextView("L'email ou le mot de passe saisi est incorrect.");
        }
    }

    public void btnInscriptionClick(View view) {
        Intent intent = new Intent(this, CreerUnCompte.class);
        startActivity(intent);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showTextInToastTextView(String message) {
        toastTextView.setText(message);
    }
}