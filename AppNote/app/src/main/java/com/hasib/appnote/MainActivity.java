package com.hasib.appnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteNameDialog.NoteNameDialogListener {
    private ListView listView;
    private List<String> notesList;
    private ArrayAdapter arrayAdapter;
    private static final String TAG = "MainActivity";
    private String noteName;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.addNoteItem:
                Log.d(TAG, "onOptionsItemSelected: tapped!");
                    openDialog();
                Log.d(TAG, "onOptionsItemSelected: "+NoteNameDialog.nName);
                return true;
            default:
                return false;
        }
    }

    public void openDialog() {
        NoteNameDialog nameDialog = new NoteNameDialog();
        nameDialog.show(getSupportFragmentManager(), "Note Name Dialog");
    }

    @Override
    public void applyTexts(String name) {
        noteName = name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.noteList);
        notesList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notesList);
        listView.setAdapter(arrayAdapter);
    }
}
