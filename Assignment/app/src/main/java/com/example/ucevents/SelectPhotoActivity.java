package com.example.ucevents;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class SelectPhotoActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int PICK_IMAGE = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static ImageView imageView;
    private static Bitmap imageBitmap;
    private static Uri selectedImage;
    private boolean bitmap;
    private String photoName;



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
        setContentView(R.layout.activity_select_photo);
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

        imageView = findViewById(R.id.imageView2);
        checkPermissions();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permissions granted.
            } else {
                // permissions not granted.
            }
        }
    }

    public void openCameraActivity(View view) {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, REQUEST_IMAGE_CAPTURE);
    }

    public void openGalleryActivity(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            bitmap = true;
        } else if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            bitmap = false;
        }
    }

    public void uploadData(View view){
        uploadInstance();
        uploadPhoto();
    }

    public void uploadInstance() {
        DatabaseReference dbRef = FirebaseDatabase.
                getInstance().getReference();
        String key = eventName;
        // to generate a random key
        photoName = dbRef.push().getKey();

        dbRef.child(key).child("Title").setValue(activityName_val);
        dbRef.child(key).child("Summary").setValue(summary_val);
        dbRef.child(key).child("Times").setValue(times_val);
        dbRef.child(key).child("Dates").setValue(dates_val);
        dbRef.child(key).child("Contact").setValue(contact_val);
        dbRef.child(key).child("Location").setValue(location_val);
        dbRef.child(key).child("Photo").setValue(photoName);
    }

    protected void uploadPhoto() {
        String filenameOnCloud = "";
        // Reference to cloud storage
        StorageReference storageRef = FirebaseStorage.
                getInstance().getReference();
        // Find the path to a resource file using its resource id
        if (!bitmap){
            Uri uri = selectedImage;
            storageRef.child(filenameOnCloud).putFile(uri);
        }
        else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = storageRef.putBytes(data);
        }
    }
}