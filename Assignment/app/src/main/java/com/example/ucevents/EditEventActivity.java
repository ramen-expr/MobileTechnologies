package com.example.ucevents;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class EditEventActivity extends AppCompatActivity {

    String eventName = null;
    String activityName_val = null;
    String summary_val = null;
    String times_val = null;
    String dates_val = null;
    String contact_val = null;
    String location_val = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_event);
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
        actionBar.setTitle("Edit An Existing Activity");

        // Get the info from the previous page
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventName = null;
            } else {
                eventName= extras.getString("eventTitle");
                activityName_val = extras.getString("activityTitle");
                summary_val = extras.getString("activitySummary");
                times_val = extras.getString("activityTimes");
                dates_val = extras.getString("activityDates");
                contact_val = extras.getString("activityContact");
                location_val = extras.getString("activityLocation");
            }
        }

        // Grab all the variables
        TextView title = findViewById(R.id.TextViewActivityTitle);
        title.setText(eventName);

        EditText activityName = findViewById(R.id.editTextTitleField);
        activityName.setText(activityName_val);

        EditText summary = findViewById(R.id.editTextSummary);
        summary.setText(summary_val);

        EditText times = findViewById(R.id.editTextTimeEvent);
        times.setText(times_val);

        EditText dates = findViewById(R.id.editTextDateEvent);
        dates.setText(dates_val);

        EditText contact = findViewById(R.id.editTextContact);
        contact.setText(contact_val);

        EditText location = findViewById(R.id.editTextLocation);
        location.setText(location_val);
    }

    public void openPhotoSelectActivity(View view) {
        Intent intent = new Intent(this, SelectPhotoActivity.class);
        intent.putExtra("eventTitle", eventName);
        intent.putExtra("activityTitle", activityName_val);
        intent.putExtra("activitySummary", summary_val);
        intent.putExtra("activityTimes", times_val);
        intent.putExtra("activityDates", dates_val);
        intent.putExtra("activityContact", contact_val);
        intent.putExtra("activityLocation", location_val);
        startActivity(intent);
    }
}