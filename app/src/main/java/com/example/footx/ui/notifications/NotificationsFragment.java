package com.example.footx.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.footx.DetailsMatch;
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
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;


public class NotificationsFragment extends Fragment {

    private static String DATE;
    private static String STADE_NAME;
    private static String STADE_VILLE;
    private static String ARBITRE;
    private static String STATUT;
    private static String HOME_TEAM;
    private static String AWAY_TEAM;
    private static String HOME_LOGO;
    private static String AWAY_LOGO;
    private static String HOME_GOAL;
    private static String AWAY_GOAL;
    private static String HOME_HALFTIME;
    private static String AWAY_HALFTIME;
    private static String HOME_PENALTY;
    private static String AWAY_PENALTY;

    public static String getStadeName() {
        return STADE_NAME;
    }

    public static void setStadeName(String stadeName) {
        STADE_NAME = stadeName;
    }

    public static String getStadeVille() {
        return STADE_VILLE;
    }

    public static void setStadeVille(String stadeVille) {
        STADE_VILLE = stadeVille;
    }

    public static String getARBITRE() {
        return ARBITRE;
    }

    public static void setARBITRE(String ARBITRE) {
        NotificationsFragment.ARBITRE = ARBITRE;
    }

    public static String getSTATUT() {
        return STATUT;
    }

    public static void setSTATUT(String STATUT) {
        NotificationsFragment.STATUT = STATUT;
    }

    public static String getHomeTeam() {
        return HOME_TEAM;
    }

    public static void setHomeTeam(String homeTeam) {
        HOME_TEAM = homeTeam;
    }

    public static String getAwayTeam() {
        return AWAY_TEAM;
    }

    public static void setAwayTeam(String awayTeam) {
        AWAY_TEAM = awayTeam;
    }

    public static String getHomeLogo() {
        return HOME_LOGO;
    }

    public static void setHomeLogo(String homeLogo) {
        HOME_LOGO = homeLogo;
    }

    public static String getAwayLogo() {
        return AWAY_LOGO;
    }

    public static void setAwayLogo(String awayLogo) {
        AWAY_LOGO = awayLogo;
    }

    public static String getHomeGoal() {
        return HOME_GOAL;
    }

    public static void setHomeGoal(String homeGoal) {
        HOME_GOAL = homeGoal;
    }

    public static String getAwayGoal() {
        return AWAY_GOAL;
    }

    public static void setAwayGoal(String awayGoal) {
        AWAY_GOAL = awayGoal;
    }

    public static String getHomeHalftime() {
        return HOME_HALFTIME;
    }

    public static void setHomeHalftime(String homeHalftime) {
        HOME_HALFTIME = homeHalftime;
    }

    public static String getAwayHalftime() {
        return AWAY_HALFTIME;
    }

    public static void setAwayHalftime(String awayHalftime) {
        AWAY_HALFTIME = awayHalftime;
    }

    public static String getHomePenalty() {
        return HOME_PENALTY;
    }

    public static void setHomePenalty(String homePenalty) {
        HOME_PENALTY = homePenalty;
    }

    public static String getAwayPenalty() {
        return AWAY_PENALTY;
    }

    public static void setAwayPenalty(String awayPenalty) {
        AWAY_PENALTY = awayPenalty;
    }

    private FragmentNotificationsBinding binding;
    private AutoCompleteTextView editQuest;
    private ArrayList<String> Team ;
    private ArrayList<Integer> TeamID;

    TextView tv1,tv2,tv3, d1, stade1, nu, nu1;
    ImageView iv1, iv2;
    Button btnInfo;



    public static String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        Button button = (Button) root.findViewById(R.id.buttonQuestion);
        editQuest = root.findViewById(R.id.editText1);
        editQuest.setTextColor(Color.WHITE);
        Team = new ArrayList<String>();
        TeamID = new ArrayList<Integer>();





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

