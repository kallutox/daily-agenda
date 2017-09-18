package com.example.isabella.notes;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Isabella on 16.09.2017.
 */

public class NoteItem implements Comparable<NoteItem> {

    private String name;
    private GregorianCalendar calendar;

    public NoteItem(String name, int day, int month, int year) {
        this.name = name;
        calendar = new GregorianCalendar(year, month, day);
    }


    public String getName() {
        return name;
    }

    public String getFormattedDate() {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        return df.format(calendar.getTime());
    }

    public Date getDueDate() {
        return calendar.getTime();
    }

    @Override
    public int compareTo(NoteItem another) {
        return getDueDate().compareTo(another.getDueDate());
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", Date: " + getFormattedDate();
    }
}

