package com.ksk.daily_agenda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Isabella on 16.09.2017.
 */

public class NotesAdapter extends ArrayAdapter<NoteItem> {
    private ArrayList<NoteItem> taskList;
    private Context context;

    public NotesAdapter(Context context, ArrayList<NoteItem> listItems) {
        super(context, R.layout.listitem_tasklist, listItems);
        this.context = context;
        this.taskList = listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listitem_tasklist, null);

        }

        NoteItem task = taskList.get(position);

        if (task != null) {
            TextView taskName = (TextView) v.findViewById(R.id.task_name);
            TextView taskDate = (TextView) v.findViewById(R.id.task_date);

            taskName.setText(task.getName());
            taskDate.setText(task.getFormattedDate());
        }

        return v;
    }

}
