package com.example.footx;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.example.footx.ui.dashboard.DashboardFragment;
import com.example.footx.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.footx.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ImageView imageView, imageView3;
    private TextView form, mj1, mj2, mj3, mscore1, mscore2, mscore3, infosuser, equipepref, ligue,
            bp1,bp2, bp3, bc1, bc2, bc3;

    private ArrayList<String> TeamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View root = binding.getRoot();


        infosuser = findViewById(R.id.infosuser);
        infosuser.setText("Bonjour "+getIntent().getStringExtra("pseudo"));

        equipepref = findViewById(R.id.equipepref);
        imageView = findViewById(R.id.imageview);
        ligue = findViewById(R.id.ligue);
        imageView3 = findViewById(R.id.imageview3);
        form = findViewById(R.id.form);

        mj1 = findViewById(R.id.mj1);
        mj2 = findViewById(R.id.mj2);
        mj3 = findViewById(R.id.mj3);

        mscore1 = findViewById(R.id.mscore1);
        mscore2 = findViewById(R.id.mscore2);
        mscore3 = findViewById(R.id.mscore3);

        bp1 = findViewById(R.id.bp1);
        bp2 = findViewById(R.id.bp2);
        bp3 = findViewById(R.id.bp3);

        bc1 = findViewById(R.id.bc1);
        bc2 = findViewById(R.id.bc2);
        bc3 = findViewById(R.id.bc3);







        TeamID = new ArrayList<String>();

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
        System.out.println(TeamID);


        int i=0;
        for(i=0;i<TeamID.size();i++){
            if(TeamID.get(i).equals(getIntent().getStringExtra("teamid"))) {
                int IdTeam = i;
                String IdT = TeamID.get(i);
                String IdLeague;
                if(IdTeam<=19){
                    IdLeague = "61";
                    try {
                        String str = traitementTeam(IdLeague,IdT);
                        System.out.println(str);
                        decodeTeamMatchJSON(str,root);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if (IdTeam>19 && IdTeam<=39){
                    IdLeague = "39";
                    try {
                        String str = traitementTeam(IdLeague,IdT);
                        System.out.println(str);
                        decodeTeamMatchJSON(str,root);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (IdTeam>=40 && IdTeam<=59){
                    IdLeague = "140";
                    try {
                        String str = traitementTeam(IdLeague,IdT);
                        System.out.println(str);
                        decodeTeamMatchJSON(str,root);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (IdTeam>=60 && IdTeam<=77){
                    IdLeague = "78";
                    try {
                        String str = traitementTeam(IdLeague,IdT);
                        System.out.println(str);
                        decodeTeamMatchJSON(str,root);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    IdLeague = "135";
                    try {
                        String str = traitementTeam(IdLeague,IdT);
                        System.out.println(str);
                        decodeTeamMatchJSON(str,root);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }



        // Menu du bas
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
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
            String team_id = step6.getString("id");
            TeamID.add(team_id);


        }
    }

    private String traitement(String id) throws ExecutionException, InterruptedException {
        String req;
        DashboardFragment.RequestTask task = new DashboardFragment.RequestTask();
        req = task.execute(id).get();
        return req;
    }

    public void decodeTeamMatchJSON(String str, View root) throws JSONException {


// Ici décoder les infos de l'équipe
        JSONObject obj = new JSONObject(str);
        JSONObject step1 = obj.getJSONObject("response");
        JSONObject step2 = step1.getJSONObject("league");

        String name_ligue = step2.getString("name");
        String season_ligue = step2.getString("season");

        ligue.setText(name_ligue+" - "+season_ligue);


        String logo_ligue = step2.getString("logo");
        Picasso.get().load(logo_ligue).into(imageView3);

        JSONObject step3 = step1.getJSONObject("team");

        String name_team = step3.getString("name");
        equipepref.setText(name_team);

        String logo_team = step3.getString("logo");
        Picasso.get().load(logo_team).into(imageView);

        String histo_form = step1.getString("form");
        form.setText(histo_form.substring(0,5));

        JSONObject step4 = step1.getJSONObject("fixtures");
        JSONObject step5 = step4.getJSONObject("played");

        String home_played = step5.getString("home");
        mj1.setText("Matchs à domicile "+home_played);
        String away_played = step5.getString("away");
        mj2.setText("Matchs à l'éxterieur "+away_played);
        String total_played = step5.getString("total");
        mj3.setText("Matchs totaux "+total_played);

        JSONObject step6 = step4.getJSONObject("wins");
        JSONObject step7 = step4.getJSONObject("draws");
        JSONObject step8 = step4.getJSONObject("loses");

        String victoires = step6.getString("total");
        mscore1.setText("Victoires "+victoires);
        String nul = step7.getString("total");
        mscore2.setText("Nuls "+nul);
        String defaites = step8.getString("total");
        mscore3.setText("Défaites "+defaites);


        JSONObject step9 = step1.getJSONObject("goals");
        JSONObject step10 = step9.getJSONObject("for");
        JSONObject step11 = step10.getJSONObject("total");

        String home_but = step11.getString("home");
        bp1.setText("Buts à domicile "+home_but);
        String away_but = step11.getString("away");
        bp2.setText("Buts à l'éxterieur "+away_but);
        String total_but = step11.getString("total");
        bp3.setText("Buts totaux "+total_but);


        JSONObject step12 = step9.getJSONObject("against");
        JSONObject step13 = step12.getJSONObject("total");

        String home_but_encaisse = step13.getString("home");
        bc1.setText("Encaissés à domicile "+home_but_encaisse);
        String away_but_encaisse = step13.getString("away");
        bc2.setText("Encaissés à l'éxterieur "+away_but_encaisse);
        String total_but_encaisse = step13.getString("total");
        bc3.setText("Encaissés totaux "+total_but_encaisse);


    }

    private static String requeteTeamMatch(String IdLeague, String IdTeam) {
        String response = "";
        try {
            HttpURLConnection connection = null;
            URL url = new URL(" https://v3.football.api-sports.io/teams/statistics?league="+IdLeague+"&season=2021&team="+IdTeam);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.addRequestProperty("x-apisports-key", "fb0f3952c194ffdfeb0fcdd8ba320399");
            System.out.println(connection.getResponseCode());
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String ligne = bufferedReader.readLine() ;
            while (ligne!= null){
                response+=ligne;
                ligne = bufferedReader.readLine();
            }
            JSONObject toDecode = new JSONObject(response);
            //response = decodeJSON(toDecode);
        } catch (UnsupportedEncodingException e) {
            response = "problème d'encodage";
        } catch (MalformedURLException e) {
            response = "problème d'URL ";
        }  catch (Exception e) {
            e.printStackTrace();
        }


        return response;
    }


    private String traitementTeam(String IdLeague, String IdTeam) throws ExecutionException, InterruptedException {
        String req;
        ParamTask params = new ParamTask(IdLeague,IdTeam);
        RequestTask task = new RequestTask();
        req = task.execute(params).get();
        return req;

    }

    public static class ParamTask {
        String IdLeague;
        String IdTeam;

        ParamTask(String IdLeague, String IdTeam) {
            this.IdLeague = IdLeague;
            this.IdTeam = IdTeam;
        }
    }

    public static class RequestTask extends AsyncTask<ParamTask, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
        //  lance la requète
        protected String doInBackground(ParamTask... params) {
            String IdLeague = params[0].IdLeague;
            String IdTeam = params[0].IdTeam;
            String response = requeteTeamMatch(IdLeague, IdTeam);
            return response;
        }

        protected void onPostExecute(String result) {
            System.out.println("response : "+result);
        }
    }

    public void onDestroyView() {
        onDestroyView();
        binding = null;
    }

}