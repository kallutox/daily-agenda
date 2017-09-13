package com.ksk.daily_agenda;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Stefan on 10.09.2017.
 */

public class Group implements Serializable {
    private String name;
    private long ID;
    private ArrayList<String> members;

    public Group(String name, long ID, ArrayList<String> members){
        this.name = name;
        this.ID = ID;
        this.members = members;
    }

    @Override
    public String toString() {
        return name + "@" + String.valueOf(ID);
    }

    @Override
    public boolean equals(Object o) {
        try {
            Group otherGroup = (Group) o;
            if(otherGroup.ID == ID && otherGroup.name.equals(name)) return true;
        } catch(ClassCastException e) {
            return false;
        }
        return false;
    }
}
