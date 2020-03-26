package com.hasib.sqlitedatabaseanisul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.AccessNetworkConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper myDatabase;

    private EditText nameEditText;
    private EditText ageEditText;
    private EditText genderEditText;
    private Button addingDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = (EditText) findViewById(R.id.name);
        ageEditText = (EditText) findViewById(R.id.age);
        genderEditText = (EditText) findViewById(R.id.gender);
        addingDataButton = (Button) findViewById(R.id.addingButton);

        myDatabase = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();

        final SQLiteDatabase testing = this.openOrCreateDatabase("Test", MODE_PRIVATE, null);
        testing.execSQL("CREATE TABLE IF NOT EXISTS testing ( _id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(256), Age INTEGER, Gender VARCHAR(16))");

        addingDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String gender = genderEditText.getText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put("Name", name);
                contentValues.put("Age", age);
                contentValues.put("Gender", gender);


                testing.insert("testing", null, contentValues);
                long rowId = myDatabase.insertData(name, age, gender);
                if (rowId > 0) {
                    Toast.makeText(getApplicationContext(), "Row "+rowId+" is successfully inserted", Toast.LENGTH_LONG).show();
                    nameEditText.getText().clear();
                    ageEditText.getText().clear();
                    genderEditText.getText().clear();
                } else {
                    Toast.makeText(getApplicationContext(), "unsuccessfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
