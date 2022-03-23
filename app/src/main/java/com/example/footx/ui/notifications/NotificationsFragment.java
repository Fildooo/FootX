package com.example.footx.ui.notifications;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.footx.R;
import com.example.footx.databinding.FragmentNotificationsBinding;
import com.example.footx.ui.dashboard.DashboardFragment;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;


public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private AutoCompleteTextView editQuest;
    private ArrayList<String> Team ;


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
        Collections.sort(Team);
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
                System.out.println(editQuest.getText().toString());
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
            Team.add(team_name);


        }
    }

    private String traitement(String id) throws ExecutionException, InterruptedException {
        String req;
        DashboardFragment.RequestTask task = new DashboardFragment.RequestTask();
        req = task.execute(id).get();
        return req;
    }

    public static class RequestTask extends AsyncTask<String, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
        //  lance la requète
        protected String doInBackground(String... id) {
            String response = requeteTeam(id[0]);
            return response;
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