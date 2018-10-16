package com.example.sh.schweinehund;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.sql.Date;
import java.text.SimpleDateFormat;



public class QuestEinzeln extends AppCompatActivity {

    MyEditText name;
    MyTextView expValue;
    MyTextView category;
    MyTextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_info);

        Bundle extras = getIntent().getExtras();
        String questName = extras.getString("name");
        String questExpValue = extras.getString("expValue");
        String questCategory = extras.getString("category");
        Long questDate = extras.getLong("date");

        name = findViewById(R.id.nameViews);
        name.setText(questName);

        expValue = findViewById(R.id.expView);
        expValue.setText(questExpValue + " exp");

        category = findViewById(R.id.categoryView);
        category.setText("Das ist Kategorie: " + questCategory);

        date = findViewById(R.id.dateView);
        date.setText(toDate(questDate));
    }
    // Datum formatieren
    private String toDate(Long timestamp) {
        Date date = new Date(Long.parseLong(String.valueOf(timestamp)));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        return sdf.format( date );
    }

    public void backToHome(View button){
             super.onBackPressed();
    }

    public void deleteQuest(View button){
        Bundle extras = getIntent().getExtras();
        Integer id = extras.getInt("id");
        DBHelper dbHelper = new DBHelper(this);
        Quest.delete(dbHelper, id);
        Intent intent = new Intent(this, QuestActivity.class);
        startActivity(intent);
    }
}

