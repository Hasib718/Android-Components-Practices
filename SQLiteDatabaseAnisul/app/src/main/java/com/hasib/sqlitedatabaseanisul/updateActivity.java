package com.hasib.sqlitedatabaseanisul;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class updateActivity extends AppCompatDialogFragment {
    private static final String TAG = "updateActivity";

    private EditText nameEditText;
    private EditText ageEditText;
    private EditText genderEditText;
    private EditText idEditText;
    private UpdateActivityListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.update_layout, null);
        nameEditText = view.findViewById(R.id.name);
        ageEditText = view.findViewById(R.id.age);
        genderEditText = view.findViewById(R.id.gender);
        idEditText = view.findViewById(R.id.id);

        final MyDatabaseHelper myDatabase = new MyDatabaseHelper(getContext());

        builder.setView(view)
                .setTitle("Update Dialog Box")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        String age = ageEditText.getText().toString();
                        String gender = genderEditText.getText().toString();
                        String id = idEditText.getText().toString();

                        listener.isUpdated(myDatabase.updateData(id, name, age, gender));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (UpdateActivityListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+
                    "must implement UpdateActivityListener");
        }
    }

    public interface UpdateActivityListener {
        void isUpdated(boolean bool);
    }
}
