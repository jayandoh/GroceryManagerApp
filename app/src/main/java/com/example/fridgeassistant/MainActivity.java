package com.example.fridgeassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton button_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List of food
        List<FoodItem> foodList = new ArrayList<>();

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.id_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FoodAdapter(getApplicationContext(), foodList));

        // Add Button
        button_add = findViewById(R.id.floatingActionButton);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("foodList", new ArrayList<>(foodList));
                startActivity(intent);
            }
        });
    }
}