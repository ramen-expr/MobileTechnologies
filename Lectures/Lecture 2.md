#Lecture #MobileTech
Lecture starts with a bunch of assignment related info and lab ideas. At time of writing, this has already been completed so will be skipping this section.

## Activities
An activity is simply a screen within the app. It is focused on one function/feature of the application. There is a life cycle of an activity, as seen below.

![[Pasted image 20240306143058.png]]
The Activity class holds all the above functions. More info can be found [here](https://developer.android.com/guide/components/activities/intro-activities).

Side note: Found an awesome YouTube channel, which dives into things like
- [Retaining variables when rotating device](https://www.youtube.com/watch?v=TcTgbVudLyQ&ab_channel=CodinginFlow)
- [App lifecycle explained](https://www.youtube.com/watch?v=UJN3AL4tiqw&list=PLrnPJCHvNZuAe5r049EpzxQGZSybBX_tk&index=6&ab_channel=CodinginFlow)
## onCreate()
The `onCreate` method is always called as follows:
```Java
@Override
protected void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	// Some more code below
}
```

The `savedInstanceState` is parsed as a parameter, which is a [bundle object](https://developer.android.com/reference/android/os/Bundle), containing the previous saved state of the activity. If it has never existed before, it will be a `null` value. Bundle objects are used to pass data between different activities ([ref](https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application)).

## onStart()
The `onStart` method makes the activity available for the user to see, and it will prepare to come into the foreground. 

## onResume()
When the activity enters the Resumed state, it comes to the foreground, and then the system invokes the onResume() callback.

## onPause()
Only enters this phase for extended periods when the activity is only partly hidden. This is the only guaranteed stage that an activity will reach every single time when exiting from an activity.  

## onStop()
When an activity is completely hidden, it enters the stopped stage. This is where heavy operations should be done, like saving data.

## onDestroy()
DO NOT SAVE YOUR DATA HERE. It may not be saved, as there is no guarantee you will enter this phase. Things that can trigger this phase:
- Closing application
- Pressing back button
- Rotating the device
In this phase, you should be doing clean up

