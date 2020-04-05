package com.hasib.sqlitedatabaseanisul;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.AccessNetworkConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, updateActivity.UpdateActivityListener {

    MyDatabaseHelper myDatabase;

    private EditText nameEditText;
    private EditText ageEditText;
    private EditText genderEditText;
    private Button addingDataButton;
    private Button displayAllDataButton;
    private Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = (EditText) findViewById(R.id.name);
        ageEditText = (EditText) findViewById(R.id.age);
        genderEditText = (EditText) findViewById(R.id.gender);
        addingDataButton = (Button) findViewById(R.id.addingButton);
        displayAllDataButton = (Button) findViewById(R.id.displayAllDataId);
        updateButton = (Button) findViewById(R.id.updateButton);

        myDatabase = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabase.getWritableDatabase();

        addingDataButton.setOnClickListener(this);
        displayAllDataButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }

    private void showData(String title, String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
                .setMessage(data)
                .setCancelable(true)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addingButton: {
                String name = nameEditText.getText().toString();
                String age = ageEditText.getText().toString();
                String gender = genderEditText.getText().toString();

                long rowId = myDatabase.insertData(name, age, gender);
                if (rowId > 0) {
                    Toast.makeText(getApplicationContext(), "Row " + rowId + " is successfully inserted", Toast.LENGTH_LONG).show();
                    nameEditText.getText().clear();
                    ageEditText.getText().clear();
                    genderEditText.getText().clear();
                } else {
                    Toast.makeText(getApplicationContext(), "unsuccessfully", Toast.LENGTH_LONG).show();
                }
            }
            break;

            case R.id.displayAllDataId: {
                Cursor cursor = myDatabase.getAllData();

                if (cursor.getCount() == 0) {
                    showData("Error", "No Data Found1");
                    return;
                }

                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    stringBuffer.append("ID : " + cursor.getString(0) + "\n");
                    stringBuffer.append("Name : " + cursor.getString(1) + "\n");
                    stringBuffer.append("Age : " + cursor.getString(2) + "\n");
                    stringBuffer.append("Gender : " + cursor.getString(3) + "\n\n\n");
                }

                showData("ResultSet", stringBuffer.toString());
            }
            break;

            case R.id.updateButton : {
                updateActivity dialog = new updateActivity();
                dialog.show(getSupportFragmentManager(), "Update Dialog");
            }
            break;
        }
    }

    @Override
    public void isUpdated(boolean bool) {
        if(bool) {
            Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
}

