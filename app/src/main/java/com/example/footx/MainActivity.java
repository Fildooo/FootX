package com.example.footx;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.example.footx.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TextView tResultat;
    private EditText editVille;
    private Button bGo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tResultat = findViewById(R.id.tResultat);
        editVille = findViewById(R.id.editVille);
        bGo = findViewById(R.id.bGo);



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

    private void traitement(String ville) {
        RequestTask task = new RequestTask();
        task.execute(ville);
    }


    public void go(View view) {

        if (view.getId() == R.id.bGo) {
            traitement(editVille.getText().toString());
        }
    }

    private class RequestTask extends AsyncTask<String, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
        //  lance la requète
        protected String doInBackground(String... ville) {
            String response = requete(ville[0]);
            return response;
        }

        // Méthode appelée lorsque la tâche de fond sera terminée
//  Affiche le résultat
        protected void onPostExecute(String result) {
            System.out.println("response : "+result);
            tResultat.setText(result);
        }
    }

    private String requete(String ville) {


        String response = "";
        try {
            HttpURLConnection connection = null;
            URL url = new URL("https://v3.football.api-sports.io/fixtures?league=1&season=2018&team="+ URLEncoder.encode(ville,"utf-8"));
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
            response = decodeJSON(toDecode);
        } catch (UnsupportedEncodingException e) {
            response = "problème d'encodage";
        } catch (MalformedURLException e) {
            response = "problème d'URL ";
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }



    private String decodeJSON(JSONObject jso) throws Exception {
        String response = "";
            JSONObject jsostatuts = jso.getJSONObject("home");
            JSONObject jsototalresults = jso.getJSONObject("totalResults");
            JSONArray jsoarticles = jso.getJSONArray("articles");
            response = "\n  " + jso.getString("category");

            for (int i = 0; i < jsoarticles.length(); i++) {
                response += jsoarticles.getJSONObject(i).getString("description")+ " ";
            }

        return response;
    }


}