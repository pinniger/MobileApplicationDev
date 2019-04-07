package com.example.gametracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class players extends AppCompatActivity {

    private static final String TAG = "PlayersClass";
    private RecyclerView mRecyclerView;
    private PlayersListAdapter mAdapter;
    private List<Player> mPlayers;

    @Override
    protected void onStart() {
        super.onStart();
        // repopulate the players object every time this activity gets started
        // so it is always up to date with the correct list (after insert or update)
        populatePlayers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        // Set the tool bar text
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All Players");
        setSupportActionBar(toolbar);

        // init the floating action button
        initFab();

        // Init the recycler view and list adapter
        mPlayers = new ArrayList<>();
        mRecyclerView = findViewById(R.id.players_recycler_view);
        mAdapter = new PlayersListAdapter(this, mPlayers, R.layout.playerlist_item);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // populate the players member variable
        populatePlayers();
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(players.this, new_player.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    /**
     * Populates the list of players and notifies the list adapter that
     * the list data has changed and log any errors
     */
    private void populatePlayers() {
        try {
            mPlayers.clear();
            DataSourceHelper pds = new DataSourceHelper(this);
            pds.open();
            mPlayers.addAll(pds.getAllPlayers());
            pds.close();
        } catch (Exception e) {
            Log.d(TAG, "populatePlayers: " + e.getMessage());
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_players) {
            Intent intent = new Intent(players.this, players.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_games) {
            Intent intent = new Intent(players.this, games.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //showToast("Players action clicked");
        }

        return super.onOptionsItemSelected(item);
    }

}
