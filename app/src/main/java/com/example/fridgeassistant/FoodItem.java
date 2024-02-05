package com.example.fridgeassistant;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class FoodItem {
    String name;
    String tag;
    Calendar exp_date;

    public FoodItem(String name, String tag, Calendar exp_date) {
        this.name = name;
        this.tag = tag;
        this.exp_date = exp_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(Calendar exp_date) {
        this.exp_date = exp_date;
    }
}
