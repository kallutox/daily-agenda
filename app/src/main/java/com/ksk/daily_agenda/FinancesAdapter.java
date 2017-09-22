package com.ksk.daily_agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kostja on 18.09.2017.
 */

public class FinancesAdapter extends ArrayAdapter<FinanceItem>{
    private ArrayList<FinanceItem> financeList;
    private Context context;


    public FinancesAdapter(Context context, ArrayList<FinanceItem> financeItems){
        super(context, R.layout.activity_finance_list, financeItems);
        this.context = context;
        this.financeList = financeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup paren){
        View v = convertView;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_finance_list, null);
        }

        FinanceItem task = financeList.get(position);

        if(task != null){
            TextView financeBudget = (TextView) v.findViewById(R.id.finance_list_budget);
            TextView financeRevenue = (TextView) v.findViewById(R.id.finance_list_revenue);

            financeBudget.setText(task.getBudget());
            financeRevenue.setText(task.getRevenue());
        }

        return v;
    }
}
