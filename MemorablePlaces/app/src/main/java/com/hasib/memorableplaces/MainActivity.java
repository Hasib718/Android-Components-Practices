package com.hasib.memorableplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

        if (!placesList.isEmpty()) {
            infoButton.setVisibility(View.VISIBLE);
        }

        final List<String> addressList = new ArrayList<>(placesList.keySet());
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, addressList);
        addedPlacesList.setAdapter(arrayAdapter);

        addedPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                MapActivity.fromMain = new MarkerOptions()
                        .position(new LatLng(placesList.get(addressList.get(position)).latitude, placesList.get(addressList.get(position)).longitude))
                        .title(addressList.get(position));
                startActivity(intent);
            }
        });
    }
}
