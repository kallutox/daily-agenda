package com.ksk.daily_agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Vote extends AppCompatActivity {
    public static final String VOTEIVCAFTER = "voteivcaftercode";
    public static final String VOTEVOTESAFTER = "votevotesaftercode";
    public static final String VOTEVCAFTER = "votevcaftercode";
    public static final String VOTEPOSAFTER = "voteposaftercode";
    private int position;
    private TextView name;
    private ListView choicesAndStandings;
    private Button commitVoting;
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
        commitVoting = (Button) findViewById(R.id.vote_commit);
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
        commitVoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Votings.class);
                intent.putExtra(VOTEIVCAFTER, voting.getInternVoteCount());
                intent.putExtra(VOTEVOTESAFTER, voting.getVotes());
                intent.putExtra(VOTEVCAFTER, voting.getVoteCount());
                intent.putExtra(VOTEPOSAFTER, position);
                setResult(Votings.RESULTCODE_COMMITVOTES, intent);
                finish();
            }
        });
    }

    private void getVoting(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString(Votings.VOTENAME_CODE);
        ArrayList<String> choices = bundle.getStringArrayList(Votings.VOTECHOICES_CODE);
        int[] votes = bundle.getIntArray(Votings.VOTEVOTINGS_CODE);
        int internVoteCount = bundle.getInt(Votings.VOTEINTERCOUNT_CODE);
        int voteCount = bundle.getInt(Votings.VOTEVOTECOUNT_CODE);
        position = bundle.getInt(Votings.VOTEPOSITION_CODE);
        voting = new Voting(name, choices, internVoteCount, votes, voteCount);
    }
}
