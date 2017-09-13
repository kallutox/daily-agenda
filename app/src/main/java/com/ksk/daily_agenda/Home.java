package com.ksk.daily_agenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Home extends AppCompatActivity {
    Button homeButton1;
    Button homeButton2;
    public static final int REQUEST_CODE = 2;
    static long ID;
    SharedPreferences sP;
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
        homeButton2 = (Button) findViewById(R.id.home_button2);
        homeButton2.setEnabled(false);
        homeButton2.setBackgroundColor(getResources().getColor(R.color.red));
    }

    private void setupUI(){
        homeButton1 = (Button) findViewById(R.id.home_button1);
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
                out.close();
                in.close();
                socket.close();
                if(isLoggedIn)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        homeButton2.setEnabled(true);
                        homeButton2.setBackgroundColor(getResources().getColor(R.color.DAcolor));
                        getGroups();
                    }
                });
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
    }

    private void getGroups(){
        try {
            out.println("getGroups");
            out.flush();
            String line = in.readLine();
            while (!line.equals("end")){
                LinkedList<String> arguments = new LinkedList<String>();
                ArrayList<String> members = new ArrayList<String>();
                StringTokenizer sT = new StringTokenizer(line, " ");
                while(sT.hasMoreTokens()){
                    arguments.add(sT.nextToken());
                }
                long ID = Long.parseLong(arguments.get(1));
                for(int i=2; i<arguments.size(); i++){
                    members.add(arguments.get(i));
                }
                Group group = new Group(arguments.get(0), ID, members);
                groups.add(group);
                line = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Group> getGroupList() {
        return groups;
    }
}
