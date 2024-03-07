package com.example.mobiletechapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setTitle("Mobile Tech App - Weeks 2-5");
    }

    public void openMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("message", "Hello World!");
        startActivity(intent);
    }

    public void viewMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void viewLocationServices(View view) {
        Intent intent = new Intent(this, LocationServicesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_uievent) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("message", "Hello World!");
            startActivity(intent);
        }
        else if (id == R.id.action_map) {
            Intent intent = new Intent(this, MapsActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewStreetView(View view) {
        Intent intent = new Intent(this, StreetViewActivity.class);
        startActivity(intent);
    }

}