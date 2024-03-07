package com.example.mobiletechapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.Locale;

public class LocationServicesActivity extends AppCompatActivity {
   LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationClient;
    LocationCallback locationCallback;
    private double latitude;
    public double longitude;
    public String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_services);
        requestPermissions();
    }

    public void showCurrentLocation(View view) {
        getLatLngAddress();
    }

    public boolean requestPermissions() {
        int REQUEST_PERMISSION = 3000;
        String permissions[] = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};
        boolean grantFinePermission =
                ContextCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED;
        boolean grantCoarsePermission =
                ContextCompat.checkSelfPermission(this, permissions[1]) == PackageManager.PERMISSION_GRANTED;

        if (!grantFinePermission && !grantCoarsePermission) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION);
        } else if (!grantFinePermission) {
            ActivityCompat.requestPermissions(this, new String[]{permissions[0]}, REQUEST_PERMISSION);
        } else if (!grantCoarsePermission) {
            ActivityCompat.requestPermissions(this, new String[]{permissions[1]}, REQUEST_PERMISSION);
        }

        return grantFinePermission && grantCoarsePermission;
    }

    public synchronized void getLatLngAddress() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(LocationServicesActivity.this,
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                createLocationRequestLocationCallback();
                                startLocationUpdates();
                            }
                        });
    }

    public void createLocationRequestLocationCallback() {
        locationRequest = new LocationRequest.Builder(
                Priority.PRIORITY_HIGH_ACCURACY, 5000).build();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                latitude = locationResult.getLastLocation().getLatitude();
                longitude = locationResult.getLastLocation().getLongitude();
                address = getStreetAddress(latitude, longitude);
                TextView tvlat = findViewById(R.id.textViewLatitude);
                TextView tvlng = findViewById(R.id.textViewLongitude);
                TextView tvaddr = findViewById(R.id.textViewStreetAddress);
                tvlat.setText("Latitude: " + latitude);
                tvlng.setText("Longitude: " + longitude);
                tvaddr.setText("Address: " + address);
            }
        };
    }

    public String getStreetAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String streetAddress = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address address = addresses.get(0);
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    streetAddress += address.getAddressLine(i) + "\n";
                }
            } else {
                streetAddress = "Unknown";
            }
        } catch (Exception e) {
            streetAddress = "Service not available";
            e.printStackTrace();
        }
        return streetAddress;
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLatLngAddress();
    }

}

