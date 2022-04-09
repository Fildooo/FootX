package com.example.footx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class ChargementActivity extends AppCompatActivity {

    private final int CHARGEMENT_TIMEOUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chargement);

        // Redirection vers la page principale "activity_main" après 3 secondes

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                startActivity(intent);
                finish();
            }
        }, CHARGEMENT_TIMEOUT);
    }
}