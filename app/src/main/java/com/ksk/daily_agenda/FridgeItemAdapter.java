package com.ksk.daily_agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Stefan on 22.09.2017.
 */

public class FridgeItemAdapter extends ArrayAdapter {
    private ArrayList<FridgeItem> fridgeList;
    private Context context;

    public FridgeItemAdapter(@NonNull Context context, ArrayList<FridgeItem> fridgeList) {
        super(context, R.layout.fridge_list_item, fridgeList);
        this.fridgeList = fridgeList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.fridge_list_item, null);
        }
        final FridgeItem item = fridgeList.get(position);
        if(item != null){
            Button decrease = (Button) v.findViewById(R.id.fridge_item_decrease);
            Button increase = (Button) v.findViewById(R.id.fridge_item_increase);
            final TextView piecesDisplay = (TextView) v.findViewById(R.id.fridge_item_count);
            TextView nameDisplay = (TextView) v.findViewById(R.id.fridge_item_name);
            piecesDisplay.setText(""+item.getPieces());
            nameDisplay.setText(item.getName());
            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.decrease();
                    piecesDisplay.setText(""+item.getPieces());
                }
            });
            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.increase();
                    piecesDisplay.setText(""+item.getPieces());
                }
            });
        }

        return v;
    }
}
