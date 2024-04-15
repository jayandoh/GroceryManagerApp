package com.example.fridgeassistant.Food;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class FoodDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FoodItemContract.FoodItemEntry.TABLE_NAME + " (" +
                    FoodItemContract.FoodItemEntry._ID + " INTEGER PRIMARY KEY," +
                    FoodItemContract.FoodItemEntry.COLUMN_NAME_NAME + " TEXT," +
                    FoodItemContract.FoodItemEntry.COLUMN_NAME_TAG + " INTEGER," +
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

    public void addFoodItemToDatabase(Context context, FoodItem foodItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_NAME, foodItem.getName());
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_TAG, foodItem.getTagId());
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_EXP_DATE, foodItem.getExp_date().getTimeInMillis()); // Convert Calendar to milliseconds

        // Insert new row and return primary key value of the new row
        long newRowId = db.insert(FoodItemContract.FoodItemEntry.TABLE_NAME, null, values);

        // Check if insertion was successful
        if (newRowId != -1) {
            Toast.makeText(context, "Food item added to database", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Failed to add food item to database", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void removeFoodItemFromDatabase(FoodItem foodItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = FoodItemContract.FoodItemEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = { foodItem.getName() };
        db.delete(FoodItemContract.FoodItemEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public void updateFoodItemInDatabase(Context context, FoodItem foodItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_NAME, foodItem.getName());
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_TAG, foodItem.getTagId());
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_EXP_DATE, foodItem.getExp_date().getTimeInMillis());

        String selection = FoodItemContract.FoodItemEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(foodItem.getId()) };

        int count = db.update(
                FoodItemContract.FoodItemEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            Toast.makeText(context, "Food item updated in database", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Failed to update food item in database", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}