package com.example.sh.schweinehund;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.sh.schweinehund.DBHelper.QUEST_TABLE_NAME;

public class QuestActivity extends AppCompatActivity implements View.OnClickListener  {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ImageButton add_button = findViewById(R.id.add_button);
        ImageButton game_button = findViewById(R.id.app_button);

        add_button.setOnClickListener(this);
        game_button.setOnClickListener(this);

        // Bestimmung der Kopfbeschriftung anhand gewählte Kategorie
        String head;
        head = getIntent().getExtras().getString("headname");
        TextView head_text = findViewById(R.id.textView_main);
        head_text.setText(head);

        final DBHelper dbHelper = new DBHelper(this);
        String  cat=null;
        switch (head){
            case "SPORT":
                cat = "1";
                break;
            case "ERNÄHRUNG":
                cat = "2";
                break;
            case "BILDUNG":
                cat = "3";
                break;
        }
        // Alle nicht beendete Quests entsprechend Kategorie von DB ziehen
        ArrayList<Quest> questList = Quest.allIncomplete(dbHelper,cat);

        final QuestView questAdapter = new QuestView(this, questList);
        final ListView listView = findViewById(R.id.questList);
        // Als Liste ausgeben
        listView.setAdapter(questAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_button:
                Intent add_quest = new Intent(this, AddQuestActivity.class);
                startActivity(add_quest);
                break;
            case R.id.app_button:
                Intent start_game = new Intent(this, GameView.class);
                startActivity(start_game);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent app_quest = new Intent(this, MainActivity.class);
        startActivity(app_quest);
    }

    // Wenn ein Element aus der Liste getippt wird
    // Ganze Questinformation an weitere Activity übergeben
    public void getQuest(View listItem){
        Quest quest = (Quest) listItem.getTag();
        Intent i = new Intent(this, QuestEinzeln.class);
        i.putExtra("name", quest.getName());
        i.putExtra("expValue", quest.getExpValue().toString());
        i.putExtra("category", quest.showCategoryNameByNumber(quest.getCategory()));
        i.putExtra("date", quest.getDate().getTime());
        startActivity(i);
    }
}
