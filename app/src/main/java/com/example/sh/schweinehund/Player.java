package com.example.sh.schweinehund;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.example.sh.schweinehund.DBHelper.PROFILE_COLUMN_ID;
import static com.example.sh.schweinehund.DBHelper.PROFILE_COLUMN_NAME;
import static com.example.sh.schweinehund.DBHelper.PROFILE_COLUMN_LEVEL;
import static com.example.sh.schweinehund.DBHelper.PROFILE_TABLE_NAME;
import static com.example.sh.schweinehund.DBHelper.PROFILE_COLUMN_PROGRESS;


public class Player {

    private String name;
    private int id;
    private Integer level;
    private Integer progress;


    public Player(int id,String name,Integer level, Integer progress) {
        this.name = name;
        this.id = id;
        this.level = level;
        this.progress = progress;
    }

    // DB laden
    public static Player load(DBHelper dbHelper, String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // DB lesbar
        String where = " WHERE name=?";     // suchen nach Profilname
        String[] whereArgs = new String[] {String.valueOf(name)};

        Cursor cursor = db.rawQuery("SELECT * FROM " + PROFILE_TABLE_NAME + where, whereArgs);
        Player player = null;
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_ID));
            String userName = cursor.getString(cursor.getColumnIndex(PROFILE_COLUMN_NAME));
            Integer level = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_LEVEL));
            Integer progress = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_PROGRESS));
            cursor.close();
            player = new Player(id, userName, level, progress);
        }
        return player;
    }

    protected String getName() {
        return name;
    }

    protected Integer getLevel() {
        return level;
    }

    protected Integer getProgress() {
        return progress;
    }

    public Integer setProgress(int exp){
        progress = getProgress() + exp;
        return getProgress();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer setLevel(int exp){
        level = getLevel() + exp;
        return getLevel();
    }

    public Integer setLevel(){
        Integer totalExperience = getLevel();
        Integer levelCalc = (totalExperience / 1500);
        level = 1;
        level += levelCalc;
        return getLevel();
    }


    public static ArrayList<Player> all(DBHelper dbHelper){
        ArrayList<Player> players = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PROFILE_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(PROFILE_COLUMN_NAME));
            Integer level = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_LEVEL));
            Integer progress = cursor.getInt(cursor.getColumnIndex(PROFILE_COLUMN_PROGRESS));

            Player player = new Player(id, name, level, progress);
            players.add(player);
        }
        cursor.close();
        return players;
    }

    public static boolean deleteAll(DBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + PROFILE_TABLE_NAME);
        return true;
    }

    public static boolean delete(DBHelper dbHelper, Integer id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = " id = ?";
        String[] values = {id.toString()};
        db.delete(PROFILE_TABLE_NAME, selection, values);
        return true;
    }

    public void update(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFILE_COLUMN_NAME, name);
        contentValues.put(PROFILE_COLUMN_LEVEL, level);
        contentValues.put(PROFILE_COLUMN_PROGRESS, progress);

        String where = "name=?";
        String[] whereArgs = new String[] {String.valueOf(name)};

        db.update(PROFILE_TABLE_NAME, contentValues, where, whereArgs);
        db.close();
    }
}

