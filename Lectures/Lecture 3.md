#Lecture #MobileTech 
## Location Services
Almost every mobile device nowadays has inbuilt GPS capabilities. It allows for people to quickly and accurately determine their position. When designing applications that require a location, it is almost always most practical to make use of the GPS system.

Latitude is a geographic coordinate that specifies the north-south position of a point on the Earth's surface. Latitude is an angle (f) between -90° and 90° (0° at the Equator, 90° North pole, and -90° South pole). A negative latitude will be found south of the equator. 

Longitude is a geographic coordinate that specifies the east-west position of a point on the Earth's surface, or the surface of a celestial body. Longitude (l) is an angular measurement, between -180° and 180°.  A negative longitude will be found west of the prime meridian.

## Google Location Services
Google has a massive framework for location services. Utilising the framework allows for super quick implementation for maps and location related services.

## Implementing Location Services with Android
Android allows applications to access the location of a device through the package `android.location`. The central component of the location framework is the LocationManager system service, which provides APIs to determine location and bearing of the underlying device (if available).

With LocationManager, your app is able to do 3 things: 
- Query for the list of all LocationProviders for the last known user location. 
- Register/unregister for periodic updates of the user's current location from a location provider (specified either by criteria or name). 
- Register/unregister for a given Intent to be fired if the device comes within a given proximity (specified by radius in meters) of a given latitude/longitude.

## Install Google Play SDK
First step in all of this is making sure we have the right tools installed to interact with the Google framework. Go to the Android Studio preferences/settings and head to "Android SDK" (you should be able to search for it). Under the "SDK Tools" tab, make sure "Google Play services" are ticked and installed.

Now that it's installed, let's implement it into the application.

### Include Google Play Services in App Dependencies
In the Gradle Scripts folder, we need to specify Google services as a dependency, so that it includes the Play service packages we need when building the application. Go to `Gradle Scripts/build.gradle.kts` for your application module (most likely just "app"). At the bottom of the file, you should see something like this:

```KTS
dependencies {  
    implementation("androidx.appcompat:appcompat:1.6.1")  
    implementation("com.google.android.material:material:1.11.0")  
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")  
    implementation("com.google.android.gms:play-services-maps:18.2.0")  
    implementation("com.google.maps.android:android-maps-utils:2.2.0")    
    testImplementation("junit:junit:4.13.2")  
    androidTestImplementation("androidx.test.ext:junit:1.1.5")  
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")  
}
```

Make sure you include this line:
```KTS
    implementation("com.google.android.gms:play-services-location:21.1.0")  
```

### Update Required App Permissions
Now that we have the tools, we need to determine what permissions are needed. In the `AndroidManifest.xml` we should see the following:
```xml
<?xml version="1.0" encoding="utf-8"?>  
<manifest xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools">  
	<uses-permission android:name="android.permission.INTERNET" />  
	<!-- Always include this permission -->  
	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />  
	<!-- Include only if your app benefits from precise location access. -->  
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />  
	<application  
	android:allowBackup="true“  
	. . .
```

Note that in order to use the devices fine location, the coarse location must always be included first.

### Request Location Permissions
Next step is to actually ask the user for permission. This next block of code is effectively a copy-paste situation, there is little point in trying to memorise it. Include this code in the .java file for the activity that requires location permissions.

```Java
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
```

and then in the `onCreate()` method, call this as such:

```java
@Override  
protected void onCreate(Bundle savedInstanceState) {  
    super.onCreate(savedInstanceState);  
    setContentView(R.layout.activity_location_services);  
    requestPermissions(); // This command right here!!
}
```

Now, permissions have been granted, but there is no code to actually retrieve our location.

### Get User Location
The following is able to save the location of the user into variables `latitude` and `longitude`. Similar to the above, I don't recommend getting stuck into this part too much, it's more of a copy and paste situation. Use this in the .java file of the activity that requires user location.

```java
private double latitude;  
public double longitude;


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
        }  
    };  
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
```

### Get Street Address
For some applications, it might be beneficial to include a street address grabbed from the latitude and longitude. If that is the case, you can use the following:

```Java
public void getStreetAddress(double latitude, double longitude) {  
	// Get a Geocoder instance  
	Geocoder geocoder = new Geocoder(this, Locale.getDefault());  
	// Create a listener to handle results  
	Geocoder.GeocodeListener geocodeListener = new Geocoder.GeocodeListener() {  
		@Override  
		public void onGeocode(@NonNull List<Address> addresses) {  
			String streetAddress = "";  
			if (addresses != null && addresses.size() > 0) {  
				Address address = addresses.get(0);  
				for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {  
					streetAddress += address.getAddressLine(i) + " ";  
				}  
			} 
			else {  
				streetAddress = "No address found.";  
			}  
		}  
	};  
	geocoder.getFromLocation(latitude, longitude, 1, geocodeListener);  
}
```

## Places SDK
So we can access our location information, but we are still yet to implement maps and whatnot. Fear not, this is where it begins. We are able to actually include our own maps, places within the map (custom markers, custom locations, etc.).

We start by going back to the Gradle build file and including another dependency:
```KTS
implementation("com.google.android.libraries.places:places:3.3.0")
```

==--incomplete--==
