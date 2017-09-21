package com.ksk.daily_agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CreateVoting extends AppCompatActivity {
    public static final String VOTINGNAME_CODE = "votingnamecode";
    public static final String VOTINGCHOICES_CODE = "votingchoicescode";
    private ArrayList<String> choices = new ArrayList<>();
    private Button addChoice;
    private Button createVoting;
    private EditText editName;
    private EditText editChoice;
    private ListView choicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voting);
        setupUI();
    }

    private void setupUI(){
        addChoice = (Button) findViewById(R.id.createVoting_choice_add);
        createVoting = (Button) findViewById(R.id.createVoting_finish);
        editName = (EditText) findViewById(R.id.createVoting_name);
        editChoice = (EditText) findViewById(R.id.createVoting_choice);
        choicesList = (ListView) findViewById(R.id.createVoting_choices_list);
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, choices);
        choicesList.setAdapter(adapter);
        addChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choice = editChoice.getText().toString();
                if(!choice.equals("")){
                    choices.add(choice);
                    editChoice.setText("");
                    adapter.notifyDataSetChanged();
                }
                else {
                    editChoice.setHint("Bitte Abstimmungsnamen eingeben!");
                    editChoice.setHintTextColor(getResources().getColor(R.color.red));
                }
            }
        });
        createVoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                if(!name.equals("") & choices.size() != 0){
                    Intent intent = new Intent(getApplicationContext(),Votings.class);
                    intent.putExtra(VOTINGNAME_CODE,name);
                    intent.putExtra(VOTINGCHOICES_CODE, choices);
                    setResult(Votings.RESULTCODE_CREATEVOTING, intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Bitte Abstimmungsnamen eingeben oder Antwortmöglichkeit hinzufügen!",Toast.LENGTH_LONG).show();
                    editName.setHintTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }
}
