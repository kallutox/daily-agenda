package com.ksk.daily_agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {
    TextView groupNameView;
    Button notes;
    Button calendar;
    Button fridge;
    Button finances;
    Button map;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setupUI();
    }
    private void setupUI(){
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        String groupName = bundle.getString(Groups.GROUPNAME_EXTRA);
        groupNameView = (TextView) findViewById(R.id.groupname_display);
        groupNameView.setText(groupName);
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
