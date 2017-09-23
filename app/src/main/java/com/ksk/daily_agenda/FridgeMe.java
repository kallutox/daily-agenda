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
    private FridgeMeDB dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_me);
        setupDataBase();
        setupUI();
        updateFridgeList();
    }

    private void setupDataBase(){
        dataBase = new FridgeMeDB(this);
        dataBase.open();
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
        updateFridgeList();
    }

    private void updateFridgeList(){
        fridgeList.clear();
        fridgeList = dataBase.getAllFridgeItems();
        adapter = new FridgeItemAdapter(getApplicationContext(),fridgeList);
        itemList.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        String name = bundle.getString(FridgeCreateItem.FRIDGEMENAME__CODE);
        int pieces = bundle.getInt(FridgeCreateItem.FRIDGEMENUMPICK_CODE);
        dataBase.insertFridgeItem(new FridgeItem(name,pieces));
        updateFridgeList();
    }
}
