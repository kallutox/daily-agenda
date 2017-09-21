package com.ksk.daily_agenda;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

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
        new GroupsTask().execute();
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
            Group group = new Group(name,1234,new ArrayList<String>());
            groupArray.add(group);
            adapter.notifyDataSetChanged();
        }
    }

    private class GroupsTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object... params) {
            return null;
        }
    }

    private void getGroups(){
        try {
            Socket socket = new Socket(InetAddress.getByName(getResources().getString(R.string.host_name)), 18712);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("getGroups");
            out.flush();
            String line = in.readLine();
            if(line == "start") {
                line = in.readLine();
                while (!line.equals("end")) {
                    LinkedList<String> arguments = new LinkedList<String>();
                    ArrayList<String> members = new ArrayList<String>();
                    StringTokenizer sT = new StringTokenizer(line, " ");
                    while (sT.hasMoreTokens()) {
                        arguments.add(sT.nextToken());
                    }
                    long ID = Long.parseLong(arguments.get(1));
                    for (int i = 2; i < arguments.size(); i++) {
                        members.add(arguments.get(i));
                    }
                    Group group = new Group(arguments.get(0), ID, members);
                    groupArray.add(group);
                    line = in.readLine();
                }
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
