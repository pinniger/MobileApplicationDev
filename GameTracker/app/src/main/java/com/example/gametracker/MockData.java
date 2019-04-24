package com.example.gametracker;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MockData {
    private String mDateFormat = "MM/dd/yy";
    List<Player> players = new ArrayList<>();
    List<Game> games = new ArrayList<>();

    public MockData() {
        players.add(new Player("John Doe","Developer"));
        players.add(new Player("Jack Doe","Developer"));
        players.add(new Player("Justin Doe","Developer"));
        players.add(new Player("Jenna Doe","Reporting"));
        players.add(new Player("Jane Doe","Reporting"));
    }

    public List<Player> GetPlayers(){
        return players;
    }

}
