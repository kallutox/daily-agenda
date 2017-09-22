package com.ksk.daily_agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class FridgeCreateItem extends AppCompatActivity {
    public static final String FRIDGEMENAME__CODE = "fridgemenamecode";
    public static final String FRIDGEMENUMPICK_CODE = "fridgemenumpickcode";
    EditText editName;
    Button createItem;
    NumberPicker numPick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge_create_item);
        setupUI();
    }

    private void setupUI(){
        editName = (EditText) findViewById(R.id.fridgeme_item_create_name);
        createItem = (Button) findViewById(R.id.fridge_item_create_finish);
        numPick = (NumberPicker) findViewById(R.id.fridgeme_item_create_numPick);
        numPick.setMaxValue(99999);
        createItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                int pieces = numPick.getValue();
                if(!name.equals("")){
                    Intent intent = new Intent(getApplicationContext(),FridgeMe.class);
                    intent.putExtra(FRIDGEMENAME__CODE,name);
                    intent.putExtra(FRIDGEMENUMPICK_CODE,pieces);
                    setResult(FridgeMe.CREATEITEM_RESULT,intent);
                    finish();
                }
                else {
                    editName.setHint("Bitte Gruppennamen eingeben!");
                    editName.setHintTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }
}
