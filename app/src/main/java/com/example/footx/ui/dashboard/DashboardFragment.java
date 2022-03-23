package com.example.footx.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.footx.databinding.FragmentDashboardBinding;
import com.example.footx.databinding.FragmentHomeBinding;
import com.example.footx.ui.home.HomeViewModel;
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
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;
    ImageView iv1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Spinner spinner = (Spinner) root.findViewById(R.id.classement_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.classement_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String str = null;

                System.out.println(parentView.getParent());
                String t= (String) parentView.getItemAtPosition(position);
                switch (t) {
                    case "Ligue 1":
                        try {
                            str = traitement("61");
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            decodeJSON(str, root);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                        break;
                    case "Premier league":
                        try {
                            str = traitement("39");
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            decodeJSON(str, root);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                        break;
                    case "Liga":
                        try {
                            str = traitement("140");
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            decodeJSON(str, root);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                        break;
                    case "Bundesliga":
                        try {
                            str = traitement("78");
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            decodeJSON(str, root);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                        break;
                    case "Serie A":
                        try {
                            str = traitement("135");
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            decodeJSON(str, root);
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                        break;
                }

                /**
                 if (parentView.getPositionForView(selectedItemView) == 0) {
                 try {
                 str = traitement("61");
                 } catch (ExecutionException | InterruptedException e) {
                 e.printStackTrace();
                 }
                 System.out.println("requete : "+str);

                 try {
                 decodeJSON(str, root);
                 } catch (JSONException jsonException) {
                 jsonException.printStackTrace();
                 }


                 }else if (parentView.getPositionForView(selectedItemView) == 1) {
                 // traitement("39");

                 try {
                 str = traitement("39");
                 } catch (ExecutionException | InterruptedException e) {
                 e.printStackTrace();
                 }
                 System.out.println("requete : "+str);

                 try {
                 decodeJSON(str, root);
                 } catch (JSONException jsonException) {
                 jsonException.printStackTrace();
                 }
                 }else if (parentView.getPositionForView(selectedItemView) == 2) {
                 // traitement("140");
                 try {
                 str = traitement("140");
                 } catch (ExecutionException | InterruptedException e) {
                 e.printStackTrace();
                 }
                 System.out.println("requete : "+str);

                 try {
                 decodeJSON(str, root);
                 } catch (JSONException jsonException) {
                 jsonException.printStackTrace();
                 }
                 }else if (parentView.getPositionForView(selectedItemView) == 3) {
                 //  traitement("78");
                 try {
                 str =  traitement("78");
                 } catch (ExecutionException | InterruptedException e) {
                 e.printStackTrace();
                 }
                 System.out.println("requete : "+str);

                 try {
                 decodeJSON(str, root);
                 } catch (JSONException jsonException) {
                 jsonException.printStackTrace();
                 }
                 }else if (parentView.getPositionForView(selectedItemView) == 4) {
                 //  traitement("135");
                 try {
                 str = traitement("135");
                 } catch (ExecutionException | InterruptedException e) {
                 e.printStackTrace();
                 }
                 System.out.println("requete : "+str);

                 try {
                 decodeJSON(str, root);
                 } catch (JSONException jsonException) {
                 jsonException.printStackTrace();
                 }
                 }else {
                 System.out.println("test");
                 }
                 **/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        return root;
    }

    public void decodeJSON(String str, View root) throws JSONException {


        TableLayout table = (TableLayout) root.findViewById(R.id.idTable);
        TableRow row;
        // Decode
        JSONObject obj = new JSONObject(str);
        JSONArray infosLeague = obj.getJSONArray("response");
        JSONObject step1 = infosLeague.getJSONObject(0);
        JSONObject step2 = step1.getJSONObject("league");
        JSONArray step3 = step2.getJSONArray("standings");
        JSONArray step4 = step3.getJSONArray(0);

        int count = table.getChildCount();
        for(int f=0; f<count; f++) {
            View child = table.getChildAt(f);
            if(child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }

        for (int i = 0; i < step4.length(); i++) {
            JSONObject step5 = step4.getJSONObject(i);

            // Rank
            String rank = step5.getString("rank");

            // Team name
            JSONObject step6 = step5.getJSONObject("team");
            String team_name = step6.getString("name");
            String team_logo = step6.getString("logo");

            // Points
            String points = step5.getString("points");
            String goalsDiff = step5.getString("goalsDiff");


            // J G N P
            JSONObject step7 = step5.getJSONObject("all");
            String j = step7.getString("played");
            String g = step7.getString("win");
            String n = step7.getString("draw");
            String p = step7.getString("lose");


            //BP BC
            JSONObject step8 = step7.getJSONObject("goals");
            String bp = step8.getString("for");
            String bc = step8.getString("against");


            System.out.println(rank + " " + team_logo + " " + team_name + " " + j + " " + g + " " + n + " " + p + " " + bp + " " + bc + " " + goalsDiff +
                    " " + points);


            final String col1 = rank;
            final String col2 = team_name;
            final String col3 = j;
            final String col4 = g;
            final String col5 = n;
            final String col6 = p;
            final String col7 = bp;
            final String col8 = bc;
            final String col9 = goalsDiff;
            final String col10 = points;
            final String coliv = team_logo;


            row = new TableRow(getActivity().getBaseContext());

            tv1 = new TextView(getActivity().getBaseContext());
            tv1.setText(col1);
            tv1.setGravity(Gravity.CENTER);
            tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            iv1 = new ImageView(getActivity().getBaseContext());
            Picasso.get().load(coliv).into(iv1);
            iv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv2 = new TextView(getActivity().getBaseContext());
            tv2.setText(col2);
            tv2.setGravity(Gravity.CENTER);
            tv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv3 = new TextView(getActivity().getBaseContext());
            tv3.setText(col3);
            tv3.setGravity(Gravity.CENTER);
            tv3.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv4 = new TextView(getActivity().getBaseContext());
            tv4.setText(col4);
            tv4.setGravity(Gravity.CENTER);
            tv4.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv5 = new TextView(getActivity().getBaseContext());
            tv5.setText(col5);
            tv5.setGravity(Gravity.CENTER);
            tv5.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv6 = new TextView(getActivity().getBaseContext());
            tv6.setText(col6);
            tv6.setGravity(Gravity.CENTER);
            tv6.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv7 = new TextView(getActivity().getBaseContext());
            tv7.setText(col7);
            tv7.setGravity(Gravity.CENTER);
            tv7.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv8 = new TextView(getActivity().getBaseContext());
            tv8.setText(col8);
            tv8.setGravity(Gravity.CENTER);
            tv8.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv9 = new TextView(getActivity().getBaseContext());
            tv9.setText(col9);
            tv9.setGravity(Gravity.CENTER);
            tv9.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

            tv10 = new TextView(getActivity().getBaseContext());
            tv10.setText(col10);
            tv10.setGravity(Gravity.CENTER);
            tv10.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

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
            if (tv3.getParent() != null) {
                ((ViewGroup) tv3.getParent()).removeView(tv3); // <- fix
            }
            row.addView(tv3);
            if (tv4.getParent() != null) {
                ((ViewGroup) tv4.getParent()).removeView(tv4); // <- fix
            }
            row.addView(tv4);
            if (tv5.getParent() != null) {
                ((ViewGroup) tv5.getParent()).removeView(tv5); // <- fix
            }
            row.addView(tv5);
            if (tv6.getParent() != null) {
                ((ViewGroup) tv6.getParent()).removeView(tv6); // <- fix
            }
            row.addView(tv6);
            if (tv7.getParent() != null) {
                ((ViewGroup) tv7.getParent()).removeView(tv7); // <- fix
            }
            row.addView(tv7);
            if (tv8.getParent() != null) {
                ((ViewGroup) tv8.getParent()).removeView(tv8); // <- fix
            }
            row.addView(tv8);
            if (tv9.getParent() != null) {
                ((ViewGroup) tv9.getParent()).removeView(tv9); // <- fix
            }
            row.addView(tv9);
            if (tv10.getParent() != null) {
                ((ViewGroup) tv10.getParent()).removeView(tv10); // <- fix
            }
            row.addView(tv10);

            if (row.getParent() != null) {
                ((ViewGroup) row.getParent()).removeView(row);
            }
            table.addView(row);

        }
    }

    private static String requeteLeague(String id) {
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
            String response = requeteLeague(id[0]);
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




/**

 Spinner spinner = (Spinner) root.findViewById(R.id.classement_spinner);
 ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
 R.array.classement_array, android.R.layout.simple_spinner_item);
 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 spinner.setAdapter(adapter);

 spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
@Override
public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
String str = null;
if (parentView.getPositionForView(selectedItemView) == 0) {
try {
str = traitement("61");
} catch (ExecutionException | InterruptedException e) {
e.printStackTrace();
}
System.out.println("requete : "+str);

try {
decodeJSON(str, root);
} catch (JSONException jsonException) {
jsonException.printStackTrace();
}


}else if (parentView.getPositionForView(selectedItemView) == 1) {
// traitement("39");

try {
str = traitement("39");
} catch (ExecutionException | InterruptedException e) {
e.printStackTrace();
}
System.out.println("requete : "+str);

try {
decodeJSON(str, root);
} catch (JSONException jsonException) {
jsonException.printStackTrace();
}
}else if (parentView.getPositionForView(selectedItemView) == 2) {
// traitement("140");
try {
str = traitement("140");
} catch (ExecutionException | InterruptedException e) {
e.printStackTrace();
}
System.out.println("requete : "+str);

try {
decodeJSON(str, root);
} catch (JSONException jsonException) {
jsonException.printStackTrace();
}
}else if (parentView.getPositionForView(selectedItemView) == 3) {
//  traitement("78");
try {
str =  traitement("78");
} catch (ExecutionException | InterruptedException e) {
e.printStackTrace();
}
System.out.println("requete : "+str);

try {
decodeJSON(str, root);
} catch (JSONException jsonException) {
jsonException.printStackTrace();
}
}else if (parentView.getPositionForView(selectedItemView) == 4) {
//  traitement("135");
try {
str = traitement("135");
} catch (ExecutionException | InterruptedException e) {
e.printStackTrace();
}
System.out.println("requete : "+str);

try {
decodeJSON(str, root);
} catch (JSONException jsonException) {
jsonException.printStackTrace();
}
}

}

@Override
public void onNothingSelected(AdapterView<?> parentView) {

}

});

 return root;
 }

 public void decodeJSON(String str, View root) throws JSONException {


 TableLayout table = (TableLayout) root.findViewById(R.id.idTable);
 TableRow row;
 // Decode
 JSONObject obj = new JSONObject(str);
 JSONArray infosLeague = obj.getJSONArray("response");
 JSONObject step1 = infosLeague.getJSONObject(0);
 JSONObject step2 = step1.getJSONObject("league");
 JSONArray step3 = step2.getJSONArray("standings");
 JSONArray step4 = step3.getJSONArray(0);

 int count = table.getChildCount();
 for(int f=0; f<count; f++) {
 View child = table.getChildAt(f);
 if(child instanceof TableRow) ((ViewGroup) child).removeAllViews();
 }

 for (int i = 0; i < step4.length(); i++) {
 JSONObject step5 = step4.getJSONObject(i);

 // Rank
 String rank = step5.getString("rank");

 // Team name
 JSONObject step6 = step5.getJSONObject("team");
 String team_name = step6.getString("name");
 String team_logo = step6.getString("logo");

 // Points
 String points = step5.getString("points");
 String goalsDiff = step5.getString("goalsDiff");


 // J G N P
 JSONObject step7 = step5.getJSONObject("all");
 String j = step7.getString("played");
 String g = step7.getString("win");
 String n = step7.getString("draw");
 String p = step7.getString("lose");


 //BP BC
 JSONObject step8 = step7.getJSONObject("goals");
 String bp = step8.getString("for");
 String bc = step8.getString("against");


 System.out.println(rank + " " + team_logo + " " + team_name + " " + j + " " + g + " " + n + " " + p + " " + bp + " " + bc + " " + goalsDiff +
 " " + points);


 final String col1 = rank;
 final String col2 = team_name;
 final String col3 = j;
 final String col4 = g;
 final String col5 = n;
 final String col6 = p;
 final String col7 = bp;
 final String col8 = bc;
 final String col9 = goalsDiff;
 final String col10 = points;
 final String coliv = team_logo;


 row = new TableRow(getActivity().getBaseContext());

 tv1 = new TextView(getActivity().getBaseContext());
 tv1.setText(col1);
 tv1.setGravity(Gravity.CENTER);
 tv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 iv1 = new ImageView(getActivity().getBaseContext());
 Picasso.get().load(coliv).into(iv1);
 iv1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv2 = new TextView(getActivity().getBaseContext());
 tv2.setText(col2);
 tv2.setGravity(Gravity.CENTER);
 tv2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv3 = new TextView(getActivity().getBaseContext());
 tv3.setText(col3);
 tv3.setGravity(Gravity.CENTER);
 tv3.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv4 = new TextView(getActivity().getBaseContext());
 tv4.setText(col4);
 tv4.setGravity(Gravity.CENTER);
 tv4.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv5 = new TextView(getActivity().getBaseContext());
 tv5.setText(col5);
 tv5.setGravity(Gravity.CENTER);
 tv5.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv6 = new TextView(getActivity().getBaseContext());
 tv6.setText(col6);
 tv6.setGravity(Gravity.CENTER);
 tv6.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv7 = new TextView(getActivity().getBaseContext());
 tv7.setText(col7);
 tv7.setGravity(Gravity.CENTER);
 tv7.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv8 = new TextView(getActivity().getBaseContext());
 tv8.setText(col8);
 tv8.setGravity(Gravity.CENTER);
 tv8.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv9 = new TextView(getActivity().getBaseContext());
 tv9.setText(col9);
 tv9.setGravity(Gravity.CENTER);
 tv9.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

 tv10 = new TextView(getActivity().getBaseContext());
 tv10.setText(col10);
 tv10.setGravity(Gravity.CENTER);
 tv10.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1));

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
 if (tv3.getParent() != null) {
 ((ViewGroup) tv3.getParent()).removeView(tv3); // <- fix
 }
 row.addView(tv3);
 if (tv4.getParent() != null) {
 ((ViewGroup) tv4.getParent()).removeView(tv4); // <- fix
 }
 row.addView(tv4);
 if (tv5.getParent() != null) {
 ((ViewGroup) tv5.getParent()).removeView(tv5); // <- fix
 }
 row.addView(tv5);
 if (tv6.getParent() != null) {
 ((ViewGroup) tv6.getParent()).removeView(tv6); // <- fix
 }
 row.addView(tv6);
 if (tv7.getParent() != null) {
 ((ViewGroup) tv7.getParent()).removeView(tv7); // <- fix
 }
 row.addView(tv7);
 if (tv8.getParent() != null) {
 ((ViewGroup) tv8.getParent()).removeView(tv8); // <- fix
 }
 row.addView(tv8);
 if (tv9.getParent() != null) {
 ((ViewGroup) tv9.getParent()).removeView(tv9); // <- fix
 }
 row.addView(tv9);
 if (tv10.getParent() != null) {
 ((ViewGroup) tv10.getParent()).removeView(tv10); // <- fix
 }
 row.addView(tv10);

 if (row.getParent() != null) {
 ((ViewGroup) row.getParent()).removeView(row);
 }
 table.addView(row);

 }
 }

 private String requeteLeague(String id) {
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
 String response = requeteLeague(id[0]);
 return response;
 }

 protected void onPostExecute(String result) {
 System.out.println("response : "+result);
 }
 }
 **/
