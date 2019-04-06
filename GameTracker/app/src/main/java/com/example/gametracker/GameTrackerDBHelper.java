package com.example.gametracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

public class GameTrackerDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "gametracker.db";
    private static final int DATABASE_VERSION = 1;

    // Players Table
    public static final String PLAYER_TABLE_NAME = "players";
    public static final String PLAYER_COL_ID = "_id";
    public static final String PLAYER_COL_NAME = "name";
    public static final String PLAYER_COL_GROUP = "group_name";
    public static final String PLAYER_COL_IMAGE = "image";

    // Games Table
    public static final String GAMES_TABLE_NAME = "games";
    public static final String GAMES_COL_ID = "_id";
    public static final String GAMES_COL_DATEPLAYED = "date_played";
    public static final String GAMES_COL_FIRST_PLACE = "first_place";
    public static final String GAMES_COL_SECOND_PLACE = "second_place";
    public static final String GAMES_COL_THRID_PLACE = "thrid_place";

    public final Context CONTEXT;


    public GameTrackerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.CONTEXT = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

         String CREATE_TABLE_PLAYERS_SQL =
                "create table " + PLAYER_TABLE_NAME + " ( "+ PLAYER_COL_ID + " integer primary key autoincrement, "
                        + PLAYER_COL_NAME + " text, "
                        + PLAYER_COL_GROUP + " text, "
                        + PLAYER_COL_IMAGE + " text);";

        String CREATE_TABLE_GAMES_SQL =
                "create table " + GAMES_TABLE_NAME + " (" + GAMES_COL_ID + " integer primary key autoincrement, "
                        + GAMES_COL_DATEPLAYED + " date not null, "
                        + GAMES_COL_FIRST_PLACE + " text, "
                        + GAMES_COL_SECOND_PLACE + " text, "
                        + GAMES_COL_THRID_PLACE + " text);";

        db.execSQL(CREATE_TABLE_PLAYERS_SQL);
        db.execSQL(CREATE_TABLE_GAMES_SQL);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(GameTrackerDBHelper.class.getName(), "Upgrading database from version " + oldVersion + "to" + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GAMES_TABLE_NAME);
    }
}
