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

import java.util.List;

public class players extends AppCompatActivity {

    private static final String TAG = "Players Class";
    private RecyclerView mRecyclerView;
    private PlayersListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            //PlayerRepositoryMock playersRepo = new PlayerRepositoryMock();
            //List<Player> list = playersRepo.GetAll();
            PlayersDataSource pds = new PlayersDataSource(this);
            pds.open();
            List<Player> list = pds.getAll();
            pds.close();

            Log.d(TAG, "onCreate: GetAll returned: " + list.size());

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

            mRecyclerView = findViewById(R.id.players_recycler_view);
            mAdapter = new PlayersListAdapter(this, list, R.layout.playerlist_item);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));




        } catch (Exception e) {
            Log.d(TAG, "onCreate: Didn't work: " + e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_players) {
            Intent intent = new Intent(players.this, players.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_games) {
            Intent intent = new Intent(players.this, games.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //Toast.makeText(this, "Players action clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
