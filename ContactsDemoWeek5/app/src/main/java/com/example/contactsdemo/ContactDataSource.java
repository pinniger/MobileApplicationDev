package com.example.contactsdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class ContactDataSource {

    private SQLiteDatabase database;
    private ContactDBHelper dbHe1per;

    public ContactDataSource(Context context) {
        dbHe1per = new ContactDBHelper(context);
    }

    public boolean insertContact(Contact c){
        boolean didSucceed = false;
        try{
            ContentValues initialValues = setInitialValues(c);

            didSucceed = database.insert("contact",null,initialValues) > 0;
        } catch (Exception e){
            // do nothing
        }

        return didSucceed;
    }

    public boolean updateContact(Contact c){
        boolean didSucceed = false;
        try{
            Long rowId = (long)c.getContactID();
            ContentValues updateValues = setInitialValues(c);
            didSucceed = database.update("contact", updateValues, "_id=" + rowId, null) > 0;
        } catch (Exception e){
            // do nothing
        }

        return didSucceed;
    }

    private ContentValues setInitialValues(Contact c){
        ContentValues initialValues = new ContentValues();
        initialValues.put("contactname", c.getContactName());
        initialValues.put("streetaddress", c.getStreetAddress());
        initialValues.put("city", c.getCity());
        initialValues.put("state", c.getState());
        initialValues.put("zipcode", c.getZipCode());
        initialValues.put("phonenumber", c.getPhoneNumber());
        initialValues.put("cellnumber", c.getCellNumber());
        initialValues.put("email", c.geteMail());
        initialValues.put("birthday", String.valueOf(c.getBirthday().getTimeInMillis()));

        return initialValues;
    }

    public int getLastContactId(){
        int lastId = -1;
        try{
            String query = "Select MAX(_id) from contact";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e){
            lastId = -1;
        }
        return lastId;
    }

    public void open() throws SQLException {
        database = dbHe1per.getWritableDatabase();
    }

    public void close() {
        dbHe1per.close();
    }
}
