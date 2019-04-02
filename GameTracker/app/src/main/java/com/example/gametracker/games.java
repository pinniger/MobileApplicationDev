package com.example.gametracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class games extends AppCompatActivity {

    private static final String TAG = "GamesClass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All Games");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(games.this, new_game.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        initGames();
    }

    private void initGames() {
        Date currentTime = Calendar.getInstance().getTime();
        Game g1 = new Game();
        //g1.setDate(currentTime);

        TextView tv = findViewById(R.id.text_game_one);
        String df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH).format(g1.getDate());
        tv.setText(df);
    }


}
