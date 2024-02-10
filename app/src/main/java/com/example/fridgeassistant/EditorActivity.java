package com.example.fridgeassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class EditorActivity extends AppCompatActivity {
    Button expButton, addButton;
    EditText nameEditTxt, tagEditTxt;
    DatePickerDialog.OnDateSetListener setListener;
    FoodDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // SQL Database
        dbHelper = new FoodDbHelper(this);

        // Layout variables
        nameEditTxt = findViewById(R.id.etxt_foodName);
        tagEditTxt = findViewById(R.id.etxt_tag);
        expButton = findViewById(R.id.btn_expDate);

        // DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        expButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditorActivity.this, android.R.style.Theme_Holo_Dialog, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                month = month + 1;
                String date = month+"/"+dayOfMonth+"/"+year;
                expButton.setText(date);
            }
        };

        // Add Button
        addButton = findViewById(R.id.btn_submit);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodItemToDatabase(calendar);
                Intent intent = new Intent(EditorActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addFoodItemToDatabase(Calendar expDate) {
        String name = nameEditTxt.getText().toString();
        String tag = tagEditTxt.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_NAME, name);
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_TAG, tag);
        values.put(FoodItemContract.FoodItemEntry.COLUMN_NAME_EXP_DATE, expDate.getTimeInMillis()); // Convert Calendar to milliseconds

        // Insert new row and return primary key value of the new row
        long newRowId = db.insert(FoodItemContract.FoodItemEntry.TABLE_NAME, null, values);

        // Check if insertion was successful
        if (newRowId != -1) {
            Toast.makeText(EditorActivity.this, "Food item added to database", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(EditorActivity.this, "Failed to add food item to database", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}