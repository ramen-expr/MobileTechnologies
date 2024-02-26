### Course Info
**Course Convenor/Lecturer**
**Name:** Dat Tran
**Email:** [dat.tran@canberra.edu.au](mailto:dat.tran@canberra.edu.au)
**Office:** 6C24
**Phone Number:** 02 6201 2394

\*Note, this lecturer has a habit of making snowballing assessments/content. CRITICAL that you stay on top of this class.

We will be developing in Android Studio using Java(!!!)

I have set up the environment now on the office computer. Now we should be ready to start!
___
### Course Intro
The name of the game: mobile technology. Crazy, right? But, the way mobile phones, watches and other devices are taking the world by storm at the moment, we would be crazy to not look into it. We will look into various parts of the mobile space, such as performance issues, developing apps, system testing, etc.
![[UnitOutlineMT.pdf]]

Here is the uni outline, keep it in mind. Good reference for things that need to be learnt and assessment information.

Mobile Technologies are growing ridiculously fast. Apparently we will be working with location-based map services and more. We will also do cloud computing (DB stuff) and Machine Learning incorporated with this unit.

Next week he is sending us a link to Google Cloud so we can use an API to use for DB in our applications.

Firebase for data and machine learning

We are focusing on Android technologies. Potentially good to look into some resources and news surrounding it. some are:

- Android Developer
- Firebase
- Tutorials Point (can look at during work)
- Apple Developers (eh)
- ChatGPT (with caution)

Final mark is the only criteria. Keep each of the weightings in mind, and be ready for tutorials ahead of time. Keep final assignment also in mind, plug away at it over the weeks. Make sure to have at least one commit per week. Even a little bit is better than nothing.
___
### Java Programming

Java is a faster platform to things such as Python, because it is a lower level language. Java requires braces `{}` to define blocks of code, and semicolons `;` to end statements. It doesn't look at indentation, but please still indent your code, it looks better and is easier to follow. It looks like this unit is over complicating Java again, yipee. I'll write up key ideas myself.

Most of these first slides and basics of Java are covered in ST2 [[Software Technology 2/Lectures/Lecture 1|Lecture 1]]. If you need more detail, check that out.

There are some parts from this lecture that are nice to mention in the ST2 Lecture notes so will add there, such as:
- Try/catch/finally
- var = logic_statement ? option1 : option2;
- Interfaces
___
### Android Project, User Interfaces, Events and Event Handlers
When developing an application, the project have have many different "activities", which makes up the different faces of the application. You can access it by going on the file explorer, going to the "java" folder, and then the sub-folder which holds the activities "com.\*.\*", and then right clicking, then "New" > "Activity" > "Gallery", and from there you can choose what type of frame you want to have. 

*Note: When creating an activity, there is a checkbox for "Launcher Activity. Selecting this will force this activity to be the first activity when application is launched.*

These are the ramblings of a madman. Will need to come back and figure out basic layout and understanding of Android Studio. There was something about build scripts, etc.
___
### View/ViewGroup
The GUI for an Android app is based on a "hierarchy of **View** and **ViewGroup** object instances". The **View** class is a super-class of a bunch of components, such as:
- **TextView**
- **EditText**
- **ImageView**
- **ProgressBar**
- **Button**
- **ImageButton**
- **CheckBox**
- **DatePicker**
The **ViewGroup** is a subclass of the **View** class. **ViewGroup** objects are containers and compartments for other **View** objects to be arranged into. Some of the most common types of **ViewGroup** objects are:
- **LinearLayout**
	- Arranges its children in a single column or row. Direction of this can be determined by calling `setOrientation()`.
	- ==There is also something about `setGravity()` but is not too well elaborated on.== 
- **ContraintLayout**
- **ListView**
- **GridView**
And **Layouts** are a subclass of the **ViewGroup** class. You may also nest these ViewGroups!

There is also a drag and drop option to place and design elements onto an activity, which allows for more simple design process.
___
### Click Event and Event Handler (Listener)
This is to be covered in week 2 tutorial, but the idea is that when a user does something, the application responds in real-time, such as pushing a button and something changing. He gives an example of this in the lecture but for some reason the function that is called once the button is pressed requires a View object to be parsed, but never uses it, and it all still *works?*
___
### Actions
- [x] Make sure UpSideDownCake (Android 14) is installed in Android Studio
- [x] Create git repo for course
- [x] Assignment dates and weightings printed and put up on wall
- [x] Prepare for tutorial
	- [x] Give the tutorial a first go, see if any roadblocks are hit
	- [x] Write up any theory missing
	- I finished it lol 
- [x] Learn more about Android Studio
