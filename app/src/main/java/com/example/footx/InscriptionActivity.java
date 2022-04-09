package com.example.footx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.footx.DAO.DBHandler;
import com.example.footx.databinding.ActivityInscriptionBinding;
import com.example.footx.ui.dashboard.DashboardFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class InscriptionActivity extends AppCompatActivity {

    private ActivityInscriptionBinding binding;
    private EditText pseudo;
    private EditText mdp;
    private EditText mdp2;
    private Button inscrip;
    private Button connexionpc;

    DBHandler db;

    private AutoCompleteTextView autocompletev;
    private ArrayList<String> Team ;
    private ArrayList<Integer> TeamID;
    private TextView error_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(binding.getRoot());
        this.pseudo = (EditText) this.findViewById(R.id.editText_pseudoi);
        this.mdp = (EditText) this.findViewById(R.id.editText_mdpi);
        this.mdp2 = (EditText) this.findViewById(R.id.editText_mdpi2);
        db = new DBHandler(this);

        Team = new ArrayList<String>();
        TeamID = new ArrayList<Integer>();

        autocompletev = findViewById(R.id.autocompletev);
        error_msg = findViewById(R.id.textView5);

        try {
            String str1 = traitement("61");
            decodeTeamJSON(str1, root);
            String str2 = traitement("39");
            decodeTeamJSON(str2, root);
            String str3 = traitement("140");
            decodeTeamJSON(str3, root);
            String str4 = traitement("78");
            decodeTeamJSON(str4, root);
            String str5 = traitement("135");
            decodeTeamJSON(str5, root);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(Team);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_dropdown_item_1line, Team);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocompletev);
        textView.setAdapter(adapter);


        this.inscrip = (Button) this.findViewById(R.id.button_inscrip);
        this.connexionpc = (Button) this.findViewById(R.id.button_connexionpc);

        this.inscrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mdp.getText().toString().equals(mdp2.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                    startActivity(intent);
                    finish();
                    for(int i=0;i<Team.size();i++) {
                        if(Team.get(i).equals(autocompletev.getText().toString())) {
                            String p = pseudo.getText().toString();
                            String m = mdp.getText().toString();
                            String IdT = TeamID.get(i).toString();
                            db.insertUser(p,m,IdT);
                            System.out.println(IdT);
                        }
                    }
                }else {
                    error_msg.setText("Les mots de passes ne correspondent pas");
                }
            }
        });

        this.connexionpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ConnexionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void decodeTeamJSON(String str, View root) throws JSONException {



        // Decode
        JSONObject obj = new JSONObject(str);
        JSONArray infosLeague = obj.getJSONArray("response");
        JSONObject step1 = infosLeague.getJSONObject(0);
        JSONObject step2 = step1.getJSONObject("league");
        JSONArray step3 = step2.getJSONArray("standings");
        JSONArray step4 = step3.getJSONArray(0);



        for (int i = 0; i < step4.length(); i++) {
            JSONObject step5 = step4.getJSONObject(i);


            // Team name
            JSONObject step6 = step5.getJSONObject("team");
            String team_name = step6.getString("name");
            int team_id = step6.getInt("id");
            TeamID.add(team_id);
            Team.add(team_name);


        }
    }

    private String traitement(String id) throws ExecutionException, InterruptedException {
        String req;
        DashboardFragment.RequestTask task = new DashboardFragment.RequestTask();
        req = task.execute(id).get();
        return req;
    }


}