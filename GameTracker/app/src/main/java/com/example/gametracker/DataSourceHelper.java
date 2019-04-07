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

/**
 * Class used to interact with the database
 */
public class DataSourceHelper {

    private SQLiteDatabase database;
    private GameTrackerDBHelper dbHe1per;

    public DataSourceHelper(Context context) {
        dbHe1per = new GameTrackerDBHelper(context);
    }

    /**
     * Inserts a new player into the database
     * @param player
     * @return
     */
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

    /**
     * Deletes player from the database
     * @param player
     * @return True if successful, false if unsuccessful
     */
    public boolean deletePlayer(Player player) {
        try {

            return database.delete(dbHe1per.PLAYER_TABLE_NAME, dbHe1per.PLAYER_COL_ID + "= ?", new String[]{Integer.toString(player.getId())}) > 0;

        } catch (Exception e) {
            Log.d(TAG, "deletePlayer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets all the players
     * @return Cursor object of all the players
     */
    public Cursor getAllCursor() {
        try {
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + ";";
            return database.rawQuery(query, null);

        } catch (Exception e) {
            Log.d(TAG, "getAllCursor: Failed getting players");
            return null;
        }
    }

    /**
     * Gets all players
     * @return List of players
     */
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        try {
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + ";";
            Cursor cursor = database.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Log.d(TAG, "getAllPlayers: adding player " + cursor.getInt(0));
                Player p = new Player();
                p.setId(cursor.getInt(0));
                p.setName(cursor.getString(1));
                p.setGroup(cursor.getString(2));

                players.add(p);
            }

            cursor.close();

        } catch (Exception e) {
            Log.d(TAG, "getAllPlayers: Failed getting players");
        }

        return players;
    }

    /**
     * Gets a single player by Id
     * @param id
     * @return A player object
     */
    public Player getPlayer(int id) {
        try {
            Player player = new Player();
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + " where _id = " + id;
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
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

    /**
     * Gets a single player detail object
     * @param id
     * @return Player Detail object
     */
    public PlayerDetail getPlayerDetail(int id) {

        try {
            PlayerDetail player = new PlayerDetail();
            String query = "select * from " + dbHe1per.PLAYER_TABLE_NAME + " where " + dbHe1per.PLAYER_COL_ID + " = " + id;
            Cursor playerCursor = database.rawQuery(query, null);
            if (playerCursor.moveToFirst()) {
                player.setId(playerCursor.getInt(0));
                player.setName(playerCursor.getString(1));
                player.setGroup(playerCursor.getString(2));
                playerCursor.close();
            }

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
            Cursor thirdPlaceCursor = database.rawQuery(query, null);
            if (thirdPlaceCursor.moveToFirst()) {
                player.setThirdPlaceFinishes(thirdPlaceCursor.getInt(0));
                thirdPlaceCursor.close();
            }


            return player;
        } catch (Exception e) {
            Log.d(TAG, "getPlayerDetail: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets a list of Player Detail objects
     * @return List of player Detail objects
     */
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

    /**
     * Updates a player in the database
     * @param player
     * @return Successful or not
     */
    public boolean updatePlayer(Player player) {
        Log.d(TAG, "updatePlayer: Updating " + player.getName());
        try {
            ContentValues initialValues = setPlayerValues(player);
            return database.update(dbHe1per.PLAYER_TABLE_NAME, initialValues, dbHe1per.PLAYER_COL_ID + " = " + player.getId(), null) > 0;
        } catch (Exception e) {
            Log.d(TAG, "updatePlayer: " + e.getMessage());
            return false;
        }
    }


    /**
     * This method is here to seed the players table during development
     * I'm leaving it here for future use, but it is not called anywhere
     * in the program
     */
    public void seedPlayers() {
        Log.d(TAG, "seedPlayers: Seeding players...");
        try {
            PlayerRepositoryMock mockData = new PlayerRepositoryMock();
            List<Player> players = mockData.GetAll();
            for (Player p : players) {
                insertPlayer(p);
            }
        } catch (Exception e) {
            Log.d(TAG, "seedPlayers: " + e.getMessage());
        }
    }


    /**
     * Helper method for creating a content value object for a player
     * @param player
     * @return Player content value object
     */
    private ContentValues setPlayerValues(Player player) {
        ContentValues values = new ContentValues();

        values.put(dbHe1per.PLAYER_COL_NAME, player.getName());
        values.put(dbHe1per.PLAYER_COL_GROUP, player.getGroup());

        return values;
    }

    /**
     * Helper method for creating a content value object for a game
     * @param game
     * @return Game content value object
     */
    private ContentValues setGameValues(Game game) {
        ContentValues values = new ContentValues();

        values.put(dbHe1per.GAMES_COL_DATEPLAYED, game.getDateString());
        values.put(dbHe1per.GAMES_COL_FIRST_PLACE, game.getFirstPlace());
        values.put(dbHe1per.GAMES_COL_SECOND_PLACE, game.getSecondPlace());
        values.put(dbHe1per.GAMES_COL_THRID_PLACE, game.getThirdPlace());

        return values;
    }

    /**
     * Opens a database connection
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHe1per.getWritableDatabase();
    }

    /**
     * Closes the database connection
     */
    public void close() {
        dbHe1per.close();
    }

    /**
     * Deletes a game
     * @param game
     * @return Successful or not
     */
    public boolean deleteGame(Game game) {
        try {

            return database.delete(dbHe1per.GAMES_TABLE_NAME, dbHe1per.GAMES_COL_ID + "= ?", new String[]{Integer.toString(game.getId())}) > 0;

        } catch (Exception e) {
            Log.d(TAG, "deleteGame: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a list of all games
     * @return List of all games
     */
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            String query = "select * from " + dbHe1per.GAMES_TABLE_NAME + " order by " + dbHe1per.GAMES_COL_DATEPLAYED + " desc;";
            Cursor cursor = database.rawQuery(query, null);
            while (cursor.moveToNext()) {
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

    /**
     * Gets a list of recent winners
     * @param num
     * @return List of recent winners
     */
    public List<RecentWinner> getRecentWinners(int num) {

        List<RecentWinner> winners = new ArrayList<>();
        String query = "select * from " + dbHe1per.GAMES_TABLE_NAME + " order by " + dbHe1per.GAMES_COL_DATEPLAYED + " desc LIMIT " + num + ";";
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {

            // reuse formatter for dates
            Game game = new Game();
            game.setDateString(cursor.getString(1));

            // get the first place player for the game
            Player player = this.getPlayer(cursor.getInt(2));

            RecentWinner rw = new RecentWinner(player.getName(), game.getDate());
            rw.setPlayerId(player.getId());
            winners.add(rw);
        }

        return winners;
    }

    /**
     * Gets a single game
     * @param id
     * @return Game object
     */
    public Game getGame(int id) {
        try {
            Game game = new Game();
            String query = "select * from " + dbHe1per.GAMES_TABLE_NAME + " where _id = " + id;
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                game.setId(cursor.getInt(0));
                game.setDateString(cursor.getString(1));
                game.setFirstPlace(cursor.getInt(2));
                game.setSecondPlace(cursor.getInt(3));
                game.setThirdPlace(cursor.getInt(4));

                cursor.close();
            }

            return game;
        } catch (Exception e) {
            Log.d(TAG, "getGame: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates a game in the database
     * @param game
     * @return
     */
    public boolean updateGame(Game game) {
        try {
            ContentValues initialValues = setGameValues(game);
            return database.update(dbHe1per.GAMES_TABLE_NAME, initialValues, dbHe1per.GAMES_COL_ID + " = " + game.getId(), null) > 0;
        } catch (Exception e) {
            Log.d(TAG, "updateGame: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inserts a game into the database
     * @param game
     * @return
     */
    public boolean insertGame(Game game) {
        try {
            ContentValues initialValues = setGameValues(game);
            return database.insert(dbHe1per.GAMES_TABLE_NAME, null, initialValues) > 0;
        } catch (Exception e) {
            Log.d(TAG, "insertGame: " + e.getMessage());
            return false;
        }
    }
}
