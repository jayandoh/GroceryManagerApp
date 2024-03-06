package com.example.fridgeassistant.Food;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class FoodItem implements Parcelable {
    private long _id = -1;
    private String name;
    private String tag;
    private Calendar exp_date;

    public FoodItem(String name, String tag, Calendar exp_date) {
        this.name = name;
        this.tag = tag;
        this.exp_date = exp_date;
    }

    public FoodItem(long _id, String name, String tag, Calendar exp_date) {
        this._id = _id;
        this.name = name;
        this.tag = tag;
        this.exp_date = exp_date;
    }

    public long getId() {
        return _id;
    }

    public void setId(long _id) {
        this._id = _id;
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

    public Calendar getExp_date() {
        return exp_date;
    }
    public void setExp_date(Calendar exp_date) {
        this.exp_date = exp_date;
    }

    // Parcelable implementation
    private FoodItem(Parcel in) {
        _id = in.readLong();
        name = in.readString();
        tag = in.readString();
        exp_date = (Calendar) in.readSerializable();
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(name);
        dest.writeString(tag);
        dest.writeSerializable(exp_date);
    }

}
