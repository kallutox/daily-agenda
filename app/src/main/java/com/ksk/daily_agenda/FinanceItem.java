package com.ksk.daily_agenda;

/**
 * Created by Kostja on 18.09.2017.
 */

public class FinanceItem {
    private String budget;
    private String revenue;

    public FinanceItem(String budget, String revenue){
        this.budget = budget;
        this.revenue = revenue;
    }

    public String getBudget(){return budget;}

    public String getRevenue(){return revenue;}

    public String toSting(){return "Budget: " + getBudget() +", last Revenue: " + getRevenue();}
}
