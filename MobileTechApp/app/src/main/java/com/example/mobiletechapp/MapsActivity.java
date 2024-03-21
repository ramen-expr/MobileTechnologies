package com.example.mobiletechapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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

    public void showDirection(View view) {
        LatLng cit = new LatLng(-35.247502915070626, 149.09557187772742);
        LatLng uc = new LatLng(-35.24, 149.08);
        mMap.moveCamera(CameraUpdateFactory.
                newLatLngZoom(uc, 14));
        String apiKey = getMapApiKey(this);
        drawRoute(apiKey, cit, uc);
    }

    public void drawRoute(String apiKey, LatLng origin, LatLng destination) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + origin.latitude + "," + origin.longitude + "&destination="
                + destination.latitude + "," + destination.longitude
                + "&mode=driving&key=" + apiKey;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.
                        GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
// Parse the JSON response and draw the route on the map
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.color(Color.
                                RED);
                        polylineOptions.width(14);
                        JSONArray routes = null;
                        try {
                            routes = response.getJSONArray("routes");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        for (int i = 0; i < routes.length(); i++) {
                            try {
                                JSONObject route = routes.getJSONObject(i);
                                JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
                                String points = overviewPolyline.getString("points");
                                List<LatLng> path = PolyUtil.
                                        decode(points);
                                polylineOptions.addAll(path);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        mMap.addPolyline(polylineOptions);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.
                newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }
    public String getMapApiKey(Context context) {
        String apiKey = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.
                            GET_META_DATA);
            Bundle bundle = appInfo.metaData;
            if (bundle != null) {
                apiKey = bundle.getString("com.google.android.geo.API_KEY");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

}