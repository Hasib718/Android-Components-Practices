package com.hasib.petzone.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class FoodConverter {
    @TypeConverter
    public static Food fromString(String value) {
        Type foodType = new TypeToken<Food>() {}.getType();
        return new Gson().fromJson(value, foodType);
    }

    @TypeConverter
    public static String fromFood(Food food) {
        Gson gson = new Gson();
        return gson.toJson(food);
    }
}
