package com.ksk.daily_agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Votings extends AppCompatActivity {
    public static final String VOTENAME_CODE = "votenamecode";
    public static final String VOTECHOICES_CODE = "votechoicescode";
    public static final String VOTEVOTINGS_CODE = "votevotescode";
    public static final int RESULTCODE_CREATEVOTING = 192;

    private ArrayAdapter adapter;
    private Button createVoting;
    private ListView votingListView;
    private ArrayList<Voting> votingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votings);
        setupUI();
    }

    private void setupUI(){
        createVoting = (Button) findViewById(R.id.create_voting);
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, votingList);
        votingListView = (ListView) findViewById(R.id.voting_list);
        votingListView.setAdapter(adapter);
        votingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Vote.class);
                intent.putExtra(VOTENAME_CODE,votingList.get(position).toString());
                intent.putExtra(VOTECHOICES_CODE,votingList.get(position).getChoices());
                intent.putExtra(VOTEVOTINGS_CODE,votingList.get(position).getVotes());
                startActivity(intent);
            }
        });
        createVoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateVoting.class);
                startActivityForResult(intent,RESULTCODE_CREATEVOTING);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        String name = bundle.getString(CreateVoting.VOTINGNAME_CODE);
        ArrayList<String> choices = bundle.getStringArrayList(CreateVoting.VOTINGCHOICES_CODE);
        votingList.add(new Voting(name, choices,0));
        adapter.notifyDataSetChanged();
    }
}
