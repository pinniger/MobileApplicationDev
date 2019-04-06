package com.example.gametracker;

public class Player {
    private int id;
    private String name;
    private String group;

    public Player(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public Player(){}

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

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return this.id;
    }
}

