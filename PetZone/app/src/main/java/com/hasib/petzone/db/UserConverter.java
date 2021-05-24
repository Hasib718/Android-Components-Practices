package com.hasib.petzone.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class UserConverter {
    @TypeConverter
    public static User fromString(String value) {
        Type userType = new TypeToken<User>() {}.getType();
        return new Gson().fromJson(value, userType);
    }

    @TypeConverter
    public static String fromFood(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }
}
