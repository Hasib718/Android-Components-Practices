package com.hasib.callmsgmaildemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CALL = 1001;
    private static final String TAG = MainActivity.class.getSimpleName();
    private MaterialButton callBtn, messageBtn, emailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        callBtn = findViewById(R.id.callbtn);
        messageBtn = findViewById(R.id.messagebtn);
        emailBtn = findViewById(R.id.mailbtn);

        callBtn.setOnClickListener(this);
        messageBtn.setOnClickListener(this);
        emailBtn.setOnClickListener(this);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS}, REQUEST_CALL);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callbtn:
                final View view = getLayoutInflater().inflate(R.layout.layout_call, null);

                createDialogBox(view, "Calling Dialog", "Call", (dialog, which) -> {
                    Log.d(TAG, "onClick: " + ((EditText) view.findViewById(R.id.phoneNumber)).getText().toString());
                    makePhoneCall(((EditText) view.findViewById(R.id.phoneNumber)).getText().toString());
                });
                break;

            case R.id.mailbtn:
                final View view1 = getLayoutInflater().inflate(R.layout.layout_email, null);

                createDialogBox(view1, "Email Dialog", "Send", (dialog, which) -> {
                    Log.d(TAG, "onClick: email " + ((TextInputEditText) view1.findViewById(R.id.receiverEmail)).getText().toString());
                    Log.d(TAG, "onClick: email " + ((TextInputEditText) view1.findViewById(R.id.subject)).getText().toString());
                    Log.d(TAG, "onClick: email " + ((TextInputEditText) view1.findViewById(R.id.body)).getText().toString());

                    Intent selectorIntent = new Intent(Intent.ACTION_SENDTO);
                    selectorIntent.setData(Uri.parse("mailto:"));

                    final Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{((TextInputEditText) view1.findViewById(R.id.receiverEmail)).getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, ((TextInputEditText) view1.findViewById(R.id.subject)).getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT, ((TextInputEditText) view1.findViewById(R.id.body)).getText().toString());
                    intent.setSelector(selectorIntent);

                    try {
                        startActivity(Intent.createChooser(intent, getString(R.string.share_email_title)));
                    } catch (android.content.ActivityNotFoundException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.messagebtn:
                final View view2 = getLayoutInflater().inflate(R.layout.layout_sms, null);

                createDialogBox(view2, "SMS Dialog", "Send", (dialog, which) -> {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(((TextInputEditText) view2.findViewById(R.id.cellNumber)).getText().toString().trim(),
                                null,
                                ((TextInputEditText) view2.findViewById(R.id.sms)).getText().toString(),
                                null, null);

                        Toast.makeText(this, "SMS is Sent", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "SMS sent failed", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void createDialogBox(View view, String title, String work, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setPositiveButton(work, onClickListener)
                .create()
                .show();
    }

    private void makePhoneCall(String number) {
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}