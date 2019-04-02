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
import android.widget.Toast;

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
        populatePlayers();
        Log.d(TAG, "onStart: Has been called");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("All Players");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(players.this, new_player.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mPlayers = new ArrayList<>();
        mRecyclerView = findViewById(R.id.players_recycler_view);
        mAdapter = new PlayersListAdapter(this, mPlayers, R.layout.playerlist_item);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        populatePlayers();
    }

    private void populatePlayers() {
        try {
            mPlayers.clear();
            DataSourceHelper pds = new DataSourceHelper(this);
            pds.open();
            mPlayers.addAll(pds.getAll());
            pds.close();
        } catch (Exception e) {
            Log.d(TAG, "onCreate: Didn't work: " + e.getMessage());
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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
