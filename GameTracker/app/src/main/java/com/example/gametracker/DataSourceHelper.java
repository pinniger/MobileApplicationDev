package com.example.gametracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataSourceHelper {

    private SQLiteDatabase database;
    private GameTrackerDBHelper dbHe1per;

    public boolean insertGame (Game game){
        Log.d(TAG, "insertGame: Inserting " + game.getDate());
        boolean succeeded = false;
        try {
            ContentValues initialValues = setGameValues(game);
            succeeded = database.insert(dbHe1per.GAMES_TABLE_NAME, null, initialValues) > 0;
        } catch (Exception e) {
            // do nothing
        }

        return succeeded;
    }

    public DataSourceHelper(Context context) {
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

    public boolean deletePlayer(Player player) {
        try {

            return database.delete(dbHe1per.PLAYER_TABLE_NAME, dbHe1per.PLAYER_COL_ID + "= ?", new String[] {Integer.toString(player.getId())}) > 0;

        } catch (Exception e) {
            Log.d(TAG, "deletePlayer: " + e.getMessage());
            return false;
        }
    }


    public Cursor getAllCursor(){
        try {
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + ";";
            return database.rawQuery(query, null);

        } catch (Exception e) {
            Log.d(TAG, "getAllCursor: Failed getting players");
            return null;
        }
    }

    public List<Player> getAll(){
        List<Player> players = new ArrayList<>();
        try {
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + ";";
            Cursor cursor = database.rawQuery(query, null);
            while(cursor.moveToNext()){
                Log.d(TAG, "getAll: adding player " + cursor.getInt(0));
                Player p = new Player();
                p.setId(cursor.getInt(0));
                p.setName(cursor.getString(1));
                p.setGroup(cursor.getString(2));

                players.add(p);
            }

            cursor.close();

        } catch (Exception e) {
            Log.d(TAG, "getAll: Failed getting players");
        }

        return players;
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

                cursor.close();
            }

            return player;
        } catch (Exception e) {
            Log.d(TAG, "getPlayer: " + e.getMessage());
            return null;
        }
    }

    public PlayerDetail getPlayerDetail(int id) {
        try {
            PlayerDetail player = new PlayerDetail();
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + " where _id = " + id;
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                player.setId(cursor.getInt(0));
                player.setName(cursor.getString(1));
                player.setGroup(cursor.getString(2));
                cursor.close();
            }

            // get first place finishes
            query = "select count(*) as total from " + dbHe1per.GAMES_TABLE_NAME + " where " + dbHe1per.GAMES_COL_FIRST_PLACE + " = " + id;
            cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                player.setFirstPlaceFinishes(cursor.getInt(0));
                cursor.close();
            }

            // get second place finishes
            query = "select count(*) as total from " + dbHe1per.GAMES_TABLE_NAME + " where " + dbHe1per.GAMES_COL_SECOND_PLACE + " = " + id;
            cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                player.setSecondPlaceFinishes(cursor.getInt(0));
                cursor.close();
            }

            // get third place finishes
            query = "select count(*) as total from " + dbHe1per.GAMES_TABLE_NAME + " where " + dbHe1per.GAMES_COL_THRID_PLACE + " = " + id;
            cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()){
                player.setThirdPlaceFinishes(cursor.getInt(0));
                cursor.close();
            }


            return player;
        } catch (Exception e) {
            Log.d(TAG, "getPlayerDetail: " + e.getMessage());
            return null;
        }
    }

    public List<PlayerDetail> getAllPlayerDetail() {

        List<PlayerDetail> topPlayers = new ArrayList<>();
        try {

            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME;

            Cursor allPlayersCursor = database.rawQuery(query, null);
            while (allPlayersCursor.moveToNext()) {
                PlayerDetail player = new PlayerDetail();
                int id = allPlayersCursor.getInt(0);
                player.setId(allPlayersCursor.getInt(0));
                player.setName(allPlayersCursor.getString(1));
                player.setGroup(allPlayersCursor.getString(2));

                // get first place finishes
                query = "select count(*) as total from " + dbHe1per.GAMES_TABLE_NAME + " where " + dbHe1per.GAMES_COL_FIRST_PLACE + " = " + id;
                Cursor firstPlaceCursor = database.rawQuery(query, null);
                if (firstPlaceCursor.moveToFirst()) {
                    player.setFirstPlaceFinishes(firstPlaceCursor.getInt(0));
                    firstPlaceCursor.close();
                }

                // get second place finishes
                query = "select count(*) as total from " + dbHe1per.GAMES_TABLE_NAME + " where " + dbHe1per.GAMES_COL_SECOND_PLACE + " = " + id;
                Cursor secondPlaceCursor = database.rawQuery(query, null);
                if (secondPlaceCursor.moveToFirst()) {
                    player.setSecondPlaceFinishes(secondPlaceCursor.getInt(0));
                    secondPlaceCursor.close();
                }

                // get third place finishes
                query = "select count(*) as total from " + dbHe1per.GAMES_TABLE_NAME + " where " + dbHe1per.GAMES_COL_THRID_PLACE + " = " + id;
                Cursor thirdPlceCursor = database.rawQuery(query, null);
                if (thirdPlceCursor.moveToFirst()) {
                    player.setThirdPlaceFinishes(thirdPlceCursor.getInt(0));
                    thirdPlceCursor.close();
                }

                topPlayers.add(player);
            }

            allPlayersCursor.close();

            return topPlayers;
        } catch (Exception e) {
            Log.d(TAG, "getAllPlayerDetail: " + e.getMessage());
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

    private ContentValues setPlayerValues(Player player) {
        ContentValues values = new ContentValues();

        values.put(dbHe1per.PLAYER_COL_NAME, player.getName());
        values.put(dbHe1per.PLAYER_COL_GROUP, player.getGroup());
        values.put(dbHe1per.PLAYER_COL_IMAGE, player.getImage());

        return values;
    }

    private ContentValues setGameValues(Game game) {
        ContentValues values = new ContentValues();

        values.put(dbHe1per.GAMES_COL_DATEPLAYED, game.getDateString());
        values.put(dbHe1per.GAMES_COL_FIRST_PLACE, game.getFirstPlace());
        values.put(dbHe1per.GAMES_COL_SECOND_PLACE, game.getSecondPlace());
        values.put(dbHe1per.GAMES_COL_THRID_PLACE, game.getThirdPlace());

        return values;
    }

    public void open() throws SQLException {
        database = dbHe1per.getWritableDatabase();
    }

    public void close() {
        dbHe1per.close();
    }

    public boolean deleteGame(Game game) {
        try {

            return database.delete(dbHe1per.GAMES_TABLE_NAME, dbHe1per.GAMES_COL_ID + "= ?", new String[] {Integer.toString(game.getId())}) > 0;

        } catch (Exception e) {
            Log.d(TAG, "deleteGame: " + e.getMessage());
            return false;
        }
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            String query = "select * from " + dbHe1per.GAMES_TABLE_NAME + " order by " + dbHe1per.GAMES_COL_DATEPLAYED + " desc;";
            Cursor cursor = database.rawQuery(query, null);
            while(cursor.moveToNext()){
                Game game = new Game();
                game.setId(cursor.getInt(0));
                game.setDateString(cursor.getString(1));
                game.setFirstPlace(cursor.getInt(2));
                game.setSecondPlace(cursor.getInt(3));
                game.setThirdPlace(cursor.getInt(4));

                games.add(game);
            }

            cursor.close();

        } catch (Exception e) {
            Log.d(TAG, "getAllGames: Failed getting players");
        }

        return games;
    }

    public List<RecentWinner> getRecentWinners(int num){

        List<RecentWinner> winners = new ArrayList<>();
        String query = "select * from " + dbHe1per.GAMES_TABLE_NAME + " order by " + dbHe1per.GAMES_COL_DATEPLAYED + " desc LIMIT " + num + ";";
        Cursor cursor = database.rawQuery(query, null);

        while(cursor.moveToNext()){

            // reuse formatter for dates
            Game game = new Game();
            game.setDateString(cursor.getString(1));

            // get the first place player for the game
            Player player = this.getPlayer(cursor.getInt(2));

            RecentWinner rw = new RecentWinner(player.getName(), game.getDate());
            winners.add(rw);
        }

        return winners;
    }

}
