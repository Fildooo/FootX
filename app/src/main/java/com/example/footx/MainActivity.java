package com.example.footx;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
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
    private ImageView imageView, imageView3, iv1, iv2;
    private TextView form, mj1, mj2, mj3, mscore1, mscore2, mscore3, infosuser, equipepref, ligue,
            bp1,bp2, bp3, bc1, bc2, bc3, tv1, tv2;

    private TextView tv12,tv22,tv32,tv42,tv52,tv62,tv72,tv82,tv92,tv102,tv112,tv122,tv132;

    private ArrayList<String> TeamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View root = binding.getRoot();


        infosuser = findViewById(R.id.infosuser);


        String n1 = getIntent().getStringExtra("teamid");
        String n2 = getIntent().getStringExtra("pseudo");


        TeamID = new ArrayList<String>();

        if(n1 ==null && n2==null) {
            ConnexionActivity c = new ConnexionActivity();
            n1 = c.getStringteamid();
            n2 = c.getStringpseudo();
        }

        infosuser.setText("Bonjour "+n2);

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
            if(TeamID.get(i).equals(n1)) {
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
    public void ma(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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

       // ligue.setText(name_ligue+" - "+season_ligue);


        String logo_ligue = step2.getString("logo");
        //Picasso.get().load(logo_ligue).into(imageView3);

        JSONObject step3 = step1.getJSONObject("team");

        String name_team = step3.getString("name");
        //equipepref.setText(name_team);

        String logo_team = step3.getString("logo");
       // Picasso.get().load(logo_team).into(imageView);

        String histo_form = step1.getString("form");

        JSONObject step4 = step1.getJSONObject("fixtures");
        JSONObject step5 = step4.getJSONObject("played");

        String home_played = step5.getString("home");
        String away_played = step5.getString("away");
        String total_played = step5.getString("total");

        JSONObject step6 = step4.getJSONObject("wins");
        JSONObject step7 = step4.getJSONObject("draws");
        JSONObject step8 = step4.getJSONObject("loses");

        String victoires = step6.getString("total");
        String nul = step7.getString("total");
        String defaites = step8.getString("total");

        JSONObject step9 = step1.getJSONObject("goals");
        JSONObject step10 = step9.getJSONObject("for");
        JSONObject step11 = step10.getJSONObject("total");

        String home_but = step11.getString("home");
        String away_but = step11.getString("away");
        String total_but = step11.getString("total");

        JSONObject step12 = step9.getJSONObject("against");
        JSONObject step13 = step12.getJSONObject("total");

        String home_but_encaisse = step13.getString("home");
        String away_but_encaisse = step13.getString("away");
        String total_but_encaisse = step13.getString("total");





        TableLayout table = (TableLayout) root.findViewById(R.id.idEquipe);
        TableRow row1, row2, row3;

        row1 = new TableRow(getBaseContext());

        row2 = new TableRow(getBaseContext());

        row3 = new TableRow(getBaseContext());



        iv1 = new ImageView(getBaseContext());
        Picasso.get().load(logo_team).into(iv1);
        iv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv1 = new TextView(getBaseContext());
        tv1.setText(name_team);
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv2 = new TextView(getBaseContext());
        tv2.setText(name_ligue);
        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
        tv2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        iv2 = new ImageView(getBaseContext());
        Picasso.get().load(logo_ligue).into(iv2);
        iv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

        if (iv1.getParent() != null) {
            ((ViewGroup) iv1.getParent()).removeView(iv1); // <- fix
        }
        row1.addView(iv1);
        if (tv1.getParent() != null) {
            ((ViewGroup) tv1.getParent()).removeView(tv1); // <- fix
        }
        row2.addView(tv1);
        if (tv2.getParent() != null) {
            ((ViewGroup) tv2.getParent()).removeView(tv2); // <- fix
        }
        row3.addView(tv2);
        if (iv2.getParent() != null) {
            ((ViewGroup) iv2.getParent()).removeView(iv2); // <- fix
        }
        row3.addView(iv2);

        table.addView(row1);
        table.addView(row2);
        table.addView(row3);

        TableLayout table2 = (TableLayout) root.findViewById(R.id.stats);
        TableRow row12, row22, row32, row42, row52, row62, row72, row82, row92, row102, row112, row122, row132;

        row12 = new TableRow(getBaseContext());
        row22 = new TableRow(getBaseContext());
        row32 = new TableRow(getBaseContext());
        row42 = new TableRow(getBaseContext());
        row52 = new TableRow(getBaseContext());
        row62 = new TableRow(getBaseContext());
        row72 = new TableRow(getBaseContext());
        row82 = new TableRow(getBaseContext());
        row92 = new TableRow(getBaseContext());
        row102 = new TableRow(getBaseContext());
        row112 = new TableRow(getBaseContext());
        row122 = new TableRow(getBaseContext());
        row132 = new TableRow(getBaseContext());

        tv12 = new TextView(getBaseContext());
        tv12.setText(histo_form.substring(0,5));
        tv12.setTextColor(Color.WHITE);
        tv12.setGravity(Gravity.CENTER);
        tv12.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));


        tv22 = new TextView(getBaseContext());
        tv22.setText("Matchs à domicile "+home_played);
        tv22.setTextColor(Color.WHITE);
        tv22.setGravity(Gravity.CENTER);
        tv22.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv32 = new TextView(getBaseContext());
        tv32.setText("Matchs à l'éxterieur "+away_played);
        tv32.setTextColor(Color.WHITE);
        tv32.setGravity(Gravity.CENTER);
        tv32.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv42 = new TextView(getBaseContext());
        tv42.setText("Matchs totaux "+total_played);
        tv42.setTextColor(Color.WHITE);
        tv42.setGravity(Gravity.CENTER);
        tv42.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv52 = new TextView(getBaseContext());
        tv52.setText("Buts à domicile "+home_but);
        tv52.setTextColor(Color.WHITE);
        tv52.setGravity(Gravity.CENTER);
        tv52.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv62 = new TextView(getBaseContext());
        tv62.setText("Buts à l'éxterieur "+away_but);
        tv62.setTextColor(Color.WHITE);
        tv62.setGravity(Gravity.CENTER);
        tv62.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv72 = new TextView(getBaseContext());
        tv72.setText("Buts totaux "+total_but);
        tv72.setTextColor(Color.WHITE);
        tv72.setGravity(Gravity.CENTER);
        tv72.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv82 = new TextView(getBaseContext());
        tv82.setText("Victoires "+victoires);
        tv82.setTextColor(Color.WHITE);
        tv82.setGravity(Gravity.CENTER);
        tv82.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv92 = new TextView(getBaseContext());
        tv92.setText("Défaites "+defaites);
        tv92.setTextColor(Color.WHITE);
        tv92.setGravity(Gravity.CENTER);
        tv92.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv102 = new TextView(getBaseContext());
        tv102.setText("Nuls "+nul);
        tv102.setTextColor(Color.WHITE);
        tv102.setGravity(Gravity.CENTER);
        tv102.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv112 = new TextView(getBaseContext());
        tv112.setText("Encaissés à domicile "+home_but_encaisse);
        tv112.setTextColor(Color.WHITE);
        tv112.setGravity(Gravity.CENTER);
        tv112.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv122 = new TextView(getBaseContext());
        tv122.setText("Encaissés à l'éxterieur "+away_but_encaisse);
        tv122.setTextColor(Color.WHITE);
        tv122.setGravity(Gravity.CENTER);
        tv122.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        tv132 = new TextView(getBaseContext());
        tv132.setText("Encaissés totaux "+total_but_encaisse);
        tv132.setTextColor(Color.WHITE);
        tv132.setGravity(Gravity.CENTER);
        tv132.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        if (tv12.getParent() != null) {
            ((ViewGroup) tv12.getParent()).removeView(tv12); // <- fix
        }
        row12.addView(tv12);

        if (tv22.getParent() != null) {
            ((ViewGroup) tv22.getParent()).removeView(tv22); // <- fix
        }
        row22.addView(tv22);

        if (tv32.getParent() != null) {
            ((ViewGroup) tv32.getParent()).removeView(tv32); // <- fix
        }
        row32.addView(tv32);

        if (tv42.getParent() != null) {
            ((ViewGroup) tv42.getParent()).removeView(tv42); // <- fix
        }
        row42.addView(tv42);

        if (tv52.getParent() != null) {
            ((ViewGroup) tv52.getParent()).removeView(tv52); // <- fix
        }
        row52.addView(tv52);

        if (tv62.getParent() != null) {
            ((ViewGroup) tv62.getParent()).removeView(tv62); // <- fix
        }
        row62.addView(tv62);

        if (tv72.getParent() != null) {
            ((ViewGroup) tv72.getParent()).removeView(tv72); // <- fix
        }
        row72.addView(tv72);

        if (tv82.getParent() != null) {
            ((ViewGroup) tv82.getParent()).removeView(tv82); // <- fix
        }
        row82.addView(tv82);

        if (tv92.getParent() != null) {
            ((ViewGroup) tv92.getParent()).removeView(tv92); // <- fix
        }
        row92.addView(tv92);

        if (tv102.getParent() != null) {
            ((ViewGroup) tv102.getParent()).removeView(tv102); // <- fix
        }
        row102.addView(tv102);

        if (tv112.getParent() != null) {
            ((ViewGroup) tv112.getParent()).removeView(tv112); // <- fix
        }
        row112.addView(tv112);

        if (tv122.getParent() != null) {
            ((ViewGroup) tv122.getParent()).removeView(tv122); // <- fix
        }
        row122.addView(tv122);

        if (tv132.getParent() != null) {
            ((ViewGroup) tv132.getParent()).removeView(tv132); // <- fix
        }
        row132.addView(tv132);

        table2.addView(row12);
        table2.addView(row22);
        table2.addView(row32);
        table2.addView(row42);
        table2.addView(row52);
        table2.addView(row62);
        table2.addView(row72);
        table2.addView(row82);
        table2.addView(row92);
        table2.addView(row102);
        table2.addView(row112);
        table2.addView(row122);
        table2.addView(row132);




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