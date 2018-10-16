package com.example.sh.schweinehund;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class QuestView extends ArrayAdapter<Quest> {

    Player player;
    MediaPlayer mp;

    public QuestView(Context context, ArrayList<Quest> quests) {
        super(context, 0, quests);
    }

    public View getView(final int position, View listItemView, ViewGroup parent) {
        if (listItemView == null) {
            // Layout für einzelne Quest nutzen
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.quest_einzeln, parent, false);
        }
        Quest currentQuest = getItem(position);

        MyTextView myTextView = listItemView.findViewById(R.id.itemName);
        myTextView.setText(currentQuest.getName().toString());
        listItemView.setBackgroundResource(R.color.progressBarTransparent50);

        Button expValue = listItemView.findViewById(R.id.expValue);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "americanTypwriterRegular.ttf");
        expValue.setTypeface(font);

        expValue.setText(currentQuest.getExpValue().toString() + " exp");

        final DBHelper dbHelper = new DBHelper(this.getContext());

        mp = null;

        player = Player.load(dbHelper, "Schweinehund");

        listItemView.setTag(currentQuest);

        final View finalListItemView = listItemView;
        expValue.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(View v) {
                Quest currentQuest = getItem(position);
                removeListItem(finalListItemView, currentQuest);
                longclick(dbHelper, currentQuest, player);
                notifyDataSetChanged();
                return true;
            }
        });

        return listItemView;

    }
    // Quest aus der Liste entfernen
    protected void removeListItem(final View rowView, final Quest position) {
        final Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.fadeout);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {

            public void run() {
                rowView.startAnimation(animation);
                remove(position);
                notifyDataSetChanged();
            }
        }, 0);

    }
    // Quest beenden
    public void longclick(DBHelper dbHelper, Quest quest, Player player) {
        quest.setCompleted(1);
        quest.update(dbHelper);

        player.setLevel(quest.getExpValue());       // Exp hinzufügen

        // ton abspielen
        MediaPlayer mp = MediaPlayer.create(this.getContext(), R.raw.complete);
        mp.start();

        player.update(dbHelper);

        // Info über Beendigung zeigen
        Context context = this.getContext();
        CharSequence text = "Quest Beendet!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}