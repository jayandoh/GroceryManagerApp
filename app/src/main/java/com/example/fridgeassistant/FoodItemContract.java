package com.example.fridgeassistant;

import android.provider.BaseColumns;

public class FoodItemContract {
    private FoodItemContract() {}

    public static class FoodItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "food_items";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TAG = "tag";
        public static final String COLUMN_NAME_EXP_DATE = "exp_date";
    }
}
