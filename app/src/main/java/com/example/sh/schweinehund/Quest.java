package com.example.sh.schweinehund;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import static com.example.sh.schweinehund.DBHelper.QUEST_COLUMN_CATEGORY;
import static com.example.sh.schweinehund.DBHelper.QUEST_COLUMN_COMPLETED;
import static com.example.sh.schweinehund.DBHelper.QUEST_COLUMN_DATE;
import static com.example.sh.schweinehund.DBHelper.QUEST_COLUMN_EXP;
import static com.example.sh.schweinehund.DBHelper.QUEST_COLUMN_ID;
import static com.example.sh.schweinehund.DBHelper.QUEST_COLUMN_NAME;
import static com.example.sh.schweinehund.DBHelper.QUEST_TABLE_NAME;

/**
 * Created by Oleg Kornelsen on 27/09/2018
 */

public class Quest {

    private String name;
    private Integer expValue;
    private Integer category;
    private int isCompleted;
    private Date date;
    private int id;

    public Quest(String name, Integer expValue, Integer category) {
        this.name = name;
        this.expValue = expValue;
        this.category = category;
        this.isCompleted = 0;
        this.date = new Date();
    }

    public Quest(String name, Integer expValue, Integer category, Date date) {
        this.name = name;
        this.expValue = expValue;
        this.category = category;
        this.isCompleted = 0;
        this.date = date;
    }

    public Quest(int id, String name, Integer expValue, Integer category, Date date) {
        this.id = id;
        this.name = name;
        this.expValue = expValue;
        this.category = category;
        this.isCompleted = 0;
        this.date = date;
    }

    // Getters

    public String getName() {
        return name;
    }

    public Integer getExpValue() {
        return expValue;
    }

    public Integer getCategory() {
        return category;
    }

    public int isCompleted() {
        return isCompleted;
    }

    public Date getDate() {
        return date;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setExpValue(Integer expValue) {
        this.expValue = expValue;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public void setCompleted(int completed) {
        isCompleted = completed;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Category getCategoryByInteger(DBHelper dbHelper, Integer questInt){

        switch (questInt) {
            case 1:
                return Category.load(dbHelper, "Sport");
            case 2:
                return Category.load(dbHelper, "Naehrung");
            case 3:
                return Category.load(dbHelper, "Bildung");
        }
        return null;
    }

    public String showCategoryNameByNumber(Integer questInt){

        switch (questInt) {
            case 1:
                return "Sport";
            case 2:
                return "Naehrung";
            case 3:
                return "Bildung";
        }
        return null;
    }

    /*public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }*/

    // Neuen Quest abspeichern
    public boolean save(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUEST_COLUMN_NAME, name);
        contentValues.put(QUEST_COLUMN_EXP, expValue);
        contentValues.put(QUEST_COLUMN_CATEGORY, category);
        contentValues.put(QUEST_COLUMN_DATE, date.getTime());

        db.insert(QUEST_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public static ArrayList<Quest> all(DBHelper dbHelper){
        ArrayList<Quest> quests = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QUEST_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(QUEST_COLUMN_NAME));
            Integer expValue = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_EXP));
            Integer category = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_CATEGORY));
            Long datelong = cursor.getLong(cursor.getColumnIndex(QUEST_COLUMN_DATE));
            Date dueDate = new Date(datelong);

            Quest quest = new Quest(id, name, expValue, category, dueDate);
            quests.add(quest);
        }
        cursor.close();
        return quests;
    }

    public static ArrayList<Quest> allComplete(DBHelper dbHelper){
        ArrayList<Quest> quests = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = " WHERE completed = ?";
        Cursor cursor = db.rawQuery("SELECT * FROM " + QUEST_TABLE_NAME + query , new String[] {"1"});
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(QUEST_COLUMN_NAME));
            Integer expValue = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_EXP));
            Integer category = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_CATEGORY));
            Long datelong = cursor.getLong(cursor.getColumnIndex(QUEST_COLUMN_DATE));
            Date dueDate = new Date(datelong);

            Quest quest = new Quest(id, name, expValue, category, dueDate);
            quests.add(quest);
        }
        cursor.close();
        return quests;
    }

    public static ArrayList<Quest> allIncomplete(DBHelper dbHelper,String cat){
        ArrayList<Quest> quests = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = " WHERE completed = ? and category = ?";
        Cursor cursor = db.rawQuery("SELECT * FROM " + QUEST_TABLE_NAME + query , new String[] {"0",  cat});
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(QUEST_COLUMN_NAME));
            Integer expValue = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_EXP));
            Integer category = cursor.getInt(cursor.getColumnIndex(QUEST_COLUMN_CATEGORY));
            Long datelong = cursor.getLong(cursor.getColumnIndex(QUEST_COLUMN_DATE));
            Date dueDate = new Date(datelong);

            Quest quest = new Quest(id, name, expValue, category, dueDate);
            quests.add(quest);
        }
        cursor.close();
        return quests;
    }

    public static boolean deleteAll(DBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + QUEST_TABLE_NAME);
        return true;
    }

    public static boolean delete(DBHelper dbHelper, Integer id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = " id = ?";
        String[] values = {id.toString()};
        db.delete(QUEST_TABLE_NAME, selection, values);
        return true;
    }

    public void update(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUEST_COLUMN_NAME, name);
        contentValues.put(QUEST_COLUMN_EXP, expValue);
        contentValues.put(QUEST_COLUMN_CATEGORY, category);
        contentValues.put(QUEST_COLUMN_COMPLETED, isCompleted);
        contentValues.put(QUEST_COLUMN_DATE, date.getDate());

        db.update(QUEST_TABLE_NAME, contentValues, QUEST_COLUMN_ID+"="+id, null);
        db.close();
    }
}

