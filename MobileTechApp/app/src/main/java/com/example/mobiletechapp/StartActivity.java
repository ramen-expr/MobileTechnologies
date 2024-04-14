package com.example.mobiletechapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setTitle("Mobile Tech App - Weeks 2-5");

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#0F9D58"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        // uploadSingleValueToRealtimeDatabase("Week 6", "Testing realtime database");
//        downloadSingleValueFromRealtimeDB("Week 6");
//        uploadMultipleValuesToRealtimeDB(-35.2369777,149.0841217, "UC Building 6");
        downloadMultipleValuesFromRealtimeDB();
        //uploadSingleResourceFileToCloudStorage(R.drawable.
          //      dogs, "my_dogs");
        downloadSingleFilefromStorage("my_dogs");
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
        } else if (id == R.id.action_map) {
            Intent intent = new Intent(this, MapsActivity.class);

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewStreetView(View view) {
        Intent intent = new Intent(this, StreetViewActivity.class);
        startActivity(intent);
    }

    public void uploadSingleValueToRealtimeDatabase(String name, String value) {
        DatabaseReference dbRef = FirebaseDatabase.
                getInstance().getReference("Mobile Tech");
        dbRef.child(name).setValue(value);
    }

    public void downloadSingleValueFromRealtimeDB(String name) {
        DatabaseReference dbRef = FirebaseDatabase.
                getInstance().getReference("Mobile Tech");

        // Create the addListenerForSingleValueEvent event listener from dbRef that
        // contains 2 event listeners (Steps for creating a runtime event listener
        // are in Week 5 Tutorial & Lab) then add the following lines to
        // the onDataChange event listener and uncomment them

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String text_downloaded = snapshot.child(name).getValue().toString();
                Toast.makeText(getApplicationContext(), text_downloaded, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // In case of failures, you can handle them here.
                System.out.println("Something went wrong");
            }
        };
        dbRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public void uploadMultipleValuesToRealtimeDB(double latitude, double longitude, String address) {
        DatabaseReference dbRef = FirebaseDatabase.
                getInstance().getReference();
        String key = "My Key";
        // to generate a random key
        dbRef.child(key).child("latitude").setValue(latitude);
        dbRef.child(key).child("longitude").setValue(longitude);
        dbRef.child(key).child("address").setValue(address);
    }

    public void downloadMultipleValuesFromRealtimeDB() {
        DatabaseReference dbRef = FirebaseDatabase.
                getInstance().getReference();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot key : snapshot.getChildren()) {
                    Toast.makeText(getApplicationContext(),
                            snapshot.child(key.getKey()).getValue().toString(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        dbRef.addChildEventListener(childEventListener);
    }


    public void uploadSingleResourceFileToCloudStorage(int resourceId, String filenameOnCloud) {
        // Reference to cloud storage
        StorageReference storageRef = FirebaseStorage.
                getInstance().getReference();
        // Find the path to a resource file using its resource id
        Uri uri = Uri.
                parse("android.resource://" +
                        R.class.getPackage().getName() + "/" + resourceId);
        // Upload the resource file to cloud storage and rename it to filenameOnCloud
        storageRef.child(filenameOnCloud).putFile(uri);
    }

    public void downloadSingleFilefromStorage(String filenameOnCloud) {
        // fileOnPhone is a local file used to download file filenameOnCloud to it
        File fileOnPhone = null;
        try {
            // Create a temporary file having the same filename
            // with the filename on cloud. Skip the suffix (jpg, png, bmp)
            // as we may not know it and image view can show image without suffix
            fileOnPhone = File.
                    createTempFile(filenameOnCloud, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Get reference to file in storage with this filename
        StorageReference fileRef = FirebaseStorage.
                getInstance().getReference()
                .child(filenameOnCloud);
        // Check if the filenameOnCloud file exists on Cloud storage
        if (fileRef == null) return;
        // Download the filenameOnCloud file to localFile
        // and use localFile to display image on Image View
        if (fileOnPhone != null) {
            File finalLocalFile = fileOnPhone;
            // get filenameOnCloud from cloud storage & save it to fileOnPhone
            fileRef.getFile(fileOnPhone)
                    .addOnSuccessListener(
                            new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Uri uri = Uri.
                                            fromFile(finalLocalFile);
                                    ImageView imageView = (ImageView) findViewById(R.id.
                                            imageViewStart);
                                    imageView.setImageURI(uri);
                                }
                            })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                                    Toast.
                                            makeText(getApplicationContext(), "Unable to download ",
                                                    Toast.
                                                            LENGTH_SHORT).show();
                                }
                            });
        }
    }
}
