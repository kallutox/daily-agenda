package com.ksk.daily_agenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Kostja on 18.09.2017.
 *
 * Code verwendet aus Uebung 04 "ToDoList", abgeaendert
 */

public class Finances extends AppCompatActivity {

    private ArrayList<FinanceItem> items;
    private FinancesAdapter items_adapter;
    private FinancesDB financesDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);
        initItemList();
        initDatabase();
        initUI();
        refreshArrayList();
    }

    private void initDatabase(){
        financesDB = new FinancesDB(this);
        financesDB.open();
    }

    private void refreshArrayList(){
        ArrayList tempList = financesDB.getAllFinanceItems();
        items.clear();
        items.addAll(tempList);
        items_adapter.notifyDataSetChanged();
    }

    private void initItemList(){
        items = new ArrayList<FinanceItem>();
        initListAdapter();
    }

    private void initUI(){
        initButtons();
    }

    private void initButtons(){
        Button addButton = (Button) findViewById(R.id.finances_button_add);
        Button withdrawButton = (Button) findViewById(R.id.finances_button_withdraw);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addInputToList();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    withdrawInputToList();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void withdrawInputToList() throws ParseException {
        EditText edit = (EditText) findViewById(R.id.finances_revenue_edit);
        TextView budgetDefault = (TextView) findViewById(R.id.finances_budget_default);
        String revenue = edit.getText().toString();
        String budget = budgetDefault.getText().toString();

        if (!revenue.equals("") && !budget.equals("")) {
            edit.setText("000.00");

            NumberFormat format = NumberFormat.getInstance(Locale.GERMANY);
            Number numberBudget = format.parse(budget);
            double budgetDigit = numberBudget.doubleValue();

            Number numberRevenue = format.parse(revenue);
            double revenueDigit = numberRevenue.doubleValue();

            budgetDigit = budgetDigit - revenueDigit;
            String budgetString = String.valueOf(budgetDigit);

            budgetDefault.setText(budgetString);

            addNewItem(budget, revenue);
        }
    }

    private void addInputToList() throws ParseException {
        EditText edit = (EditText) findViewById(R.id.finances_revenue_edit);
        TextView budgetDefault = (TextView) findViewById(R.id.finances_budget_default);
        String revenue = edit.getText().toString();
        String budget = budgetDefault.getText().toString();

        if (!revenue.equals("") && !budget.equals("")) {
            edit.setText("000.00");

            NumberFormat format = NumberFormat.getInstance(Locale.GERMANY);
            Number numberBudget = format.parse(budget);
            double budgetDigit = numberBudget.doubleValue();

            Number numberRevenue = format.parse(revenue);
            double revenueDigit = numberRevenue.doubleValue();

            budgetDigit = budgetDigit + revenueDigit;
            String budgetString = String.valueOf(budgetDigit);

            budgetDefault.setText(budgetString);

            addNewItem(budget, revenue);
        }
    }

    private void addNewItem(String budget, String revenue){
        FinanceItem newItem = new FinanceItem(budget, revenue);
        FinancesDB.insertFinanceItem(newItem);
        refreshArrayList();
    }

    private void initListAdapter(){
        ListView list = (ListView) findViewById(R.id.finances_list);
        items_adapter = new FinancesAdapter(this, items);
        list.setAdapter(items_adapter);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        financesDB.close();
    }

}
