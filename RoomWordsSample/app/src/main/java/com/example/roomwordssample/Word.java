package com.example.roomwordssample;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "word_table")
public class Word {



    @PrimaryKey(autoGenerate=true)
    @NonNull
    @ColumnInfo(name = "_id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public String getWord(){
        return this.mWord;
    }

    public void setWord(String word){
        this.mWord = word;
    }

    @NonNull
    @ColumnInfo(name = "part")
    private String mPart;

    public String getPart(){
        return this.mPart;
    }

    public void setPart(@NonNull String mPart) {
        this.mPart = mPart;
    }

    public Word(@NonNull String word, String part) {
        this.mWord = word;
        this.mPart = part;
    }
}