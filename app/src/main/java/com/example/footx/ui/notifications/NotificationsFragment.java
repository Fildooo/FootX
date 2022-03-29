package com.example.footx.ui.notifications;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.footx.R;
import com.example.footx.databinding.FragmentNotificationsBinding;
import com.example.footx.ui.dashboard.DashboardFragment;

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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private AutoCompleteTextView editQuest;
    private ArrayList<String> Team ;
    private ArrayList<Integer> TeamID;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        Button button = (Button) root.findViewById(R.id.buttonQuestion);
        editQuest = root.findViewById(R.id.editText1);
        Team = new ArrayList<String>();
        TeamID = new ArrayList<Integer>();





        try {
            String str1 = traitement("61");
            decodeJSON(str1, root);
            String str2 = traitement("39");
            decodeJSON(str2, root);
            String str3 = traitement("140");
            decodeJSON(str3, root);
            String str4 = traitement("78");
            decodeJSON(str4, root);
            String str5 = traitement("135");
            decodeJSON(str5, root);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(Team);

        LayoutInflater inflator = (LayoutInflater) getActivity().getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflator.inflate(R.layout.fragment_notifications, (ViewGroup) root);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                android.R.layout.simple_dropdown_item_1line, Team);
        AutoCompleteTextView textView = (AutoCompleteTextView) v
                .findViewById(R.id.editText1);
        textView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // System.out.println(editQuest.getText().toString());
                int i=0;
                System.out.println(Team.get(i));
                System.out.println(TeamID.get(i));
                System.out.println(editQuest.getText().toString());
                for(i=0;i<Team.size();i++){
                    if(Team.get(i).equals(editQuest.getText().toString())) {
                        TeamID.get(i);
                        System.out.println(TeamID.get(i));
                        int IdTeam = TeamID.get(i);
                        String IdT = TeamID.get(i).toString();
                        String IdLeague;
                        String IdL;
                        if(IdTeam<=19){
                            IdLeague = "61";
                            try {
                                traitementTeam(IdLeague,IdT);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (IdTeam>19 && IdTeam<=40){
                            IdLeague = "39";
                        }
                        else if (IdTeam>40 && IdTeam<=61){
                            IdLeague = "140";
                        }
                        else if (IdTeam>61 && IdTeam<=80){
                            IdLeague = "78";
                        }
                        else{
                            IdLeague = "135";
                        }
                    }

                }



            }
        });

        return root;
    }

    private static String requeteTeam(String id) {
        String response = "";
        try {
            HttpURLConnection connection = null;
            URL url = new URL("https://v3.football.api-sports.io/standings?league="+id+"&season=2021");
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

    private static String requeteTeamMatch(String IdLeague, String IdTeam) {
        String response = "";
        try {
            HttpURLConnection connection = null;
            URL url = new URL("https://v3.football.api-sports.io/fixtures?league="+IdLeague+"&season=2021&team="+IdTeam);
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



    public void decodeJSON(String str, View root) throws JSONException {



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
            return requeteTeamMatch(IdLeague,IdTeam);
        }

        protected void onPostExecute(String result) {
            System.out.println("response : "+result);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}