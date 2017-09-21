package com.ksk.daily_agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Vote extends AppCompatActivity {
    private TextView name;
    private ListView choicesAndStandings;
    Voting voting;
    private VotingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        getVoting();
        setupUI();
    }

    private void setupUI(){
        name = (TextView) findViewById(R.id.vote_name_display);
        name.setText(voting.toString());
        choicesAndStandings = (ListView) findViewById(R.id.vote_choices);
        adapter = new VotingAdapter(getApplicationContext(), voting);
        choicesAndStandings.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        choicesAndStandings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.voteFor(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getVoting(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString(Votings.VOTENAME_CODE);
        ArrayList<String> choices = bundle.getStringArrayList(Votings.VOTECHOICES_CODE);
        int[] votes = bundle.getIntArray(Votings.VOTEVOTINGS_CODE);
        voting = new Voting(name, choices, 0);
    }
}
