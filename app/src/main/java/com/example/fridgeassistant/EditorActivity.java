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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditorActivity extends AppCompatActivity {
    Button expButton;
    Button addButton;

    EditText nameEditTxt;
    EditText tagEditTxt;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Date Picker Dialog code
        expButton = findViewById(R.id.btn_expDate);

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
                month = month + 1;
                String date = day+"/"+month+"/"+year;
                expButton.setText(date);
            }
        };

        // Add button code
        addButton = findViewById(R.id.btn_submit);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to MainActivity
                Intent intent = new Intent(EditorActivity.this, MainActivity.class);


                ArrayList<String> foodList = (ArrayList<String>) getIntent().getSerializableExtra("foodList");

                // Add variables
                intent.putStringArrayListExtra("myList", new ArrayList<>(foodList));
                Calendar expDate = Calendar.getInstance();
                expDate.set(2024,2,1);
                foodList.add(new FoodItem("Celery","Veggie", expDate));

                // Clear backstack and return to MainActivity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                                    // clear back stack
                startActivity(intent);                                                              // go back to MainActivity
                finish();
            }
        });

    }
}