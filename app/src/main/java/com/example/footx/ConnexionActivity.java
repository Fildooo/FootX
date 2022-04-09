package com.example.footx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Visibility;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.footx.DAO.DBHandler;
import com.example.footx.databinding.ActivityConnexionBinding;
import com.example.footx.ui.notifications.NotificationsFragment;


public class ConnexionActivity extends AppCompatActivity {

    private com.example.footx.databinding.ActivityConnexionBinding binding;
    private EditText pseudo;
    private EditText mdp;
    private Button connect;
    private Button inscrirepc;
    private TextView error_msg;

    DBHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConnexionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Vérification mode avion au lancement de la page connexion
        ModeAvionActivity mode_avion = new ModeAvionActivity();
        if(ModeAvionON(this)) {
            Toast.makeText(this, "Le mode avion est activé ! Désactivez-le pour continuer", Toast.LENGTH_SHORT).show();
        }
        IntentFilter intentFilter = new IntentFilter("android.intent.action.AIRPLANE_MODE");
        this.registerReceiver(mode_avion,intentFilter);


        this.pseudo = (EditText) this.findViewById(R.id.editText_pseudo);
        this.mdp = (EditText) this.findViewById(R.id.editText_mdp);

        this.connect = (Button) this.findViewById(R.id.button_connect);
        this.inscrirepc = (Button) this.findViewById(R.id.button_inscrirepc);
        error_msg = findViewById(R.id.textView5);


        db = new DBHandler(this);


        this.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validUser = db.checkLogin(pseudo.getText().toString(), mdp.getText().toString());
                if(validUser == true) {
                    System.out.println(db.recupEquipe(pseudo.getText().toString()));

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("pseudo", pseudo.getText().toString());
                    intent.putExtra("teamid", db.recupEquipe(pseudo.getText().toString()));
                    startActivity(intent);
                    finish();
                }else {
                    error_msg.setText("Pseudo ou mot de passe incorrect");
                }

            }
        });

        this.inscrirepc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InscriptionActivity.class);
                startActivity(intent);
                finish();
                System.out.println("test pas encore de comtpe bv");
            }
        });
    }

    // Méthode mode avion
    private static boolean ModeAvionON(Context context) {
        return Settings.System.getInt(context.getContentResolver(),Settings.Global.AIRPLANE_MODE_ON,0)!=0;
    }
}