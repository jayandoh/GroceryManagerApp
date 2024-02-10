package com.example.fridgeassistant;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton button_add;
    List<FoodItem> foodList = new ArrayList<>();
    RecyclerView recyclerView;
    FoodAdapter adapter;
    FoodDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SQL Database
        dbHelper = new FoodDbHelper(this);
        loadFoodItemsFromDatabase();
        adapter = new FoodAdapter(getApplicationContext(), foodList);

        // RecyclerView
        recyclerView = findViewById(R.id.id_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FoodAdapter(getApplicationContext(), foodList));

        // Add Button
        button_add = findViewById(R.id.floatingActionButton);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditorActivity();
            }
        });
    }

    private void loadFoodItemsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FoodItemContract.FoodItemEntry.COLUMN_NAME_NAME,
                FoodItemContract.FoodItemEntry.COLUMN_NAME_TAG,
                FoodItemContract.FoodItemEntry.COLUMN_NAME_EXP_DATE
        };

        Cursor cursor = db.query(
                FoodItemContract.FoodItemEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FoodItemContract.FoodItemEntry.COLUMN_NAME_NAME));
            String tag = cursor.getString(cursor.getColumnIndexOrThrow(FoodItemContract.FoodItemEntry.COLUMN_NAME_TAG));
            long expDateMillis = cursor.getLong(cursor.getColumnIndexOrThrow(FoodItemContract.FoodItemEntry.COLUMN_NAME_EXP_DATE));
            Calendar expDate = Calendar.getInstance();
            expDate.setTimeInMillis(expDateMillis);
            FoodItem item = new FoodItem(name, tag, expDate);
            foodList.add(item);
        }
        cursor.close();
    }

    private void startEditorActivity() {
        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
        startActivity(intent);
    }
}