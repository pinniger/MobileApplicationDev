package com.example.gametracker;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class new_player extends AppCompatActivity {

    private static final String TAG = "new_player";
    Player mCurrentPlayer;
    Context mContext;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view
        setContentView(R.layout.activity_new_player);

        // init member variables
        this.mContext = this;
        this.mToolbar = findViewById(R.id.new_player_toolbar);

        // Decided if we need to edit or create new, if no id
        // is passed in the intent extra, then we'll set it to
        // -1, which is the indicator that we'll need to perform
        // a insert for a new player
        int id = getIntent().getIntExtra("id", -1);
        if (id == -1){
            startNewMode(id);
        } else {
            startEditMode(id);
        }

        setSupportActionBar(mToolbar);

        // Set up button event listeners
        initCancelButton();
        initSaveButton();
    }


    /**
     * Method to setup the activity to create a new player
     * @param id
     */
    private void startNewMode(int id) {
        mToolbar.setTitle(R.string.new_player_text);
        mCurrentPlayer = new Player();
        mCurrentPlayer.setId(id);
    }

    /**
     * Method to set up the activity to edit a player
     * @param id
     */
    private void startEditMode(int id) {

        // Get the player we need to edit
        try {
            DataSourceHelper pds = new DataSourceHelper(this);
            pds.open();
            mCurrentPlayer = pds.getPlayer(id);
            pds.close();
        } catch (Exception e){
            Log.d(TAG, "startEditMode: ERROR: " + e.getMessage());
        }

        // Set the title of the tool bar
        mToolbar.setTitle(getString(R.string.edit_text) + " " + mCurrentPlayer.getName());

        // Get the view widgets we need and set their text
        TextView name = findViewById(R.id.edit_new_player_name);
        name.setText(mCurrentPlayer.getName());

        TextView group = findViewById(R.id.edit_new_player_group);
        group.setText(mCurrentPlayer.getGroup());
    }

    /***
     * Attach click listener to cancel button to exit the activity
     * when clicked
     */
    private void initCancelButton() {
        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Attach click listener to the save button to save or update then
     * player
     */
    private void initSaveButton() {
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // Get the text from the view widgets and set the current player
                // object appropriately
                TextView name = findViewById(R.id.edit_new_player_name);
                mCurrentPlayer.setName(name.getText().toString());

                TextView group = findViewById(R.id.edit_new_player_group);
                mCurrentPlayer.setGroup(group.getText().toString());

                // perform database operation
                insertOrUpdatePlayer();

                // finish and close this activity
                finish();
            }
        });
    }

    /**
     * use the data source helper object to add a new player if the id is
     * less than 0, otherwise update the player because its a valid id and
     * log any errors
     */
    private void insertOrUpdatePlayer() {

        try {
            DataSourceHelper dsh = new DataSourceHelper(new_player.this);
            dsh.open();
            if (mCurrentPlayer.getId() < 0) {
                dsh.insertPlayer(mCurrentPlayer);
            } else {
                dsh.updatePlayer(mCurrentPlayer);
            }
            dsh.close();
        } catch (Exception e) {
            Log.d(TAG, "savePlayer: " + e.getMessage());
        }
    }
}
