package com.example.sh.schweinehund;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;




        import static com.example.sh.schweinehund.DBHelper.CATEGORY_COLUMN_ID;

        import static com.example.sh.schweinehund.DBHelper.CATEGORY_COLUMN_NAME;
        import static com.example.sh.schweinehund.DBHelper.CATEGORY_TABLE_NAME;

/**
 * Created by Oleg Kornelsen on 27/09/2018
 */

// Vorgesehen für weiteres Funktionalität

public class Category {

    private String name;
    private int id;
    private Integer exp;
    private Integer level;


    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Category load(DBHelper dbHelper, String name){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String where = " WHERE name=?";
        String[] whereArgs = new String[] {String.valueOf(name)};

        Cursor cursor = db.rawQuery("SELECT * FROM " + CATEGORY_TABLE_NAME + where, whereArgs);
        Category category = null;
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(CATEGORY_COLUMN_ID));
            String catName = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_NAME));
            cursor.close();
            category = new Category(id, catName);
        }
        return category;
    }

    public boolean save(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_NAME, name);

        db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<Category> all(DBHelper dbHelper){
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CATEGORY_TABLE_NAME, null);
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex(CATEGORY_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_NAME));

            Category category= new Category(id, name);
            categories.add(category);
        }
        cursor.close();
        return categories;
    }

    public static boolean deleteAll(DBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + CATEGORY_TABLE_NAME);
        return true;
    }

    public static boolean delete(DBHelper dbHelper, Integer id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = " id = ?";
        String[] values = {id.toString()};
        db.delete(CATEGORY_TABLE_NAME, selection, values);
        return true;
    }

    public void update(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db == null) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_COLUMN_NAME, name);

        String where = "name=?";
        String[] whereArgs = new String[] {String.valueOf(name)};

        db.update(CATEGORY_TABLE_NAME, contentValues, where, whereArgs);
        db.close();
    }
}

