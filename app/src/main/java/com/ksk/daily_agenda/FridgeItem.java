package com.ksk.daily_agenda;

/**
 * Created by Stefan on 22.09.2017.
 */

public class FridgeItem {
    private String name;
    private int pieces;

    public FridgeItem(String name, int pieces){
        this.name = name;
        this.pieces = pieces;
    }

    public String getName(){
        return name;
    }

    public int getPieces(){
        return pieces;
    }

    public void increase(){
        pieces++;
    }

    public void decrease(){
        if(pieces > 0){
            pieces--;
        }
    }
}
