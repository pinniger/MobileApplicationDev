package com.example.gametracker;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepository {
    public static List<Player> GetAll(){
        List<Player> players = new ArrayList<>();

        players.add(new Player("John Doe","Developers", "@drawable/profile_woman"));
        players.add(new Player("Jack Doe","Developers", "@drawable/profile_woman"));
        players.add(new Player("Justin Doe","Developers", "@drawable/profile_woman"));
        players.add(new Player("Jenna Doe","Developers", "@drawable/profile_woman"));
        players.add(new Player("Jane Doe","Developers", "@drawable/profile_woman"));


        return players;
    }
}
