package com.example.gametracker;


import java.util.Date;

public class Game {
    public Date getDate() {
        return date;
    }

    public Game() {

    }

    public Game(Date date, Player firstPlace, Player secondPlace, Player thirdPlace) {
        this.date = date;
        this.firstPlace = firstPlace;
        this.secondPlace = secondPlace;
        this.thirdPlace = thirdPlace;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Player getFirstPlace() {
        return firstPlace;
    }

    public void setFirstPlace(Player firstPlace) {
        this.firstPlace = firstPlace;
    }

    public Player getSecondPlace() {
        return secondPlace;
    }

    public void setSecondPlace(Player secondPlace) {
        this.secondPlace = secondPlace;
    }

    public Player getThirdPlace() {
        return thirdPlace;
    }

    public void setThirdPlace(Player thirdPlace) {
        this.thirdPlace = thirdPlace;
    }

    private Date date;
    private Player firstPlace;
    private Player secondPlace;
    private Player thirdPlace;
}