package com.hasib.noteappwithmvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRIORITY = "PRIORITY";

    private EditText title, description;
    private NumberPicker priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        title = findViewById(R.id.add_note_title);
        description = findViewById(R.id.add_note_description);
        priority = findViewById(R.id.priority_picker);

        priority.setMaxValue(10);
        priority.setMinValue(1);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(ID)) {
            setTitle("Edit Note");
            title.setText(intent.getStringExtra(TITLE));
            description.setText(intent.getStringExtra(DESCRIPTION));
            priority.setValue(intent.getIntExtra(PRIORITY, 1));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            saveNote();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String noteTitle = title.getText().toString();
        String noteDescription = description.getText().toString();
        int notePriority = priority.getValue();

        if (noteTitle.trim().isEmpty() || noteDescription.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a Title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(TITLE, noteTitle);
        data.putExtra(DESCRIPTION, noteDescription);
        data.putExtra(PRIORITY, notePriority);

        int id = getIntent().getIntExtra(ID, -1);
        if (id != -1) {
            data.putExtra(ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}