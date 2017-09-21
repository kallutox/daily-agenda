package com.ksk.daily_agenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    private TextView nameDisplay;
    private Button meButton;
    private Button groupsButton;
    public static final int REQUEST_CODE = 2;
    public static final String GETGROUP_CODE = "getgroupscode";
    static long ID;
    private SharedPreferences sP;
    private boolean initialized;
    private static ArrayList<Group> groups = new ArrayList<>();

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupPreConnection();
        if(initialized) {
            new Connector().execute();
        }
        setupUI();
    }

    //needs to be done before checking connection
    private void setupPreConnection(){
        sP = this.getPreferences(Context.MODE_PRIVATE);
        initialized = sP.getBoolean(getResources().getString(R.string.settings_init), false);
        if(!initialized){
            Intent intent = new Intent(getApplicationContext(), EditName.class);
            startActivityForResult(intent,REQUEST_CODE);
        }
        Home.ID = sP.getLong(getResources().getString(R.string.settings_ID),-1);
        groupsButton = (Button) findViewById(R.id.home_button2);
        groupsButton.setEnabled(false);
        groupsButton.setBackgroundColor(getResources().getColor(R.color.red));
    }

    private void setupUI(){
        String name = sP.getString(getResources().getString(R.string.settings_name),"user");
        nameDisplay = (TextView) findViewById(R.id.name_display);
        if(name != null) {
            nameDisplay.setText(name);
        }
        meButton = (Button) findViewById(R.id.home_button1);
        meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Me.class);
                startActivity(intent);
            }
        });
        groupsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Groups.class);
                startActivity(intent);
            }
        });
    }

    private class Connector extends AsyncTask {

        @Override
        protected Object doInBackground(Object... params) {
            try {
                socket = new Socket(InetAddress.getByName(getResources().getString(R.string.host_name)), 18712);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
                out.println("login " + sP.getLong(getResources().getString(R.string.settings_ID),-1));
                out.flush();
                boolean isLoggedIn = in.readLine().equals("ok");
                if(isLoggedIn)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        groupsButton.setEnabled(true);
                        groupsButton.setBackgroundColor(getResources().getColor(R.color.DAcolor));
                    }
                });
                out.close();
                in.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        long ID = bundle.getLong(EditName.PROFILEID_CODE);
        Home.ID = ID;
        String name = bundle.getString(EditName.PROFILENAME_CODE);
        SharedPreferences.Editor editor = sP.edit();
        editor.putBoolean(getResources().getString(R.string.settings_init), true);
        editor.putLong(getResources().getString(R.string.settings_ID),ID);
        editor.putString(getResources().getString(R.string.settings_name),name);
        editor.apply();
        setupPreConnection();
        new Connector().execute();
        setupUI();
    }



    public static ArrayList<Group> getGroupList() {
        return groups;
    }
}
