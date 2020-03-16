package com.hasib.currentlocationnewmethod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MainActivity";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int DEFAULT_ZOOM = 15;

    private boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mapGoogle;
    private Location currentLocation;
    private Geocoder geocoder;
    private SearchView searchView;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapGoogle = googleMap;

    }

    public void searchLocation(View view) {
        Log.d(TAG, "searchLocation: entered search method");

    }

    public void fetchDeviceLocation(View view){
        Log.d(TAG, "fetchDeviceLocation: getting the device current location");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionsGranted) {
                final Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
                locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (locationTask.isSuccessful()) {
                            Log.d(TAG, "onSuccess: found location!");

                            currentLocation = (Location) locationTask.getResult();
                            //Toast.makeText(MainActivity.this, currentLocation.getLatitude()+" "+currentLocation.getLongitude(), Toast.LENGTH_LONG).show();
                            mapGoogle.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));
                            mapGoogle.addMarker(new MarkerOptions()
                                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .title(geoCoding(new LatLng(location.getLatitude(), location.getLongitude()))));
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.d(TAG, "fetchDeviceLocation: "+e.getMessage());
        }
    }

    private String geoCoding(LatLng latLng) {
        String addressLine = "";

        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (addressList != null && addressList.size() > 0) {
                Log.d(TAG, "geoCoding: "+addressList.get(0).toString());

                addressLine = addressList.get(0).getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addressLine;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        searchView = (SearchView) findViewById(R.id.locationSearchView);

        getLocationPermisson();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String submittedQuery = searchView.getQuery().toString();
                List<Address> addressList = null;

                try {
                    addressList = geocoder.getFromLocationName(submittedQuery, 1);

                    Log.d(TAG, "onQueryTextSubmit: found submitted location");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onQueryTextSubmit: "+e.getMessage());
                }
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                //Toast.makeText(MainActivity.this, latLng.toString(), Toast.LENGTH_LONG).show();
                mapGoogle.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 15f));
                mapGoogle.addMarker(new MarkerOptions()
                        .position(new LatLng(address.getLatitude(), address.getLongitude()))
                        .title(geoCoding(new LatLng(address.getLatitude(), address.getLongitude()))));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void getLocationPermisson() {
        Log.d(TAG, "getLocationPermisson: getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mLocationPermissionsGranted = true;
            initMap();
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_Map);
        supportMapFragment.getMapAsync(MainActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0){
                    for (int i=0; i<grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permisson failed");
                            return;
                        }
                    }

                    Log.d(TAG, "onRequestPermissionsResult: permisson granted");
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }
}
