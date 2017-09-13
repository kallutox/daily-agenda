package com.ksk.daily_agenda;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class EditName extends AppCompatActivity {
    public static final String PROFILENAME_CODE = "profilename_code";
    public static final String PROFILEID_CODE = "profileid_code";
    TextView informationText;
    EditText nameEdit;
    Button setName;
    String name;
    boolean isRegistered = false;
    private boolean checking = false;
    private long ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        setupUI();
    }

    private void setupUI(){
        informationText = (TextView) findViewById(R.id.info_text_nameEdit);
        nameEdit = (EditText) findViewById(R.id.name_edit);
        setName = (Button) findViewById(R.id.set_name);
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checking) return;
                name = nameEdit.getText().toString();
                if(name.matches("^(([A-Z]|[a-z]|[0-9]){3,20})")) {
                    new NameTest().execute();
                }
                else {
                    nameEdit.setBackgroundColor(getResources().getColor(R.color.red));
                }
            }
        });
    }

    private void proceedToHome(){
        Intent intent = new Intent(getApplicationContext(), Home.class);
        intent.putExtra(PROFILENAME_CODE,name);
        intent.putExtra(PROFILEID_CODE, ID);
        setResult(Home.REQUEST_CODE,intent);
        Toast.makeText(getApplicationContext(), "Register complete", Toast.LENGTH_LONG).show();
        finish();
    }

    private class NameTest extends AsyncTask{

        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        @Override
        protected Object doInBackground(Object... params) {
            checking = true;
            try {
                socket = new Socket(InetAddress.getByName(getResources().getString(R.string.host_name)), 18712);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream());
                out.println("new " + name);
                out.flush();
                String answer = in.readLine();
                isRegistered = answer.startsWith("true");
                if(isRegistered) {
                    ID = Long.parseLong(answer.substring(5));
                }
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
                        Toast.makeText(getApplicationContext(), "samsing went rong", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                checking = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        proceedToHome();
                    }
                });
            }
            checking = false;
            return null;
        }
    }
}
