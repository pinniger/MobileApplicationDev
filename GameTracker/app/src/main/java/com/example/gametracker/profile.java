package com.example.gametracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);

        initProfile();
    }


    private void initProfile() {
        PlayerDetail p1 = new PlayerDetail();

        p1.setRank(2);
        p1.setName("Clair Inniger");
        p1.setGroup("Developers");
        p1.setFirstPlaceFinishes(8);
        p1.setSecondPlaceFinishes(12);
        p1.setThirdPlaceFinishes(4);

        TextView name = findViewById(R.id.textName);
        name.setText(p1.getName());

        TextView group = findViewById(R.id.textGroupName);
        group.setText(p1.getGroup());

        TextView points = findViewById(R.id.textPointsNumber);
        points.setText(Integer.toString(p1.getTotalScore()));

        TextView rank = findViewById(R.id.textRankNumber);
        rank.setText(Integer.toString(p1.getRank()));

        TextView firstPlace = findViewById(R.id.textFirstNumber);
        firstPlace.setText(Integer.toString(p1.getFirstPlaceFinishes()));

        TextView secondPlace = findViewById(R.id.textSecondNumber);
        secondPlace.setText(Integer.toString(p1.getSecondPlaceFinishes()));

        TextView thirdPlace = findViewById(R.id.textThridNumber);
        thirdPlace.setText(Integer.toString(p1.getThirdPlaceFinishes()));

    }

}
