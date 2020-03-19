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

            //new DataBase with primary key
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS newUsers (name VARCHAR, age INTEGER(3), id INTEGER PRIMARY KEY)");

//            //for users
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Hasib', 22)");
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Toufiq', 20)");
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Wadud', 21)");
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Antor', 19)");
//            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('Antor', 19)");

//            //for newUsers
//            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Hasib', 22)");
//            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Toufiq', 20)");
//            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Wadud', 21)");
//            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Antor', 19)");
//            myDatabase.execSQL("INSERT INTO newUsers (name, age) VALUES ('Antor', 19)");

            //LIMIT isn't working for users that haven't an id
//            myDatabase.execSQL("DELETE FROM users WHERE name = (SELECT name FROM users WHERE name = 'Antor' LIMIT 1)");
//            myDatabase.execSQL("DELETE FROM users WHERE name = 'Antor' LIMIT 1");

            //updating database
//            myDatabase.execSQL("UPDATE users SET age = 30 WHERE name = 'Antor'");

            //deleting
            myDatabase.execSQL("DELETE FROM newUsers WHERE id = 5");

            Cursor c = myDatabase.rawQuery("SELECT * FROM newUsers", null);

            int nameIndex = c.getColumnIndex("name");
            int ageIndex = c.getColumnIndex("age");

            c.moveToFirst();
            while(c != null) {
                Log.i(TAG, "onCreate: name "+c.getString(nameIndex));
                Log.i(TAG, "onCreate: age "+c.getInt(ageIndex));
                Log.i(TAG, "onCreate: id "+c.getInt(c.getColumnIndex("id")));

                c.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
