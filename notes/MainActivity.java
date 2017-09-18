package com.example.isabella.notes;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

//Übernommen aus den Übungsaufgaben

public class MainActivity extends AppCompatActivity {


    private ArrayList<NoteItem> tasks;
    private NotesDatabase notesDatabase;
    private NotesAdapter notes_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTaskList();
        initDatabase();
        initUI();
        refreshArrayList();
    }

    private void initDatabase() {
        notesDatabase = new NotesDatabase(this);
        notesDatabase.open();
    }

    private void refreshArrayList(){
        ArrayList tempList = notesDatabase.getAllToDoItems();
        tasks.clear();
        tasks.addAll(tempList);
        notes_adapter.notifyDataSetChanged();
    }

    private void initTaskList() {
        tasks = new ArrayList<NoteItem>();
        initListAdapter();
    }

    private void initUI() {
        initTaskButton();
        initListView();
        initDateField();
    }

    private void initTaskButton() {
        Button addTaskButton = (Button) findViewById(R.id.add_note_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInputToList();
            }
        });
    }

    private void addInputToList() {
        EditText edit = (EditText) findViewById(R.id.new_task_input);
        EditText dateEdit = (EditText) findViewById(R.id.set_date_input);
        String task = edit.getText().toString();
        String date = dateEdit.getText().toString();

        if (!task.equals("") && !date.equals("")) {
            edit.setText("");
            dateEdit.setText("");
            addNewTask(task, date);
        }
    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                removeTaskAtPosition(position);
                return true;
            }
        });
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.todo_list);
        notes_adapter = new NotesAdapter(this, tasks);
        list.setAdapter(notes_adapter);
    }

    private void addNewTask(String task, String date) {
        Date dueDate = getDateFromString(date);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(dueDate);

        NoteItem newTask = new NoteItem(task, cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));

        notesDatabase.insertToDoItem(newTask);
        refreshArrayList();
    }

    private void initDateField() {
        EditText dateEdit = (EditText) findViewById(R.id.set_date_input);
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDatePickerDialog();
            }
        });

    }

    private void removeTaskAtPosition(int position) {
        if (tasks.get(position) != null) {
            notesDatabase.removeToDoItem(tasks.get(position));
            refreshArrayList();
        }
    }

    public void showDatePickerDialog() {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    private Date getDateFromString(String dateString) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                Locale.GERMANY);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            // return current date as fallback
            return new Date();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                sortList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void sortList() {
        Collections.sort(tasks);
        notes_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notesDatabase.close();
    }
}

