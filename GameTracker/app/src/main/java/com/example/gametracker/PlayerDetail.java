package com.example.gametracker;

public class PlayerDetail extends Player{
    private int firstPlaceFinishes;
    private int secondPlaceFinishes;
    private int thirdPlaceFinishes;

    public int getTotalScore() {
        return this.getFirstPlaceFinishes() * 3 + this.getSecondPlaceFinishes() * 2 + this.getThirdPlaceFinishes();
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

}
