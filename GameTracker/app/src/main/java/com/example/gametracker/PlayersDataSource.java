package com.example.gametracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PlayersDataSource {

    private SQLiteDatabase database;
    private GameTrackerDBHelper dbHe1per;

    public PlayersDataSource(Context context) {
        dbHe1per = new GameTrackerDBHelper(context);
    }

    public boolean insertPlayer(Player player) {
        Log.d(TAG, "insertPlayer: Inserting " + player.getName());
        boolean succeeded = false;
        try {
            ContentValues initialValues = setPlayerValues(player);
            succeeded = database.insert(dbHe1per.PLAYER_TABLE_NAME, null, initialValues) > 0;
        } catch (Exception e) {
            // do nothing
        }

        return succeeded;
    }

    public List<Player> getAll(){
        Log.d(TAG, "getAll: Getting all players");
        List<Player> players = new ArrayList<>();
        try {
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + ";";
            Cursor cursor = database.rawQuery(query, null);
            Log.d(TAG, "getAll: Feteched this many players: " + cursor.getCount());
            while(cursor.moveToNext()){
                Log.d(TAG, "getAll: adding player " + cursor.getInt(0));
                Player p = new Player();
                p.setId(cursor.getInt(0));
                p.setName(cursor.getString(1));
                p.setGroup(cursor.getString(2));
                p.setImage(cursor.getString(3));

                players.add(p);
            }

            cursor.close();

        } catch (Exception e) {
            Log.d(TAG, "getAll: Failed getting players");
        }

        return players;
    }


    private ContentValues setPlayerValues(Player player) {
        ContentValues initialValues = new ContentValues();

        initialValues.put(dbHe1per.PLAYER_COL_NAME, player.getName());
        initialValues.put(dbHe1per.PLAYER_COL_GROUP, player.getGroup());
        initialValues.put(dbHe1per.PLAYER_COL_IMAGE, player.getImage());

        return initialValues;
    }



    public void open() throws SQLException {
        database = dbHe1per.getWritableDatabase();
    }

    public void close() {
        dbHe1per.close();
    }



    public boolean deletePlayer(Player player) {
        try {

            return database.delete(dbHe1per.PLAYER_TABLE_NAME, dbHe1per.PLAYER_COL_ID + "= ?", new String[] {Integer.toString(player.getId())}) > 0;

        } catch (Exception e) {
            Log.d(TAG, "getAll: Failed getting players");
            return false;
        }
    }

    public Player getPlayer(int id) {
        try {
            Player player = new Player();
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + " where _id = " + id;
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                player.setId(cursor.getInt(0));
                player.setName(cursor.getString(1));
                player.setGroup(cursor.getString(2));
                player.setImage(cursor.getString(3));

                cursor.close();
            }

            return player;
        } catch (Exception e) {
            Log.d(TAG, "getAll: Failed getting players");
            return null;
        }

    }

    public boolean updatePlayer(Player player) {
        Log.d(TAG, "updatePlayer: Updating " + player.getName());
        try {
            ContentValues initialValues = setPlayerValues(player);
            return database.update(dbHe1per.PLAYER_TABLE_NAME, initialValues, dbHe1per.PLAYER_COL_ID + " = " + player.getId(), null) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void seedPlayers() {
        Log.d(TAG, "seedPlayers: Seeding players...");
        try {
            PlayerRepositoryMock mockData = new PlayerRepositoryMock();
            List<Player> players = mockData.GetAll();
            for (Player p : players) {
                insertPlayer(p);
            }
        } catch (Exception e){
            Log.d(TAG, "seedPlayers: " + e.getMessage());
        }
    }
}
