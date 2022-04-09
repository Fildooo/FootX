package com.example.footx;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.footx.ui.notifications.NotificationsFragment;
import com.squareup.picasso.Picasso;

public class DetailsMatch extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;
    ImageView i1,i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_match);
        LinearLayout line = (LinearLayout) findViewById(R.id.idLine);

        t1 = new TextView(getBaseContext());
        t1.setText("Date du match : "+ NotificationsFragment.getDATE());
        t1.setTextColor(Color.WHITE);

        line.addView(t1);

        t2 = new TextView(getBaseContext());
        t2.setText("Lieu du match : "+NotificationsFragment.getStadeName()+", "+NotificationsFragment.getStadeVille());
        t2.setTextColor(Color.WHITE);

        line.addView(t2);

        t3 = new TextView(getBaseContext());
        t3.setText("Arbitre du match : "+NotificationsFragment.getARBITRE());
        t3.setTextColor(Color.WHITE);

        line.addView(t3);

        t4 = new TextView(getBaseContext());
        t4.setText("Statut du match : "+NotificationsFragment.getSTATUT());
        t4.setTextColor(Color.WHITE);

        line.addView(t4);

        LinearLayout line1 = (LinearLayout) findViewById(R.id.idLine1);

        t5 = new TextView(getBaseContext());
        t5.setText("Equipe à Domicile : "+NotificationsFragment.getHomeTeam());
        t5.setTextColor(Color.WHITE);

        line1.addView(t5);

        i1 = new ImageView(getBaseContext());
        Picasso.get().load(NotificationsFragment.getHomeLogo()).into(i1);
        i1.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
        line1.addView(i1);

        LinearLayout line2 = (LinearLayout) findViewById(R.id.idLine2);

        t6 = new TextView(getBaseContext());
        t6.setText("Equipe à l'extérieur : "+NotificationsFragment.getAwayTeam());
        t6.setTextColor(Color.WHITE);

        line2.addView(t6);

        i2 = new ImageView(getBaseContext());
        Picasso.get().load(NotificationsFragment.getAwayLogo()).into(i2);
        i2.setLayoutParams(new TableRow.LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
        line2.addView(i2);

        t7 = new TextView(getBaseContext());
        t7.setText("Score à la mie-temps : "+NotificationsFragment.getHomeHalftime()+" - "+NotificationsFragment.getAwayHalftime());
        t7.setTextColor(Color.WHITE);

        line.addView(t7);

        t8 = new TextView(getBaseContext());
        t8.setText("Score final :  "+NotificationsFragment.getHomeGoal()+" - "+NotificationsFragment.getAwayGoal());
        t8.setTextColor(Color.WHITE);

        line.addView(t8);

        t9 = new TextView(getBaseContext());
        t9.setText("Penalty : "+NotificationsFragment.getHomePenalty()+" - "+NotificationsFragment.getAwayPenalty());
        t9.setTextColor(Color.WHITE);

        line.addView(t9);

    }


}