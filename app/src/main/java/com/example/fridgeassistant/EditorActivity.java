package com.example.fridgeassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

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
        dbHelper = new FoodDbHelper(EditorActivity.this);

        // Layout variables
        nameEditTxt = findViewById(R.id.etxt_foodName);
        tagEditTxt = findViewById(R.id.etxt_tag);
        expButton = findViewById(R.id.btn_expDate);
        addButton = findViewById(R.id.btn_submit);

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

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodItem foodItem = new FoodItem(nameEditTxt.getText().toString(),
                        tagEditTxt.getText().toString(), calendar);
                dbHelper.addFoodItemToDatabase(EditorActivity.this, foodItem);
                Intent intent = new Intent(EditorActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}