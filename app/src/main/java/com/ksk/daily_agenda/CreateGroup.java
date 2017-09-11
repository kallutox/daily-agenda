package com.ksk.daily_agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateGroup extends AppCompatActivity {
    EditText groupName;
    Button createGroup;
    public static final String NAME_EXTRA = "groupName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        setupUI();
    }

    private void setupUI(){
        groupName = (EditText) findViewById(R.id.createGroup_name);
        createGroup = (Button) findViewById(R.id.createGroup_finish);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gName = groupName.getText().toString();
                if(gName.equals("")) {
                    groupName.setHint("Bitte Gruppennamen eingeben!");
                    groupName.setHintTextColor(getResources().getColor(R.color.red));
                }
                else {
                    Intent intent = new Intent(getApplicationContext(),Groups.class);
                    intent.putExtra(NAME_EXTRA,gName);
                    setResult(Groups.REQUEST_CODE, intent);
                    finish();
                }
            }
        });
    }
}
