package com.example.gametracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecentWinner {
    private String name;
    private Date date;
    private String mDateFormat = "MM/dd/yy";

    private int playerId;

    public String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat(mDateFormat);
        return sdf.format(date);
    }

    public RecentWinner(String name, java.util.Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
