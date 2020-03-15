package com.hasib.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button enteringMapView;
    private TextView infoButton;
    private ListView addedPlacesList;
    static Map<String, LatLng> placesList = new LinkedHashMap<>();
    static ArrayAdapter arrayAdapter;
    private int mapSize=0;
    private SharedPreferences sharedPreferences;

    public void proceedToMapView(View view) {
        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enteringMapView = (Button) findViewById(R.id.enteringMapView);
        infoButton = (TextView) findViewById(R.id.infoButton);
        addedPlacesList = (ListView) findViewById(R.id.addedPlacesList);
        sharedPreferences = this.getSharedPreferences("com.hasib.memorableplaces", Context.MODE_PRIVATE);

        if (!placesList.isEmpty()) {
            infoButton.setVisibility(View.VISIBLE);
        }


        List<String> printAddressList = new ArrayList<>();
        List<String> printLatitude = new ArrayList<>();
        List<String> printLongitude = new ArrayList<>();
        try {
            printAddressList = (ArrayList) ObjectSerializer.deserialize(sharedPreferences.getString("addressList", ObjectSerializer.serialize(new ArrayList<>())));
            printLatitude = (ArrayList) ObjectSerializer.deserialize(sharedPreferences.getString("latitude", ObjectSerializer.serialize(new ArrayList<>())));
            printLongitude = (ArrayList) ObjectSerializer.deserialize(sharedPreferences.getString("longitude", ObjectSerializer.serialize(new ArrayList<>())));

            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, printAddressList);
            addedPlacesList.setAdapter(arrayAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("addressList", printAddressList.toString());

        final List<String> finalPrintAddressList = printAddressList;
        final List<String> finalPrintLatitude = printLatitude;
        final List<String> finalPrintLongitude = printLongitude;
        addedPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                MapActivity.fromMain = new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(finalPrintLatitude.get(position)), Double.parseDouble(finalPrintLongitude.get(position))))
                        .title(finalPrintAddressList.get(position));
                startActivity(intent);
            }
        });
    }
}
