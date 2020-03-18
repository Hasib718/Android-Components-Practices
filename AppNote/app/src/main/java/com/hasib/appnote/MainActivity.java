package com.hasib.appnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NoteNameDialog.NoteNameDialogListener {
    private ListView listView;
    static Map<String, String> notesList;
    static ArrayAdapter arrayAdapter;
    static List<String> noteKeyList;
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

        Toast.makeText(MainActivity.this, noteName+" have created", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onOptionsItemSelected: "+"note name have been taken");
        notesList.put(noteName, "");
        noteKeyList.add(noteName);
        arrayAdapter.notifyDataSetChanged();
        Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
        intent.putExtra("noteName", noteName);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.noteList);
        notesList = new LinkedHashMap<>();
        noteKeyList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, noteKeyList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("key", position);
                Log.d(TAG, "onItemClick: "+notesList.get(noteKeyList.get(position)));
                startActivity(intent);
            }
        });
    }
}
