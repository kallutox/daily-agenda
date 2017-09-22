package com.ksk.daily_agenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class CreateGroup extends AppCompatActivity {
    EditText groupName;
    EditText addUser;
    Button createGroup;
    String gName;
    String member;
    SharedPreferences sP;
    private boolean isRegistered = false;
    private boolean checking = false;
    public static final String NAME_EXTRA = "groupName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        setupUI();
    }

    private void setupUI(){
        sP = getPreferences(Context.MODE_PRIVATE);
        groupName = (EditText) findViewById(R.id.createGroup_name);
        createGroup = (Button) findViewById(R.id.createGroup_finish);
        addUser = (EditText) findViewById(R.id.add_user_group);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checking) return;
                gName = groupName.getText().toString();
                member = addUser.getText().toString();
                if(gName.matches("^(([A-Z]|[a-z]|[0-9]){3,20})")) {
                    new GroupTest().execute();
                }
                else {
                    groupName.setHint("Bitte Gruppennamen eingeben!");
                    groupName.setHintTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }

    private void proceedToGroups(){
        Intent intent = new Intent(getApplicationContext(),Groups.class);
        intent.putExtra(NAME_EXTRA,gName);
        setResult(Groups.REQUEST_CODE, intent);
        finish();
    }

    private class GroupTest extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            checking = true;
            try {
                Socket socket = new Socket(InetAddress.getByName(getResources().getString(R.string.host_name)), 18712);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("login " + Home.ID);
                out.flush();
                out.println("createGroup " +gName+" "+member);
                out.flush();
                String answer = in.readLine();
                System.out.println(answer);
                isRegistered = answer.trim().equals("ok");
                out.close();
                in.close();
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!isRegistered) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                checking = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        proceedToGroups();
                    }
                });
            }
            checking = false;
            return null;
        }
    }
}
