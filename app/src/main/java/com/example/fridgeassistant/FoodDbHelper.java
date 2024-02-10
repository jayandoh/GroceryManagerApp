package com.example.fridgeassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodItemContract.FoodItemEntry.TABLE_NAME + " (" +
                    FoodItemContract.FoodItemEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodItemContract.FoodItemEntry.COLUMN_NAME_NAME + " TEXT," +
                    FoodItemContract.FoodItemEntry.COLUMN_NAME_TAG + " TEXT," +
                    FoodItemContract.FoodItemEntry.COLUMN_NAME_EXP_DATE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FoodItemContract.FoodItemEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FoodItems.db";

    public FoodDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}