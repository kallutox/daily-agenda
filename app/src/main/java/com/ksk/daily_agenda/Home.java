package com.ksk.daily_agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    Button homeButton1;
    Button homeButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupUI();
    }

    private void setupUI(){
        homeButton1 = (Button) findViewById(R.id.home_button1);
        homeButton2 = (Button) findViewById(R.id.home_button2);
        homeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Me.class);
                startActivity(intent);
            }
        });
        homeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Groups.class);
                startActivity(intent);
            }
        });
    }
}
