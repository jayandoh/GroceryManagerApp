package com.example.fridgeassistant;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView
        recyclerView = findViewById(R.id.id_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FoodAdapter(getApplicationContext(), foodList));

        // Test cases
        Calendar test1Cal = Calendar.getInstance();
        FoodItem broccoli = new FoodItem("Broccoli", "Veggies", test1Cal);
        FoodItem chicken_breast = new FoodItem("Chicken Breast", "Meats", test1Cal);
        foodList.add(broccoli);
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
        editorLauncher.launch(intent);
    }

    private ActivityResultLauncher<Intent> editorLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String name = data.getStringExtra("name");
                        String tag = data.getStringExtra("tag");
                        Calendar expDate = (Calendar) data.getSerializableExtra("expDate");
                        FoodItem newItem = new FoodItem(name, tag, expDate);
                        foodList.add(newItem);
                        recyclerView.getAdapter().notifyItemInserted(foodList.size() - 1);
                    }
                }
            });

}