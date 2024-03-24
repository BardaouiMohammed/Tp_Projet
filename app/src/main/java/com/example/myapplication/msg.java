package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class msg extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        String selectedVille = getIntent().getStringExtra("selectedVille");

        int countAnnonces = new Bdd(this).getCountAnnoncesByVille(selectedVille);

        TextView textViewMsg = findViewById(R.id.mmsg);
        String message = "Il y'a actuellement  " + countAnnonces + "annonce(s) sur " + selectedVille;
        textViewMsg.setText(message);
    }
}