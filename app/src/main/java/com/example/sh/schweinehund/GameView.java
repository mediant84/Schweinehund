package com.example.sh.schweinehund;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends AppCompatActivity implements View.OnClickListener{

    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schweinehund);



        ImageButton add_button = findViewById(R.id.add_button);
        ImageButton brot_button = findViewById(R.id.storage_button);
        ImageButton burger_button = findViewById(R.id.store_button);
        TextView game_points = findViewById(R.id.points_input_string);
        ProgressBar progress = findViewById(R.id.progressBar3);
        final DBHelper dbHelper = new DBHelper(this);

        add_button.setOnClickListener(this);
        brot_button.setOnClickListener(this);
        burger_button.setOnClickListener(this);




        player = Player.load(dbHelper, "Schweinehund");     // Datenbank bereitstellen
        progress.setProgress(player.getProgress());               // Progressbar bestimmen (aus Datenbank)
        game_points.setText(player.getLevel().toString());        // Exp bestimmen (aus Datenbank)



    }
    @Override
    public void onClick(View v) {
        final DBHelper dbHelper = new DBHelper(this);
        player = Player.load(dbHelper, "Schweinehund");
        TextView game_points = findViewById(R.id.points_input_string);

        ProgressBar progress = findViewById(R.id.progressBar3);

        switch (v.getId()){
            // geht auf Activity umQuest hinzufügen
            case R.id.add_button:
                Intent add_quest = new Intent(this, AddQuestActivity.class);
                startActivity(add_quest);
                break;

            case R.id.storage_button:   // füttern mit 5 Exp
                if(player.getLevel()>=5) {
                    player.setProgress(5);
                    progress.setProgress(player.getProgress());
                    player.setLevel(-5);
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.essen);
                    mp.start();

                }
                else{
                    Context context = this;
                    CharSequence text = "Wenig Punkte!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                break;
            case R.id.store_button:     // füttern mit 10 Exp
                if(player.getLevel()>=10) {
                    player.setProgress(10);
                    progress.setProgress(player.getProgress());
                    player.setLevel(-10);
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.essen);
                    mp.start();
                }
                else {
                    Context context = this;
                    CharSequence text = "Wenig Punkte!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                break;

        }
        player.update(dbHelper);
        game_points.setText(player.getLevel().toString());  // Wert aktualisieren

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent app_quest = new Intent(this, MainActivity.class);
        startActivity(app_quest);
    }
}
