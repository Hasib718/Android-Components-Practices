package com.hasib.petzone.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListConverter {
    @TypeConverter
    public static List<Food> fromString(String value) {
        Type listType = new TypeToken<List<Food>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromFood(List<Food> foods) {
        Gson gson = new Gson();
        return gson.toJson(foods);
    }
}
