package com.hasib.changingactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ListView listView = (ListView) findViewById(R.id.nameList);
        Intent intent = getIntent();
        Toast.makeText(this, intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();

        final List<String> stringList = new ArrayList<>(Arrays.asList("Toufiq", "Wadud", "Partho", "Antor"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, stringList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Click", parent.getItemAtPosition(position).toString());

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("tappedString", stringList.get(position));
                startActivity(intent);
            }
        });
    }
}
