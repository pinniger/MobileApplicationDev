package com.example.gametracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class players extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PlayersListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Player> list = PlayerRepository.GetAll();

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

        //initTopPlayers();

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.players_recycler_view);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new PlayersListAdapter(this, list);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initTopPlayers() {
        Player p1 = new Player("Clair Inniger","Developers", "@drawable/profile_woman");
        p1.setScore(75);

        ImageView civ = (ImageView) findViewById(R.id.profile_image);
        civ.setImageResource(R.drawable.profile_woman);

        TextView name = findViewById(R.id.text_player_one);
        name.setText(p1.getName());
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
            //Toast.makeText(this, "Players action has been clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_games) {
            Intent intent = new Intent(players.this, games.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //Toast.makeText(this, "Players actioin clicked", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
