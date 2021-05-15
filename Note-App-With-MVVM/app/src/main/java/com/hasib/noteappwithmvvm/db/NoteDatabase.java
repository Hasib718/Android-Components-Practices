package com.hasib.noteappwithmvvm.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hasib.noteappwithmvvm.model.Note;
import com.hasib.noteappwithmvvm.utils.AppExecutors;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;
    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            AppExecutors.getInstance().diskIO().execute(new PopulateDatabase(instance));
        }
    };

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();

    private static class PopulateDatabase implements Runnable {
        private final NoteDao noteDao;

        private PopulateDatabase(NoteDatabase noteDatabase) {
            noteDao = noteDatabase.noteDao();
        }

        @Override
        public void run() {
            noteDao.insert(new Note("Test 1", "aslkdjbvbsd", 4));
            noteDao.insert(new Note("Test 2", "loremsdancsdav", 3));
            noteDao.insert(new Note("Test 3", "asdkjvaskdsln", 9));
        }
    }
}
