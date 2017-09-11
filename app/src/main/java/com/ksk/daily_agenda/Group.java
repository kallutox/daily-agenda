package com.ksk.daily_agenda;

/**
 * Created by Stefan on 10.09.2017.
 */

public class Group {
    private String name;
    private static int ID = 0;
    private int[] members;

    public Group(String name,int[] members){
        this.name = name;
        this.members = members;
    }

    @Override
    public String toString() {
        return name;
    }
}
