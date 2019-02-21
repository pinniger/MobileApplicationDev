package com.example.contactsdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class ContactDataSource {

    private SQLiteDatabase database;
    private ContactDBHelper dbHe1per;

    public ContactDataSource(Context context) {
        dbHe1per = new ContactDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHe1per.getWritableDatabase();
    }

    public void close() {
        dbHe1per.close();
    }
}
