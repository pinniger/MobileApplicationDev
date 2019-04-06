package com.example.gametracker;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class Game {
    private String mDateFormat = "MM/dd/yy";
    private int id;
    private Date date;
    private int firstPlace;
    private int secondPlace;
    private int thirdPlace;

    public Date getDate() {
        return date;
    }

    public void setDateString(String d)  {
        SimpleDateFormat sdf = new SimpleDateFormat(mDateFormat);
        try {
            this.date = sdf.parse(d);
        } catch (Exception e){
            Log.d(TAG, "setDateString: " + e.getMessage());
        }
    }

    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat(mDateFormat);
        return sdf.format(date);
    }

    public Game() {
        this.id = -1;
    }

    public Game(Date date, int firstPlace, int secondPlace, int thirdPlace) {
        this.date = date;
        this.firstPlace = firstPlace;
        this.secondPlace = secondPlace;
        this.thirdPlace = thirdPlace;
    }

    public void setDate(Date date) {
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



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
