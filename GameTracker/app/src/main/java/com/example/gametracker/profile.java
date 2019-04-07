package com.example.gametracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class profile extends AppCompatActivity {

    private static final String TAG = "Profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", -1);
        List<PlayerDetail> playerList = new ArrayList<>();

        try {
            DataSourceHelper dsh = new DataSourceHelper(this);
            dsh.open();
            playerList = dsh.getAllPlayerDetail();
            dsh.close();
        } catch (Exception e) {
            Log.d(TAG, "initProfile: Didn't work: " + e.getMessage());
        }

        // sort players decending
        Collections.sort(playerList);

        // after the players are sorted, we can loop over them until
        // we find the correct profile to display, this way we can display
        // the player rank without have to store it, much easier to maintain
        // than updating every player record every time a game gets created/edited.
        for(int i = 0; i <= playerList.size(); i++){
            if (playerList.get(i).getId() == id) {
                initProfile(playerList.get(i), i + 1);
                break;
            }
        }
    }

    private void initProfile(PlayerDetail player, int rank) {

        if (player != null) {
            TextView name = findViewById(R.id.textName);
            name.setText(player.getName());

            TextView group = findViewById(R.id.textGroupName);
            group.setText(player.getGroup());

            TextView points = findViewById(R.id.textPointsNumber);
            points.setText(Integer.toString(player.getTotalScore()));

            TextView rankTextView = findViewById(R.id.textRankNumber);
            rankTextView.setText(Integer.toString(rank));

            TextView firstPlace = findViewById(R.id.text_profile_first_number);
            firstPlace.setText(Integer.toString(player.getFirstPlaceFinishes()));

            TextView secondPlace = findViewById(R.id.text_profile_second_number);
            secondPlace.setText(Integer.toString(player.getSecondPlaceFinishes()));

            TextView thirdPlace = findViewById(R.id.text_profile_third_number);
            thirdPlace.setText(Integer.toString(player.getThirdPlaceFinishes()));

            ColorGenerator generator = ColorGenerator.MATERIAL;
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(player.getName().substring(0, 1), generator.getRandomColor());

            ImageView profileImage = findViewById(R.id.profile_image);
            profileImage.setImageDrawable(drawable);

        }

    }

}
