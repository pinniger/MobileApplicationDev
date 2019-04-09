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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView mRecentWinnersRecyclerView;
    private RecentWinnersListAdapter mWinnersListAdapter;
    private List<RecentWinner> mRecentWinners;

    private RecyclerView mTopPlayersRecyclerView;
    private TopPlayersListAdapter mTopPlayersListAdapter;
    private List<PlayerDetail> mTopPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // init floating action button
        initFab();

        // top players list
        mTopPlayers = new ArrayList<>();
        mTopPlayersRecyclerView = findViewById(R.id.recycler_view_top_winners);
        mTopPlayersListAdapter = new TopPlayersListAdapter(this, mTopPlayers, R.layout.topplayerslist_item);
        mTopPlayersRecyclerView.setAdapter(mTopPlayersListAdapter);
        mTopPlayersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // recent winners list
        mRecentWinners = new ArrayList<>();
        mRecentWinnersRecyclerView = findViewById(R.id.recycler_view_recent_winners);
        mWinnersListAdapter = new RecentWinnersListAdapter(this, mRecentWinners, R.layout.recentwinners_item);
        mRecentWinnersRecyclerView.setAdapter(mWinnersListAdapter);
        mRecentWinnersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        populateTopPlayers();
        populateRecentWinners();
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, new_game.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateTopPlayers();
        populateRecentWinners();
    }

    private void populateTopPlayers() {
        try {
            mTopPlayers.clear();
            DataSourceHelper pds = new DataSourceHelper(this);
            pds.open();
            mTopPlayers.addAll(pds.getAllPlayerDetail().subList(0,3));
            pds.close();

            // let the user know there isn't any data available
            if(mTopPlayers.size() == 0){
                TextView noData = findViewById(R.id.textTopPlayersNoData);
                noData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.d(TAG, "populateRecentWinners: Didn't work: " + e.getMessage());
        }

        // sort by total score descending
        Collections.sort(mTopPlayers);

        mTopPlayersListAdapter.notifyDataSetChanged();
    }

    private void populateRecentWinners() {

        try {
            mRecentWinners.clear();
            DataSourceHelper pds = new DataSourceHelper(this);
            pds.open();
            mRecentWinners.addAll(pds.getRecentWinners(3));
            pds.close();

            // let the user know there isn't any data available
            if(mTopPlayers.size() == 0){
                TextView noData = findViewById(R.id.textRecentWinnersNoData);
                noData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.d(TAG, "populateRecentWinners: Didn't work: " + e.getMessage());
        }


        mWinnersListAdapter.notifyDataSetChanged();
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
            Intent intent = new Intent(MainActivity.this, players.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_games) {
            Intent intent = new Intent(MainActivity.this, games.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
