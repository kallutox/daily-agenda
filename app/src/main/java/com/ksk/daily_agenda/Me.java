package com.ksk.daily_agenda;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Me extends AppCompatActivity {
    Button notes;
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
        fridge = (Button) findViewById(R.id.me_fridge);
        finances = (Button) findViewById(R.id.me_finances);
        map = (Button) findViewById(R.id.me_map);
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FridgeMe.class);
                startActivity(intent);
            }
        });
        finances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Finances.class);
                startActivity(intent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Diese Funktion ist noch nicht verfügbar!",Toast.LENGTH_LONG).show();
            }
        });
    }
}