        LayoutInflater inflator = (LayoutInflater) getActivity().getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflator.inflate(R.layout.fragment_notifications, (ViewGroup) root);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
               R.layout.custom_autocomplete, Team);
        AutoCompleteTextView textView = (AutoCompleteTextView) v
                .findViewById(R.id.editText1);
        textView.setAdapter(adapter);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // System.out.println(editQuest.getText().toString());
                int i=0;
                for(i=0;i<Team.size();i++){
                    if(Team.get(i).equals(editQuest.getText().toString())) {
                        int IdTeam = i;
                        String IdT = TeamID.get(i).toString();
                        String IdLeague;
                        if(IdTeam<=19){
                            IdLeague = "61";
                            try {
                                String str = traitementTeam(IdLeague,IdT);
                                decodeTeamMatchJSON(str,root);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (IdTeam>19 && IdTeam<=39){
                            IdLeague = "39";
                            try {
                                String str = traitementTeam(IdLeague,IdT);
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
            }

        });
       
        return root;
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

    public void decodeTeamMatchJSON(String str, View root) throws JSONException {


        // Ici décoder les matchs de la requête de chaque équipe
        TableLayout table = (TableLayout) root.findViewById(R.id.idTable);
        TableLayout table1 = (TableLayout) root.findViewById(R.id.idTable1);
        TableRow row, row2, row3, row4, rowSep, rowSep1, rowText;

        int count1 = table.getChildCount();
        for(int f=0; f<count1; f++) {
            View child = table.getChildAt(f);
            if(child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        int count2 = table1.getChildCount();
        for(int f=0; f<count2; f++) {
            View child = table1.getChildAt(f);
            if(child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        JSONObject obj = new JSONObject(str);
        JSONArray step1 = obj.getJSONArray("response");

        rowSep = new TableRow(getActivity().getBaseContext());
        rowSep.setBackgroundResource(R.drawable.background_row2);

        final String nul = "    ";


        nu = new TextView(getActivity().getBaseContext());
        nu.setText(nul);
        nu.setTextSize(1,1);
        nu.setTextColor(Color.WHITE);
        nu.setGravity(Gravity.CENTER);
        nu.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));


        if (nu.getParent() != null) {
            ((ViewGroup) nu.getParent()).removeView(nu); // <- fix
        }
        rowSep.addView(nu);
        table.addView(rowSep);

        TextView Prochain = (TextView) root.findViewById(R.id.prochain);
        Prochain.setText("Prochain match : ");
        TextView Historique = (TextView) root.findViewById(R.id.listmatch);
        Historique.setText("Historique des matchs: ");

        int k = 0;
        for (int i = step1.length()-1; i >= 0; i--) {


            JSONObject step2 = step1.getJSONObject(i);
            JSONObject step3 = step2.getJSONObject("fixture");

            // Date du Match et arbitre
            String date = step3.getString("date");
            String arbitre = step3.getString("referee");

            String[] date1 = date.split("T");


            // Stade et lieu du stade
            JSONObject step4 = step3.getJSONObject("venue");
            String stade_name = step4.getString("name");
            String stade_ville = step4.getString("city");


            // Statut du match
            JSONObject step5 = step3.getJSONObject("status");
            String statut = step5.getString("long");
            String temps = step5.getString("elapsed");




            // Nom des teams
            JSONObject step6 = step2.getJSONObject("teams");
            JSONObject step7 = step6.getJSONObject("home");
            String home_team = step7.getString("name");
            String home_team_logo = step7.getString("logo");
            JSONObject step8 = step6.getJSONObject("away");
            String away_team = step8.getString("name");
            String away_team_logo = step8.getString("logo");


            // Les buts
            JSONObject step9 = step2.getJSONObject("goals");
            String home_goal = step9.getString("home");
            String away_goal = step9.getString("away");
            JSONObject step10 = step2.getJSONObject("score");
            JSONObject step11 = step10.getJSONObject("halftime");
            String home_halfgoal = step11.getString("home");
            String away_halfgoal = step11.getString("away");
            JSONObject step12 = step10.getJSONObject("penalty");
            String home_penogoal = step11.getString("home");
            String away_penogoal = step11.getString("away");
            if(k==0){
                if(statut.equals("Not Started")){
                    JSONObject step2bis = step1.getJSONObject(i-1);
                    JSONObject step3bis = step2bis.getJSONObject("fixture");
                    JSONObject step5bis = step3bis.getJSONObject("status");
                    String statutbis = step5bis.getString("long");

                    if(statutbis.equals("Match Finished")){
                        k++;
                        final String col1 = home_team;
                        final String col2 = " - ";
                        final String col3 = away_team;
                        final String colivhome = home_team_logo;
                        final String colivaway = away_team_logo;
                        final String coldate = date1[0];
                        final String stade = stade_name;
                        final String nulll = "    ";



                        row = new TableRow(getActivity().getBaseContext());

                        row2 = new TableRow(getActivity().getBaseContext());

                        row3 = new TableRow(getActivity().getBaseContext());

                        row4 = new TableRow(getActivity().getBaseContext());

                        rowSep = new TableRow(getActivity().getBaseContext());
                        rowSep.setBackgroundResource(R.drawable.background_row2);

                        rowSep1 = new TableRow(getActivity().getBaseContext());
                        rowSep1.setBackgroundResource(R.drawable.background_row2);





                        nu1 = new TextView(getActivity().getBaseContext());
                        nu1.setText(nulll);
                        nu1.setTextSize(1,1);
                        nu1.setTextColor(Color.WHITE);
                        nu1.setGravity(Gravity.CENTER);
                        nu1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                        if (nu1.getParent() != null) {
                            ((ViewGroup) nu1.getParent()).removeView(nu1); // <- fix
                        }
                        rowSep1.addView(nu1);
                        table1.addView(rowSep1);

                        d1 = new TextView(getActivity().getBaseContext());
                        d1.setText(coldate);
                        d1.setTextColor(Color.WHITE);
                        d1.setGravity(Gravity.CENTER);
                        d1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

                        tv1 = new TextView(getActivity().getBaseContext());
                        tv1.setText(col1);
                        tv1.setTextColor(Color.WHITE);
                        tv1.setGravity(Gravity.CENTER);
                        tv1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

                        iv1 = new ImageView(getActivity().getBaseContext());
                        Picasso.get().load(colivhome).into(iv1);
                        iv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                        tv2 = new TextView(getActivity().getBaseContext());
                        tv2.setText(col2);
                        tv2.setTypeface(Typeface.DEFAULT_BOLD);
                        tv2.setTextColor(Color.WHITE);
                        tv2.setGravity(Gravity.CENTER);
                        tv2.setSingleLine();
                        tv2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

                        iv2 = new ImageView(getActivity().getBaseContext());
                        Picasso.get().load(colivaway).into(iv2);
                        iv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                        tv3 = new TextView(getActivity().getBaseContext());
                        tv3.setText(col3);
                        tv3.setTextColor(Color.WHITE);
                        tv3.setGravity(Gravity.CENTER);
                        tv3.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                        stade1 = new TextView(getActivity().getBaseContext());
                        stade1.setText(stade);
                        stade1.setTextColor(Color.WHITE);
                        stade1.setGravity(Gravity.CENTER);
                        stade1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                        nu = new TextView(getActivity().getBaseContext());
                        nu.setText(nulll);
                        nu.setTextSize(1,1);
                        nu.setTextColor(Color.WHITE);
                        nu.setGravity(Gravity.CENTER);
                        nu.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));



                        if (d1.getParent() != null) {
                            ((ViewGroup) d1.getParent()).removeView(d1); // <- fix
                        }
                        row2.addView(d1);
                        if (tv1.getParent() != null) {
                            ((ViewGroup) tv1.getParent()).removeView(tv1); // <- fix
                        }
                        row.addView(tv1);
                        if (iv1.getParent() != null) {
                            ((ViewGroup) iv1.getParent()).removeView(iv1); // <- fix
                        }
                        row.addView(iv1);
                        if (tv2.getParent() != null) {
                            ((ViewGroup) tv2.getParent()).removeView(tv2); // <- fix
                        }
                        row.addView(tv2);
                        if (iv2.getParent() != null) {
                            ((ViewGroup) iv2.getParent()).removeView(iv2); // <- fix
                        }
                        row.addView(iv2);
                        if (tv3.getParent() != null) {
                            ((ViewGroup) tv3.getParent()).removeView(tv3); // <- fix
                        }
                        row.addView(tv3);
                        if (stade1.getParent() != null) {
                            ((ViewGroup) stade1.getParent()).removeView(stade1); // <- fix
                        }
                        row3.addView(stade1);
                        if (nu.getParent() != null) {
                            ((ViewGroup) nu.getParent()).removeView(nu); // <- fix
                        }
                        rowSep.addView(nu);


                        table1.addView(row2);
                        table1.addView(row);
                        table1.addView(row3);
                        table1.addView(row4);
                        table1.addView(rowSep);


                    }
                }
            }





            if(statut.equals("Match Finished")){
                final String col1 = home_team;
                final String col2 = home_goal+" - "+away_goal;
                final String col3 = away_team;
                final String colivhome = home_team_logo;
                final String colivaway = away_team_logo;
                final String coldate = date1[0];
                final String stade = stade_name;
                final String nulll = "    ";

                System.out.println(col2);

                row = new TableRow(getActivity().getBaseContext());

                row2 = new TableRow(getActivity().getBaseContext());

                row3 = new TableRow(getActivity().getBaseContext());

                row4 = new TableRow(getActivity().getBaseContext());

                rowSep = new TableRow(getActivity().getBaseContext());
                rowSep.setBackgroundResource(R.drawable.background_row2);

                d1 = new TextView(getActivity().getBaseContext());
                d1.setText(coldate);
                d1.setTextColor(Color.WHITE);
                d1.setGravity(Gravity.CENTER);
                d1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

                tv1 = new TextView(getActivity().getBaseContext());
                tv1.setText(col1);
                tv1.setTextColor(Color.WHITE);
                tv1.setGravity(Gravity.CENTER);
                tv1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

                iv1 = new ImageView(getActivity().getBaseContext());
                Picasso.get().load(colivhome).into(iv1);
                iv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                tv2 = new TextView(getActivity().getBaseContext());
                tv2.setText(col2);
                tv2.setTypeface(Typeface.DEFAULT_BOLD);
                tv2.setTextColor(Color.WHITE);
                tv2.setGravity(Gravity.CENTER);
                tv2.setSingleLine();
                tv2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));

                iv2 = new ImageView(getActivity().getBaseContext());
                Picasso.get().load(colivaway).into(iv2);
                iv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                tv3 = new TextView(getActivity().getBaseContext());
                tv3.setText(col3);
                tv3.setTextColor(Color.WHITE);
                tv3.setGravity(Gravity.CENTER);
                tv3.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                stade1 = new TextView(getActivity().getBaseContext());
                stade1.setText(stade);
                stade1.setTextColor(Color.WHITE);
                stade1.setGravity(Gravity.CENTER);
                stade1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                nu = new TextView(getActivity().getBaseContext());
                nu.setText(nulll);
                nu.setTextSize(1,1);
                nu.setTextColor(Color.WHITE);
                nu.setGravity(Gravity.CENTER);
                nu.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));

                btnInfo = new Button(getActivity().getBaseContext());
                btnInfo.setText("Détails");
                btnInfo.setTextColor(Color.WHITE);
                btnInfo.setGravity(Gravity.CENTER);
                btnInfo.setBackgroundResource(R.drawable.custom_button1);
                btnInfo.setPadding(10,15,10,15);
                btnInfo.setOnClickListener(new View.OnClickListener() {
                                               public void onClick(View v) {
                                                   DATE = date1[0];
                                                   STADE_NAME = stade_name;
                                                   STADE_VILLE = stade_ville;
                                                   ARBITRE = arbitre;
                                                   STATUT = statut;
                                                   HOME_TEAM = home_team;
                                                   AWAY_TEAM = away_team;
                                                   HOME_LOGO = home_team_logo;
                                                   AWAY_LOGO = away_team_logo;
                                                   HOME_GOAL = home_goal;
                                                   AWAY_GOAL = away_goal;
                                                   HOME_HALFTIME = home_halfgoal;
                                                   AWAY_HALFTIME = away_halfgoal;
                                                   HOME_PENALTY = home_penogoal;
                                                   AWAY_PENALTY = away_penogoal;


                                                   Intent intent = new Intent(getActivity(), DetailsMatch.class);
                                                   startActivity(intent);

                                               }
                                           });

                btnInfo.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));


                if (d1.getParent() != null) {
                    ((ViewGroup) d1.getParent()).removeView(d1); // <- fix
                }
                row2.addView(d1);
                if (tv1.getParent() != null) {
                    ((ViewGroup) tv1.getParent()).removeView(tv1); // <- fix
                }
                row.addView(tv1);
                if (iv1.getParent() != null) {
                    ((ViewGroup) iv1.getParent()).removeView(iv1); // <- fix
                }
                row.addView(iv1);
                if (tv2.getParent() != null) {
                    ((ViewGroup) tv2.getParent()).removeView(tv2); // <- fix
                }
                row.addView(tv2);
                if (iv2.getParent() != null) {
                    ((ViewGroup) iv2.getParent()).removeView(iv2); // <- fix
                }
                row.addView(iv2);
                if (tv3.getParent() != null) {
                    ((ViewGroup) tv3.getParent()).removeView(tv3); // <- fix
                }
                row.addView(tv3);
                if (stade1.getParent() != null) {
                    ((ViewGroup) stade1.getParent()).removeView(stade1); // <- fix
                }
                row3.addView(stade1);
                if (btnInfo.getParent() != null) {
                    ((ViewGroup) btnInfo.getParent()).removeView(btnInfo); // <- fix
                }
                row4.addView(btnInfo);
                if (nu.getParent() != null) {
                    ((ViewGroup) nu.getParent()).removeView(nu); // <- fix
                }
                rowSep.addView(nu);


                table.addView(row2);
                table.addView(row);
                table.addView(row3);
                table.addView(row4);
                table.addView(rowSep);


            }


        }




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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}