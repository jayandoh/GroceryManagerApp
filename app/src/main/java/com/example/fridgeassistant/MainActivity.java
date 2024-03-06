package com.example.fridgeassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import com.example.fridgeassistant.Food.FoodAdapter;
import com.example.fridgeassistant.Food.FoodDbHelper;
import com.example.fridgeassistant.Food.FoodItem;
import com.example.fridgeassistant.Food.FoodItemContract;
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
        setupRecyclerView();

        // Add Button
        button_add = findViewById(R.id.floatingActionButton);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditorDialog();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.id_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new FoodAdapter(getApplicationContext(), foodList);
        recyclerView.setAdapter(adapter);

        // Swipe Controller
        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                FoodItem foodItem = foodList.get(position);
                foodList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, foodList.size());
                dbHelper.removeFoodItemFromDatabase(foodItem);
            }

            public void onLeftClicked(int position) {
                FoodItem foodItem = foodList.get(position);
                startEditorDialog(foodItem);
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void loadFoodItemsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FoodItemContract.FoodItemEntry._ID,
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
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(FoodItemContract
                    .FoodItemEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FoodItemContract
                    .FoodItemEntry.COLUMN_NAME_NAME));
            String tag = cursor.getString(cursor.getColumnIndexOrThrow(FoodItemContract
                    .FoodItemEntry.COLUMN_NAME_TAG));
            long expDateMillis = cursor.getLong(cursor.getColumnIndexOrThrow(FoodItemContract
                    .FoodItemEntry.COLUMN_NAME_EXP_DATE));
            Calendar expDate = Calendar.getInstance();
            expDate.setTimeInMillis(expDateMillis);

            FoodItem item = new FoodItem(id, name, tag, expDate);
            foodList.add(item);
        }
        cursor.close();
    }

    private void startEditorDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EditorDialogFragment dialogFragment = new EditorDialogFragment();
        dialogFragment.show(fragmentManager, "EditorDialog");
    }

    private void startEditorDialog(FoodItem foodItem) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EditorDialogFragment dialogFragment = new EditorDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("foodItem", foodItem);
        dialogFragment.setArguments(bundle);

        dialogFragment.show(fragmentManager, "EditorDialog");
    }
}