package com.example.gametracker;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepositoryMock {

    List<Player> players = new ArrayList<>();
    public PlayerRepositoryMock() {
        players.add(new Player("John Doe","Developer"));
        players.add(new Player("Jack Doe","Developer"));
        players.add(new Player("Justin Doe","Developer"));
        players.add(new Player("Jenna Doe","Reporting"));
        players.add(new Player("Jane Doe","Reporting"));

    }

    public List<Player> GetAll(){
        return players;
    }

    public List<Player> GetTopRanked(int num){
        return players.subList(0, num);
    }
}
