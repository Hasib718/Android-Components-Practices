package com.example.roomdbwithtypeconverter;

import android.content.Context;

public class AppDatabases {

    private static AppDatabases sInstance;
    private final PersonDatabase personDatabase;

    private AppDatabases(PersonDatabase personDatabase) {
        this.personDatabase = personDatabase;
    }

    public static AppDatabases getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AppDatabases(PersonDatabase.getInstance(context, "person_db"));
        }

        return sInstance;
    }

    public PersonDatabase getPersonDatabase() {
        return personDatabase;
    }
}
