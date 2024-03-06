package com.example.fridgeassistant.Food;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fridgeassistant.R;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    TextView foodNameView, tagView, expDateView;

    public FoodViewHolder(View itemView) {
        super(itemView);
        foodNameView = itemView.findViewById(R.id.foodName_text);
        tagView = itemView.findViewById(R.id.tag_text);
        expDateView = itemView.findViewById(R.id.expDate_text);
    }
}