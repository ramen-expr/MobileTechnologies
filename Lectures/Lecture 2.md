#Lecture #MobileTech
Lecture starts with a bunch of assignment related info and lab ideas. At time of writing, this has already been completed so will be skipping this section.

## Activities
An activity is simply a screen within the app. It is focused on one function/feature of the application. There is a life cycle of an activity, as seen below.

![[Pasted image 20240306143058.png]]
The Activity class holds all the above functions. More info can be found [here](https://developer.android.com/guide/components/activities/intro-activities).

Side note: Found an awesome YouTube channel, which dives into things like
- [Retaining variables when rotating device](https://www.youtube.com/watch?v=TcTgbVudLyQ&ab_channel=CodinginFlow)
- [App lifecycle explained](https://www.youtube.com/watch?v=UJN3AL4tiqw&list=PLrnPJCHvNZuAe5r049EpzxQGZSybBX_tk&index=6&ab_channel=CodinginFlow)
### onCreate()
The `onCreate` method is always called as follows:
```Java
@Override
protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	// Some more code below
}
```

The `savedInstanceState` is parsed as a parameter, which is a [bundle object](https://developer.android.com/reference/android/os/Bundle), containing the previous saved state of the activity. If it has nevintent.putExtra("message", "Hello World!");  intent.putExtra("message", "Hello World!");  intent.putExtra("message", "Hello World!");  er existed before, it will be a `null` valintent.putExtra("message", "Hello World!");  ue. Bundle objects are used to pass data between different activities ([ref](https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application)).

### onStart()
The `onStart` method makes the activity available for the user to see, and it will prepare to come into the foreground. 

### onResume()
When the activity enters the Resumed state, it comes to the foreground, and then the system invokes the onResume() callback.

### onPause()
Only enters this phase for extended periods when the activity is only partly hidden. This is the only guaranteed stage that an activity will reach every single time when exiting from an activity.  

### onStop()
When an activity is completely hidden, it enters the stopped stage. This is where heavy operations should be done, like saving data.

### onDestroy()
DO NOT SAVE YOUR DATA HERE. It may not be saved, as there is no guarantee you will enter this phase. Things that can trigger this phase:
- Closing application
- Pressing back button
- Rotating the device
In this phase, you should be doing clean up

## Changing Layout
When an activity is created, the Java file is automatically populated with a layout file in `app/res/layout/`. The layout is able to be changed by changing the `setContentView(...)` function within the Java file.

```Java
@Override
protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.LAYOUTNAME);
	// More under here
```

Where `LAYOUTNAME` is, you are able to add the name of your preferred layout. Then you can change it to whatever you need/want.

## Add New Activity
Adding a new activity can be done by right clicking the `com.example.myApplication` folder (no brackets after it) and going to `New > Activity > Gallery...`. From there you can name the activity and define the language it should use (Java). Once created, the activity should show up within the `AndroidManifest.xml` file.

To make an activity the one that launches when we open the app, change the following in `AndroidManifest.xml`:
```XML
<activity  
	android:name=".StartActivity"  
	android:exported="true">  
	<intent-filter>  
		<action android:name="android.intent.action.MAIN" />  
		<category android:name="android.intent.category.LAUNCHER" />
	</intent-filter>  
</activity>
```

Specifically, the fields
- `android:exported`
- `intent-filter`

## Moving Between Activities
Quick note before jumping right in, I found a [great tutorial](https://www.javatpoint.com/android-startactivityforresult-example) on getting information based on results from a different activity. 

In order to move between activities, we use intents. Intent is able to pass data and then open a new activity(!!) which is so cool. The syntax is as follows:

```Java
Intent intent = new Intent(this, Activity2.class);  
startActivity(intent);
```

This will likely be found in some event function, like the click of a button. This particular example does not pass any data. If we are wanting to pass data, we can use another line:

```Java
Intent intent = new Intent(this, Activity2.class);
intent.putExtra("nameOfVar", "Value!");
startActivity(intent);
```

Then, in the activity 2 `.class` file, we can receive this data by using the following:

```Java
Bundle extras = getIntent().getExtras();
String msg = extras.getString("nameOfVar");
System.out.println(msg);
>>> "Value!"
```

## Parent vs Child Activities
Sometimes we want an activity to be a child process (falls under the parent in this case), so we can make an activity fall under the hierarchy by altering its record in `AndroidManifest.xml`. 

```XML
<activity  
	android:name=".EventActivity"  
	android:exported="false"  
	android:parentActivityName=".MainActivity" />
```

This will also create a back button so you can return to the parent activity.

Also, while writing I had an idea and went looking: for storing data on the device locally, [see this](https://developer.android.com/training/data-storage).

## Supporting Different Display Sizes/Features
One important thing to consider is how your application looks based on screen size. Some phones may have a tiny screen size, others may be HUGE, and your application may look terrible if your app was designed around a medium phone.

Different things to consider:
- Screen size/Orientation
	- You can include a number of different directories to enable different layouts for different screen sizes. The directory names should be like:
		- `res/layout/main.xml`
		- `res/layout-land/main.xml`
		- `res/layout-large/main.xml`
		- `res/layout-large-land/main.xml`
- Pixel density
- Resolution
- Languages
- Themes
	- Users will want applications that have light and dark modes, so that they are able to theme it to the rest of their phones/mobile devices. 

## Menu Action Bar
Users want quick access to functions, so we need to be able to provide that quickly and effectively. Thing to note is that the Basic Activity template *does* have the Action Bar by default. The Empty Activity template does not have it, but we are able to add it manually.

With adding activities to the action bar, you can either have the activity shown or not. If an activity is not shown, it can be brought up in a list view with the right-most button (known as the overflow button).

![[Pasted image 20240306225221.png]]

You can also customise the Action Bar and overflow button icon.

### Adding menu manually
#### Add a menu directory
on `res/`, right click and add a new Android Resource Directory and name this directory `menu`. 
#### Add menu file
On the folder you just created, right click and add a new Android Resource File, naming it `menu_main` or something similar like `menu_*`, depending on what activity requires the action bar. 
#### Add the actions to menu
In this new file, you can add the relevant activities into the XML.
```XML
<item  
	android:id="@+id/map_normal"  
	android:orderInCategory="1"  
	android:title="Normal"  
	app:showAsAction="never" />  

<item  
	android:id="@+id/map_satellite"  
	android:orderInCategory="2"  
	android:title="Satellite"  
	app:showAsAction="never" />
```
#### Verify type of activity
In the Java file for the activity, make sure that the activity extends the correct class type. The correct class type in this case is `AppCompactActivity`.
```Java
public class MainActivity extends AppCompatActivity {
	...
}
```

#### Add the menu to the activity
Currently, the menu and the desired activity have been built separately, now to add the menu to the activity desired, write the following in the desired activity:

```Java
@Override  
public boolean onCreateOptionsMenu(Menu menu) {  
	getMenuInflater().inflate(R.menu.menu_main, menu);  
	return true;

@Override  
public boolean onOptionsItemSelected(MenuItem item) {  
	int id = item.getItemId();  
	if (id == R.id.action_uievent) {  
		Intent intent = new Intent(this, MainActivity.class);  
		startActivity(intent);  
	}  
	return super.onOptionsItemSelected(item);  
}
```

==More study is going to be required in order to understand what is going on here.==
