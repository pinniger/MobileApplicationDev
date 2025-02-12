package com.example.gametracker;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private RecyclerView mRecyclerView;
    private GamesListAdapter mAdapter;
    private List<Game> mGames;

    @Override
    protected void onStart() {
        super.onStart();
        populateGames();
        Log.d(TAG, "onStart: Has been called");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All Games");
        setSupportActionBar(toolbar);

        initFAB();

        mGames = new ArrayList<>();
        mRecyclerView = findViewById(R.id.games_recycler_view);
        mAdapter = new GamesListAdapter(this, mGames, R.layout.gameslist_item);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        populateGames();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // hide the games menu item
        menu.getItem(1).setVisible(false);
        return true;
    }

    private void populateGames() {
        try {
            mGames.clear();
            DataSourceHelper dsh = new DataSourceHelper(this);
            dsh.open();
            mGames.addAll(dsh.getAllGames());
            dsh.close();
        } catch (Exception e) {
            Log.d(TAG, "populateGames: " + e.getMessage());
        }

        // let the user know there isn't any data available
        if(mGames.size() == 0){
            TextView noData = findViewById(R.id.text_games_nodata);
            noData.setVisibility(View.VISIBLE);
        } else {
            TextView noData = findViewById(R.id.text_games_nodata);
            noData.setVisibility(View.GONE);
        }

        mAdapter.notifyDataSetChanged();
    }

    private void initFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(games.this, new_game.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_players) {
            Intent intent = new Intent(games.this, players.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
