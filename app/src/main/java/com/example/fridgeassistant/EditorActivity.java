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
    Button expButton;
    Button addButton;

    EditText nameEditTxt;
    EditText tagEditTxt;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        nameEditTxt = findViewById(R.id.etxt_foodName);
        tagEditTxt = findViewById(R.id.etxt_tag);

        // Date Picker Dialog
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

        // Add Button
        addButton = findViewById(R.id.btn_submit);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditTxt.getText().toString();
                String tag = tagEditTxt.getText().toString();

                // Create intent to MainActivity
                Intent resultIntent = new Intent();

                // Clear backstack and return to MainActivity
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("tag", tag);
                resultIntent.putExtra("expDate", calendar);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }
}