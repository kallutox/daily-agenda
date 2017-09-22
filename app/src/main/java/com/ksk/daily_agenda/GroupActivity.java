package com.ksk.daily_agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupActivity extends AppCompatActivity {
    private TextView groupNameView;
    private Button voting;
    private Intent intent;
    private long ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        setupUI();
    }
    private void setupUI(){
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        String groupName = bundle.getString(Groups.GROUPNAME_CODE);
        ID = bundle.getLong(Groups.GROUPID_CODE);
        groupNameView = (TextView) findViewById(R.id.groupname_display);
        groupNameView.setText(groupName);
        voting = (Button) findViewById(R.id.group_voting);
        voting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Votings.class);
                intent.putExtra(Groups.GROUPID_CODE, ID);
                startActivity(intent);
            }
        });
    }
}
