package com.example.fridgeassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton button_add;
    List<FoodItem> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.id_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FoodAdapter(getApplicationContext(), foodList));

        // Test cases
        Calendar test1Cal = Calendar.getInstance();
        FoodItem brocolli = new FoodItem("Brocolli", "Veggies", test1Cal);
        FoodItem chicken_breast = new FoodItem("Chicken Breast", "Meats", test1Cal);
        foodList.add(brocolli);
        foodList.add(chicken_breast);

        // Add Button
        button_add = findViewById(R.id.floatingActionButton);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditorActivity();
            }
        });
    }

    private void startEditorActivity() {
        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
        startActivity(intent);
    }
}