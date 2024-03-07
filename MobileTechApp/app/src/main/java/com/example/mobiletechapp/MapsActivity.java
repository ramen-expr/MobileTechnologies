package com.example.mobiletechapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mobiletechapp.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    //    MyLocationPlace myLocationPlace;
    private double latitude;
    public double longitude;
    public String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng uc = new LatLng(-35.24, 149.08);

        Marker myMarker = mMap.addMarker(new MarkerOptions()
                .position(uc)
                .title("Marker in UC")
                .snippet("Latitude: -35.24 and Longitude: 149.08")
                .rotation(90)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dogs40))
        );


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                if (marker.equals(myMarker)) {
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker());
                }
                return false;
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){
            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker){
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
                TextView title = infoWindow.findViewById(R.id.textViewTitle);
                TextView snippet = infoWindow.findViewById(R.id.textViewSnippet);
                ImageView image = infoWindow.findViewById(R.id.imageView);
                if (marker.getTitle() != null && marker.getSnippet() != null) {
                    title.setText(marker.getTitle());
                    snippet.setText(marker.getSnippet());
                } else {
                    title.setText("No info available");
                    snippet.setText("No info available");
                }
                image.setImageDrawable(getResources().getDrawable(R.mipmap.ic_dogs, getTheme()));
                return infoWindow;

            }

            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker){
                return null;
            }
        });

        // UC Hospital boundary
        ArrayList<LatLng> uchospital = new ArrayList<>();
        uchospital.add(new LatLng(-35.2336015, 149.0804717));
        uchospital.add(new LatLng(-35.2329108, 149.0798677));
        uchospital.add(new LatLng(-35.2327255, 149.0799932));
        uchospital.add(new LatLng(-35.2321494, 149.0803377));
        uchospital.add(new LatLng(-35.2315465, 149.0806325));
        uchospital.add(new LatLng(-35.2314683, 149.08069));
        uchospital.add(new LatLng(-35.2313886, 149.0808109));
        uchospital.add(new LatLng(-35.2313771, 149.0809849));
        uchospital.add(new LatLng(-35.231435, 149.0814239));
        uchospital.add(new LatLng(-35.2315127, 149.0818787));
        uchospital.add(new LatLng(-35.2316636, 149.082554));
        uchospital.add(new LatLng(-35.2336131, 149.0825558));
        uchospital.add(new LatLng(-35.2336015, 149.0804717));

        // Create a polygon and add options
        PolygonOptions uchPolygonOptions = new PolygonOptions().geodesic(true);
        for (LatLng latLng : uchospital) {
            uchPolygonOptions.add(latLng);
        }
        uchPolygonOptions.strokeColor(Color.RED);
        uchPolygonOptions.strokeWidth(15.0f);

        // Add the polygon (UC Hospital boundary) to the map
        mMap.addPolygon(uchPolygonOptions);

        // Use builder to zoom in the map to the greatest possible zoom level
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : uchospital) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();

        // Set padding (space around the boundary)
        int padding = 40;
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));

   }
}