package com.hasib.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");

//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Hasib', 22)");
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Toufiq', 20)");
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Wadud', 21)");
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Antor', 19)");
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Antor', 19)");

//            myDatabase.execSQL("DELETE FROM users WHERE name = (SELECT name FROM users WHERE name = 'Antor' LIMIT 1)");
            myDatabase.execSQL("DELETE FROM users WHERE name = 'Antor' LIMIT 1");

            Cursor c = myDatabase.rawQuery("SELECT * FROM users", null);

            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");

            c.moveToFirst();
            while(c != null) {
                Log.i(TAG, "onCreate: name "+c.getString(nameIndex));
                Log.i(TAG, "onCreate: age "+c.getInt(ageIndex));

                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
