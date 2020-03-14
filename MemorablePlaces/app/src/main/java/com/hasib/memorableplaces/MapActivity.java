package com.hasib.memorableplaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    private GoogleMap gMap;
    private Marker newMarker;
    private MarkerOptions markerOptions;
    static MarkerOptions fromMain = null;
    static Marker newFromMain;

    public void backToListView(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        MainActivity.arrayAdapter.notifyDataSetChanged();
        newMarker.remove();
        //newFromMain.remove();
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        fetchLastLocation();
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLocation = location;

                SupportMapFragment supportMapFragment = (SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.google_map_view);
                supportMapFragment.getMapAsync(MapActivity.this);
            }
        });
    }

    private String geoCoding(LatLng latLng) {
        String addressLine = "";
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (addressList != null && addressList.size() > 0) {
                Log.i("PlaceInfo", addressList.get(0).toString());

                addressLine = addressList.get(0).getAddressLine(0);
                //return addressLine;
                //Toast.makeText(MapActivity.this, addressLine, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressLine;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Marker temp = newMarker;
        newMarker = gMap.addMarker(new MarkerOptions().position(latLng).title(geoCoding(latLng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        temp.remove();

        MainActivity.placesList.put(geoCoding(latLng), latLng);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        String addressLine = "";
        gMap = googleMap;
        gMap.setOnMapLongClickListener(this);

        addressLine = geoCoding(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));

        markerOptions = new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title(addressLine);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 15));
        gMap.addMarker(markerOptions);
        if (fromMain != null) {
            newFromMain = gMap.addMarker(fromMain.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
            newMarker = gMap.addMarker(markerOptions);

    }
}
