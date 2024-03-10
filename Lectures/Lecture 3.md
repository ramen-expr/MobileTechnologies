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