package com.example.roomdbwithtypeconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class Converters {
    @TypeConverter
    public static HashMap<String, Boolean> fromString(String value) {
        Type mapType = new TypeToken<HashMap<String, Boolean>>() {}.getType();
        return new Gson().fromJson(value, mapType);
    }

    @TypeConverter
    public static String fromHashMap(HashMap<String, Boolean> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
