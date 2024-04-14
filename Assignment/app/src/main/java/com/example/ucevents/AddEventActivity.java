package com.example.ucevents;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#000080"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Add New Activity and Edit It");

        // Set the location options
        Spinner locations = findViewById(R.id.spinnerLocation);

        ArrayList<String> locs = new ArrayList<>();
        locs.add("Select a location");
        locs.add("Library");
        locs.add("Refectory");
        locs.add("Building 6");
        locs.add("UC Hub");
        locs.add("UC Lodge");
        locs.add("Current Location");

        ArrayAdapter<String> loc_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, locs);

        loc_adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        locations.setAdapter(loc_adapter);
    }


}