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
import java.util.StringTokenizer;

public class Votings extends AppCompatActivity {
    public static final String VOTENAME_CODE = "votenamecode";
    public static final String VOTECHOICES_CODE = "votechoicescode";
    public static final String VOTEVOTINGS_CODE = "votevotescode";
    public static final String VOTEINTERCOUNT_CODE = "voteinternvotecountcode";
    public static final String VOTEVOTECOUNT_CODE = "votevotecoundcode";
    public static final String VOTEPOSITION_CODE = "votepositioncode";
    public static final int RESULTCODE_COMMITVOTES = 9329;
    public static final int RESULTCODE_CREATEVOTING = 192;

    private long ID;

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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ID = bundle.getLong(Groups.GROUPID_CODE);
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
                intent.putExtra(VOTEINTERCOUNT_CODE,votingList.get(position).getInternVoteCount());
                intent.putExtra(VOTEVOTECOUNT_CODE,votingList.get(position).getVoteCount());
                intent.putExtra(VOTEPOSITION_CODE,position);
                startActivityForResult(intent, RESULTCODE_COMMITVOTES);
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
        if(resultCode == RESULTCODE_CREATEVOTING){
            Bundle bundle = data.getExtras();
            String name = bundle.getString(CreateVoting.VOTINGNAME_CODE);
            ArrayList<String> choices = bundle.getStringArrayList(CreateVoting.VOTINGCHOICES_CODE);
            votingList.add(new Voting(name, choices,0));
            adapter.notifyDataSetChanged();
        }
        if(resultCode == RESULTCODE_COMMITVOTES){
            Bundle bundle = data.getExtras();
            int internVoteCount = bundle.getInt(Vote.VOTEIVCAFTER);
            int[] votes = bundle.getIntArray(Vote.VOTEVOTESAFTER);
            int voteCount = bundle.getInt(Vote.VOTEVCAFTER);
            int position = bundle.getInt(Vote.VOTEPOSAFTER);
            votingList.get(position).setVotingData(internVoteCount,votes,voteCount);
        }
    }

    private class getVotingsTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                Socket socket = new Socket(InetAddress.getByName(getResources().getString(R.string.host_name)), 18712);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println("getVotings");
                out.flush();
                if(in.readLine().equals("start")){
                    String line = in.readLine();
                    StringTokenizer sT;
                    while (!line.equals("end")){
                        String name;
                        ArrayList<String> choices = new ArrayList<>();
                        int internVoteCount;
                        int voteCount;
                        int[] votes;
                        sT = new StringTokenizer(line);
                        name = sT.nextToken();
                        while (sT.hasMoreTokens()){
                            choices.add(sT.nextToken());
                        }
                        line = in.readLine();
                        sT = new StringTokenizer(line);
                        internVoteCount = Integer.parseInt(sT.nextToken());
                        voteCount = Integer.parseInt(sT.nextToken());
                        votes = new int[choices.size()];
                        for(int i=0; i<votes.length; i++){
                            votes[i] = Integer.parseInt(sT.nextToken());
                        }
                        votingList.add(new Voting(name,choices,internVoteCount,votes,voteCount));
                        line = in.readLine();
                    }
                }
                adapter.notifyDataSetChanged();
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
