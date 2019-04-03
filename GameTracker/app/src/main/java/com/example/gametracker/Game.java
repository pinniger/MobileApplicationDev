package com.example.gametracker;

public class Game {
    public String getDate() {
        return date;
    }

    public Game() {
        this.id = -1;
    }

    public Game(String date, int firstPlace, int secondPlace, int thirdPlace) {
        this.date = date;
        this.firstPlace = firstPlace;
        this.secondPlace = secondPlace;
        this.thirdPlace = thirdPlace;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFirstPlace() {
        return firstPlace;
    }

    public void setFirstPlace(int firstPlace) {
        this.firstPlace = firstPlace;
    }

    public int getSecondPlace() {
        return secondPlace;
    }

    public void setSecondPlace(int secondPlace) {
        this.secondPlace = secondPlace;
    }

    public int getThirdPlace() {
        return thirdPlace;
    }

    public void setThirdPlace(int thirdPlace) {
        this.thirdPlace = thirdPlace;
    }

    private int id;
    private String date;
    private int firstPlace;
    private int secondPlace;
    private int thirdPlace;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
