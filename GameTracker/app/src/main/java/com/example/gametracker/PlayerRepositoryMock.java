package com.example.gametracker;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepositoryMock {

    List<Player> players = new ArrayList<>();
    public PlayerRepositoryMock() {
        players.add(new Player("John Doe","Developer", "@drawable/profile_woman"));
        players.add(new Player("Jack Doe","Developer", "@drawable/profile_woman"));
        players.add(new Player("Justin Doe","Developer", "@drawable/profile_woman"));
        players.add(new Player("Jenna Doe","Reporting", "@drawable/profile_woman"));
        players.add(new Player("Jane Doe","Reporting", "@drawable/profile_woman"));

    }

    public List<Player> GetAll(){
        return players;
    }

    public List<Player> GetTopRanked(int num){
        return players.subList(0, num);
    }
}
