package com.hasib.sqlitedatabaseanisul;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "student_details";
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String GENDER = "Gender";
    private static final int VERSION_NUMBER = 1;

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+AGE+" INTEGER, "+GENDER+" VARCHAR(16))";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String SELECT_DATA = "SELECT * FROM "+TABLE_NAME;

    private Context context;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
//            db.execSQL("CREATE TABLE testing (_id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255), Age INTEGER, Gender VARCHAR(16))");
            Toast.makeText(context, "onCreate is created", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Toast.makeText(context, "onUpgrade is Called", Toast.LENGTH_LONG).show();

            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long insertData(String name, String age, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(GENDER, gender);

        long rowId = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return rowId;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_DATA, null);

        return  cursor;
    }

    public boolean updateData(String id, String name, String age, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        if (!name.isEmpty()) {
            contentValues.put(NAME, name);
        }
        if (!age.isEmpty()) {
            contentValues.put(AGE, age);
        }
        if (!gender.isEmpty()) {
            contentValues.put(GENDER, gender);
        }
        if (!id.isEmpty()) {
            contentValues.put(ID, id);
        }

        int x = db.update(TABLE_NAME, contentValues, ID+" = ?", new String[]{id});
        if (x < 1) {
            return false;
        } else {
            return true;
        }
    }
}
