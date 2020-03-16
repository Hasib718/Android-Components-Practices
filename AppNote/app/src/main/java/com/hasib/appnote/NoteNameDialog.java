package com.hasib.appnote;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NoteNameDialog extends AppCompatDialogFragment {
    private EditText noteNameText;
    private static final String TAG = "NoteNameDialog";
    private NoteNameDialogListener listener;
    public static String nName;

    public String getName() {
        return noteNameText.getEditableText().toString();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        noteNameText = view.findViewById(R.id.noteName);

        builder.setView(view)
                .setTitle("Enter Note Name")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = noteNameText.getEditableText().toString();
                        nName = name;
                        Log.d(TAG, "onClick: "+name);
                        listener.applyTexts(name);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NoteNameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
                    "must implement NoteNameDialogListener");
        }
    }

    public interface NoteNameDialogListener {
        void applyTexts(String name);
    }
}
