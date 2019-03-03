package com.example.gametracker;

public class Player {
    private String name;
    private String group;
    private String image;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    private int score;
    private int rank;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFirstPlaceFinishes() {
        return firstPlaceFinishes;
    }

    public void setFirstPlaceFinishes(int firstPlaceFinishes) {
        this.firstPlaceFinishes = firstPlaceFinishes;
    }

    public int getSecondPlaceFinishes() {
        return secondPlaceFinishes;
    }

    public void setSecondPlaceFinishes(int secondPlaceFinishes) {
        this.secondPlaceFinishes = secondPlaceFinishes;
    }

    public int getThirdPlaceFinishes() {
        return thirdPlaceFinishes;
    }

    public void setThirdPlaceFinishes(int thirdPlaceFinishes) {
        this.thirdPlaceFinishes = thirdPlaceFinishes;
    }

    private int firstPlaceFinishes;
    private int secondPlaceFinishes;
    private int thirdPlaceFinishes;

    public Player(String name, String group, String image) {
        this.name = name;
        this.group = group;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

