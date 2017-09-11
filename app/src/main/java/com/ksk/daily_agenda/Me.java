package com.ksk.daily_agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Me extends AppCompatActivity {
    Button notes;
    Button calendar;
    Button fridge;
    Button finances;
    Button map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
        setupUI();
    }

    private void setupUI(){
        notes = (Button) findViewById(R.id.me_notes);
        calendar = (Button) findViewById(R.id.me_calendar);
        fridge = (Button) findViewById(R.id.me_fridge);
        finances = (Button) findViewById(R.id.me_finances);
        map = (Button) findViewById(R.id.me_map);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        finances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
