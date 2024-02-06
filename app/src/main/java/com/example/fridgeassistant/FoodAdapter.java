package com.example.fridgeassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {

    Context context;
    List<FoodItem> items;

    public FoodAdapter(Context context, List<FoodItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FoodViewHolder(LayoutInflater.from(context).inflate(R.layout.groceries_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.foodNameView.setText(items.get(position).getName());
        holder.tagView.setText(items.get(position).getTag());

        // Convert FoodItem Calendar attribute to String so it can be displayed
        Calendar calendar = items.get(position).getExp_date();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        month = month + 1;
        String date = month+"/"+day+"/"+year;

        holder.expDateView.setText(date);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
