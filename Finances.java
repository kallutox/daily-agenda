package com.ksk.daily_agenda;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kostja on 18.09.2017.
 */

public class Finances extends AppCompatActivity {

    Button add;
    Button withdraw;
    ListView list;
    TextView budget;
    EditText revenue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);
        setupUI();
    }

    private void setupUI() {
        add = (Button) findViewById(R.id.finances_button_add);
        withdraw = (Button) findViewById(R.id.finances_button_withdraw);
        list = (ListView) findViewById(R.id.finances_list);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
