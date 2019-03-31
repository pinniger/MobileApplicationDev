package com.example.gametracker;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class new_player extends AppCompatActivity {

    private static final String TAG = "new_player";
    Player currentPlayer;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(R.layout.activity_new_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        String id = getIntent().getStringExtra("id");

        if (id == null){
            toolbar.setTitle("New Player");
            currentPlayer = new Player();
            currentPlayer.setId(-1);

        } else {
            try {
                PlayersDataSource pds = new PlayersDataSource(this);
                pds.open();
                currentPlayer = pds.getPlayer(Integer.parseInt(id));
                pds.close();
            } catch (Exception e){
                Log.d(TAG, "onCreate: ERROR: " + e.getMessage());
            }
            toolbar.setTitle("Edit " + currentPlayer.getName());

            TextView name = findViewById(R.id.editName);
            TextView group = findViewById(R.id.editGroup);
            TextView image = findViewById(R.id.editImage);

            name.setText(currentPlayer.getName());
            group.setText(currentPlayer.getGroup());
            image.setText(currentPlayer.getImage());
        }

        setSupportActionBar(toolbar);
        initCurrentPlayer();
        initSaveButton();
    }

    private void initCurrentPlayer() {

    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView name = findViewById(R.id.editName);
                TextView group = findViewById(R.id.editGroup);

                // TODO: Deal with images later.
                //TextView image = findViewById(R.id.editImage);
                String image = "@drawable/profile_woman";
                currentPlayer.setName(name.getText().toString());
                currentPlayer.setGroup(group.getText().toString());
                currentPlayer.setImage(image);


                try {
                    PlayersDataSource pds = new PlayersDataSource(new_player.this);
                    pds.open();
                    if (currentPlayer.getId() < 0) {
                        if(pds.insertPlayer(currentPlayer)){
                            Toast.makeText(mContext,"player inserted", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        pds.updatePlayer(currentPlayer);
                        Toast.makeText(mContext,"player updated", Toast.LENGTH_SHORT).show();
                    }
                    pds.close();
                } catch (Exception e) {
                    Log.d(TAG, "savePlayer: " + e.getMessage());
                }
            }
        });
    }


    public void savePlayer(View view) {


    }
}
