package com.example.recyclerview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MockContactData {

    public static List<Contact> getAll(){

        List<Contact> contacts = new ArrayList<>();

        Contact contact1 = new Contact();
        contact1.setContactName("John Doe");
        contact1.setCellNumber("9205551111");
        contacts.add(contact1);

        Contact contact2 = new Contact();
        contact2.setContactName("Jane Doe");
        contact2.setCellNumber("9205552222");
        contacts.add(contact2);

        Contact contact3 = new Contact();
        contact3.setContactName("Jack Doe");
        contact3.setCellNumber("9205553333");
        contacts.add(contact3);

        Contact contact4 = new Contact();
        contact4.setContactName("Jenny Doe");
        contact4.setCellNumber("9205554444");
        contacts.add(contact4);

        Contact contact5 = new Contact();
        contact5.setContactName("Jim Doe");
        contact5.setCellNumber("9205555555");
        contacts.add(contact5);

        Contact contact6 = new Contact();
        contact6.setContactName("Julie Doe");
        contact6.setCellNumber("9205556666");
        contacts.add(contact6);

        return contacts;
    }
}