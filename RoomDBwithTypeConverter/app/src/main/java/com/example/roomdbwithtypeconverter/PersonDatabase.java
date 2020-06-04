package com.example.roomdbwithtypeconverter;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Person.class, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PersonDatabase extends RoomDatabase {
    private static final String TAG = PersonDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "person_db";
    private static PersonDatabase sInstance;

    public static PersonDatabase getInstance(Context context, String DATABASE_NAME) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public static PersonDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(), PersonDatabase.class, DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract PersonDao personDao();
}
