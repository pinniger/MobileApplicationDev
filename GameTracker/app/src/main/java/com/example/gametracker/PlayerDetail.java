package com.example.gametracker;

public class PlayerDetail extends Player{
    private int firstPlaceFinishes;
    private int secondPlaceFinishes;
    private int thirdPlaceFinishes;

    public int getTotalScore() {
        return this.getFirstPlaceFinishes() * 3 + this.getSecondPlaceFinishes() * 2 + this.getThirdPlaceFinishes();
    }


    @Override
    public int getFirstPlaceFinishes() {
        return firstPlaceFinishes;
    }

    @Override
    public void setFirstPlaceFinishes(int firstPlaceFinishes) {
        this.firstPlaceFinishes = firstPlaceFinishes;
    }

    @Override
    public int getSecondPlaceFinishes() {
        return secondPlaceFinishes;
    }

    @Override
    public void setSecondPlaceFinishes(int secondPlaceFinishes) {
        this.secondPlaceFinishes = secondPlaceFinishes;
    }

    @Override
    public int getThirdPlaceFinishes() {
        return thirdPlaceFinishes;
    }

    @Override
    public void setThirdPlaceFinishes(int thirdPlaceFinishes) {
        this.thirdPlaceFinishes = thirdPlaceFinishes;
    }
}
