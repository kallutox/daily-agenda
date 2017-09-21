package com.ksk.daily_agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Stefan on 21.09.2017.
 */

//benutzte diese Seite für erstellen des customized ArrayAdapter https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/

public class VotingAdapter extends ArrayAdapter<String>{
    private ArrayList<String> choices;
    private Context context;
    private Voting voting;

    public VotingAdapter(@NonNull Context context, Voting voting) {
        super(context, R.layout.vote_list_item, voting.getChoices());
        this.voting = voting;
        this.choices = voting.getChoices();
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.vote_list_item, null);
        }
        String s = choices.get(position);
        if(s != null){
            TextView choicename = (TextView) v.findViewById(R.id.vote_choice_name);
            ProgressBar standings = (ProgressBar) v.findViewById(R.id.vote_standings);
            choicename.setText(s);
            standings.setProgress((int)voting.getStandings()[position]);
        }
        for(int i = 0; i< voting.getStandings().length; i++){
            System.out.println(""+voting.getStandings()[i]);
        }
        return v;
    }

    public void voteFor(int index){
        voting.voteFor(index);
    }
}