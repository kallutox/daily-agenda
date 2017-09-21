package com.ksk.daily_agenda;

import java.util.ArrayList;

/**
 * Created by Stefan on 21.09.2017.
 */

public class Voting {
    private String name;
    private ArrayList<String> choices;
    private int[] votes;
    private double[] standings;
    private int voteCount = 0;
    private int internVoteCount = 0;

    public Voting(String name, ArrayList<String> choices, int internVoteCount){
        this.name = name;
        this.internVoteCount = internVoteCount;
        this.choices = choices;
        votes = new int[choices.size()];
        standings = new double[choices.size()];
        for(int i=0; i < choices.size(); i++){
            votes[i] = 0;
            standings[i] = 0.0;
        }
    }

    public void voteFor(int index){
        if(internVoteCount < 2){
            internVoteCount++;
            voteCount++;
            votes[index]++;
            calcStandings();
        }
    }

    private void calcStandings(){
        for(int i=0; i < votes.length; i++){
            standings[i] = votes[i]/(double)voteCount;
        }
    }

    public int[] getStandings(){
        int[] formattedStandings = new int[standings.length];
        for (int i=0; i<standings.length;i++){
            formattedStandings[i] = (int) (standings[i] * 100);
        }
        return formattedStandings;
    }

    public int[] getVotes(){
        return votes;
    }

    public ArrayList<String> getChoices(){
        return choices;
    }

    public void setStandings(int[] votes){
        if(votes.length == choices.size()){
            this.votes = votes;
            int sum = 0;
            for(int i=0; i<votes.length; i++){
                sum += votes[i];
            }
            voteCount = sum;
            if(voteCount != 0){
                calcStandings();
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
