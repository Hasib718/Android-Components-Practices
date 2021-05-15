package com.hasib.noteappwithmvvm.mvvm;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hasib.noteappwithmvvm.db.NoteDao;
import com.hasib.noteappwithmvvm.db.NoteDatabase;
import com.hasib.noteappwithmvvm.model.Note;
import com.hasib.noteappwithmvvm.utils.AppExecutors;

import java.util.List;

public class NoteRepository {
    private final NoteDao noteDao;
    private final LiveData<List<Note>> notes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        notes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                noteDao.insert(note);
            }
        });
    }

    public void update(Note note) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                noteDao.update(note);
            }
        });
    }

    public void delete(Note note) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                noteDao.delete(note);
            }
        });
    }

    public void deleteAllNotes() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteAllNotes();
            }
        });
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }
}
