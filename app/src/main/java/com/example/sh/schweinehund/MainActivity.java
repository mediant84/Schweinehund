package com.example.sh.schweinehund;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
//import android.support.design.widget.BottomNavigationView;
//import android.support.design.widget.FloatingActionButton;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView header;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton add_button = findViewById(R.id.add_button);
        ImageButton game_button = findViewById(R.id.app_button);
        Button sport_button = findViewById(R.id.sport_button);
        Button ernaehrung_button = findViewById(R.id.ernaehrung_button);
        Button bildung_button = findViewById(R.id.bildung_button);

        add_button.setOnClickListener(this);
        game_button.setOnClickListener(this);
        sport_button.setOnClickListener(this);
        ernaehrung_button.setOnClickListener(this);
        bildung_button.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        // Bestimmung was passiert, wenn irgendeine Taste getippt wird
        switch (v.getId()){
            case R.id.add_button:
                Intent add_quest = new Intent(this, AddQuestActivity.class);
                startActivity(add_quest);
                break;
            case R.id.app_button:
                Intent start_game = new Intent(this, GameView.class);
                startActivity(start_game);
                break;
            case R.id.sport_button:
                Intent quest_view = new Intent(this, QuestActivity.class);
                quest_view.putExtra("headname", "SPORT");   // Übergabe an andere Activity
                startActivity(quest_view);
                break;
            case R.id.ernaehrung_button:
                Intent quest_view1 = new Intent(this, QuestActivity.class);
                quest_view1.putExtra("headname", "ERNÄHRUNG"); // Übergabe an andere Activity
                startActivity(quest_view1);
                break;
            case R.id.bildung_button:
                Intent quest_view2 = new Intent(this, QuestActivity.class);
                quest_view2.putExtra("headname", "BILDUNG"); // Übergabe an andere Activity
                startActivity(quest_view2);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        finishAffinity();

    }


}
