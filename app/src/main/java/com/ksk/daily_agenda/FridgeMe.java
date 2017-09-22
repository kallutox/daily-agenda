package com.ksk.daily_agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FridgeMe extends AppCompatActivity {
    public static final int CREATEITEM_RESULT = 3214;
    private ArrayList<FridgeItem> fridgeList = new ArrayList<>();
    private Button addItem;
    private ListView itemList;
    private FridgeItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_me);
        setupUI();
    }

    private void setupUI(){
        addItem = (Button) findViewById(R.id.fridgeme_item_add);
        itemList = (ListView) findViewById(R.id.fridgeme_item_list);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FridgeCreateItem.class);
                startActivityForResult(intent,CREATEITEM_RESULT);
            }
        });
        adapter = new FridgeItemAdapter(getApplicationContext(),fridgeList);
        itemList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        String name = bundle.getString(FridgeCreateItem.FRIDGEMENAME__CODE);
        int pieces = bundle.getInt(FridgeCreateItem.FRIDGEMENUMPICK_CODE);
        fridgeList.add(new FridgeItem(name,pieces));
        adapter.notifyDataSetChanged();
    }
}
