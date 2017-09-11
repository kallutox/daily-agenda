package com.ksk.daily_agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Groups extends AppCompatActivity {
    private ArrayList<Group> groupArray = new ArrayList<Group>();
    private ArrayAdapter adapter;
    private Button createGroup;
    private ListView groupList;
    public static final int REQUEST_CODE = 1;
    public static final String GROUPNAME_EXTRA = "groupActivityExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        setupUI();
    }

    private void setupUI(){
        createGroup = (Button) findViewById(R.id.create_group);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateGroup.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, groupArray);
        groupList = (ListView) findViewById(R.id.group_list);
        groupList.setAdapter(adapter);
        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),GroupActivity.class);
                intent.putExtra(GROUPNAME_EXTRA, groupArray.get(position).toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
        }
        else {
            Bundle bundle = data.getExtras();
            String name = bundle.getString(CreateGroup.NAME_EXTRA);
            Group group = new Group(name,new int[10]);
            groupArray.add(group);
            adapter.notifyDataSetChanged();
        }
    }
}
