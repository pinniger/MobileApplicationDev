package com.example.recyclerview;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class ContactUnitTests {

    private final Calendar cal = Calendar.getInstance();
    @Test
    public void IsValid_ReturnsTrue_IfCorrect (){
        Contact c = new Contact();
        c.setContactName("Name");
        cal.set(2000, 1, 1);
        c.setBirthday(cal);

        assertTrue(c.isValid());
    }

    @Test
    public void IsValid_ReturnsFalse_ForNoName (){
        Contact c = new Contact();
        c.setContactName("");
        cal.set(2000, 1, 1);
        c.setBirthday(cal);

        assertFalse(c.isValid());
    }

    @Test
    public void IsValid_ReturnsFalse_ForBirthdayGreaterThanToday (){
        Contact c = new Contact();
        c.setContactName("");
        cal.set(2020, 1, 1);
        c.setBirthday(cal);

        assertFalse(c.isValid());
    }

    @Test
    public void IsValid_ReturnsFalse_ForEmptyBirthday (){
        Contact c = new Contact();
        c.setContactName("");
        assertFalse(c.isValid());
    }

    @Test(expected = NullPointerException.class)
    public void IsValid_ThrowsException_WhenNameIsNotSet (){
        Contact c = new Contact();
        c.isValid();
    }
}
