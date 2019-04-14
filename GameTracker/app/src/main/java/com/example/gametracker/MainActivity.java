package com.example.gametracker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
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

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotificationManager;


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

        createAlarm();
    }

    private void createAlarm() {

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
         Calendar calendar = Calendar.getInstance();
         calendar.set(Calendar.DAY_OF_WEEK, 5);
         calendar.set(Calendar.HOUR, 15);
         calendar.set(Calendar.MINUTE, 05);
         calendar.set(Calendar.SECOND, 0);

         if (alarmManager != null) {
             alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), calendar.getTimeInMillis(), notifyPendingIntent);
         }

        // Create the notification channel.
        createNotificationChannel();
    }

    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,"Log the game!", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Reminder to log your game on Friday's at 3:05 pm");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
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
        } catch (Exception e) {
            Log.d(TAG, "populateRecentWinners: Didn't work: " + e.getMessage());
        }

        // let the user know there isn't any data available
        if(mTopPlayers.size() == 0){
            TextView noData = findViewById(R.id.textTopPlayersNoData);
            noData.setVisibility(View.VISIBLE);
        } else {
            // sort by total score descending
            Collections.sort(mTopPlayers);
        }

        mTopPlayersListAdapter.notifyDataSetChanged();
    }

    private void populateRecentWinners() {

        try {
            mRecentWinners.clear();
            DataSourceHelper pds = new DataSourceHelper(this);
            pds.open();
            mRecentWinners.addAll(pds.getRecentWinners(3));
            pds.close();
        } catch (Exception e) {
            Log.d(TAG, "populateRecentWinners: Didn't work: " + e.getMessage());
        }

        // let the user know there isn't any data available
        if(mTopPlayers.size() == 0){
            TextView noData = findViewById(R.id.textRecentWinnersNoData);
            noData.setVisibility(View.VISIBLE);
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
