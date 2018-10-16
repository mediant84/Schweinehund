package com.example.sh.schweinehund;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Date;

public class AddQuestActivity extends AppCompatActivity implements View.OnClickListener{

    EditText quest_name;    // Name bestimmen
    EditText quest_exp;     // EXP bestimmen
    Spinner quest_cat;      // Kategorie bestimmen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        ImageButton app_button = findViewById(R.id.app_button);
        quest_name = findViewById(R.id.name_input);
        quest_exp = findViewById(R.id.point_input);
        quest_cat = findViewById(R.id.category_spinner);

        app_button.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // Geht auf Aktivity mit Schweinehund
            case R.id.app_button:
                Intent app_quest = new Intent(this, GameView.class);
                startActivity(app_quest);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent app_quest = new Intent(this, MainActivity.class);
        startActivity(app_quest);
    }

    // Questannahme
    public void addQuest(View button){
        DBHelper dbHelper = new DBHelper(this);
        String name = quest_name.getText().toString();

        Integer expValue= Integer.parseInt(quest_exp.getText().toString());
        // EXP begränzung
        if(expValue>10)
            expValue=10;
        if(expValue<0)
            expValue=0;

        Integer categoryId = quest_cat.getSelectedItemPosition() +1;

        Quest quest = new Quest(name, expValue, categoryId);
        quest.save(dbHelper);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
