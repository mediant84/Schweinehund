package com.example.sh.schweinehund;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Oleg Kornelsen on 27/09/2018
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "test95.db";

    //    Category table
    public static final String CATEGORY_TABLE_NAME = "categories";
    public static final String CATEGORY_COLUMN_ID = "id";
    public static final String CATEGORY_COLUMN_NAME = "name";
    public static final String CATEGORY_COLUMN_EXP = "exp";
    public static final String CATEGORY_COLUMN_LEVEL = "level";

    //    Quest table
    public static final String QUEST_TABLE_NAME = "quests";
    public static final String QUEST_COLUMN_ID = "id";
    public static final String QUEST_COLUMN_NAME = "name";
    public static final String QUEST_COLUMN_EXP = "expValue";
    public static final String QUEST_COLUMN_CATEGORY = "category";
    public static final String QUEST_COLUMN_DATE = "date";
    public static final String QUEST_COLUMN_COMPLETED = "completed";

    //    Profile table
    public static final String PROFILE_TABLE_NAME = "users";
    public static final String PROFILE_COLUMN_ID = "id";
    public static final String PROFILE_COLUMN_NAME = "name";
    public static final String PROFILE_COLUMN_STRENGTH_EXP = "strength_exp";
    public static final String PROFILE_COLUMN_STAMINA_EXP = "stamina_exp";
    public static final String PROFILE_COLUMN_INTELLIGENCE_EXP = "intelligence_exp";
    public static final String PROFILE_COLUMN_SOCIAL_EXP = "social_exp";
    public static final String PROFILE_COLUMN_LEVEL = "level";


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db){
        setForeignKeyConstraintsEnabled(db);

        db.execSQL("CREATE TABLE " + PROFILE_TABLE_NAME + "(id INTEGER primary key autoincrement NOT NULL, name TEXT, strength_exp INTEGER, stamina_exp INTEGER, intelligence_exp INTEGER, social_exp INTEGER, level INTEGER )");

        db.execSQL("CREATE TABLE " + CATEGORY_TABLE_NAME + "(id INTEGER primary key autoincrement NOT NULL, name TEXT, exp INTEGER, level INTEGER )");
        db.execSQL("CREATE TABLE " + QUEST_TABLE_NAME + "(id INTEGER primary key autoincrement NOT NULL, name TEXT, expValue INTEGER, date INTEGER, completed INTEGER DEFAULT 0, category INTEGER NOT NULL, FOREIGN KEY (category) REFERENCES '+CATEGORY_TABLE_NAME+' (id))");

        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + "('name', 'exp', 'level') VALUES ('Strength', 0, 0 );");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + "('name', 'exp', 'level') VALUES ('Stamina', 0, 0 );");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + "('name', 'exp', 'level') VALUES ('Intelligence', 0, 0 );");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + "('name', 'exp', 'level') VALUES ('Social', 0, 0 );");

        db.execSQL("INSERT INTO " + PROFILE_TABLE_NAME + "('name', 'strength_exp', 'stamina_exp', 'intelligence_exp', 'social_exp', 'level') VALUES ('Schweinehund', 0, 0, 0, 0, 0 );");


        db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('Start', '10', '1510737479', '0', '1');");
        //db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('Drinks with friends', '100', '1510743305', '0', '4');");
        //db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('CodeClan course', '800', '1510743305', '0', '3');");
        //db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('Cycling to Balloch and back', '225', '1510743305', '0', '2');");
        //db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('Python Dojo', '75', '1510743305', '0', '3');");
        //db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('Lindsey (Birthday)', '125', '1510743305', '0', '4');");
        //db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('Yoga', '40', '1510743305', '0', '2');");
        //db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('Bear, Fish, River in Python', '300', '1510743305', '0', '3');");
        //db.execSQL("INSERT INTO " + QUEST_TABLE_NAME + "('name', 'expValue', 'date', 'completed', 'category') VALUES ('Build / Put up new shelves', '175', '1510743305', '0', '1');");


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QUEST_TABLE_NAME);
        onCreate(db);
    }

    private static void setForeignKeyConstraintsEnabled(@NonNull SQLiteDatabase db) {
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=1;");
        }
    }


    //    TABLE CHECKER
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
