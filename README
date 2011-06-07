
***********************************
*                                 *
* Android App : Shazam Tag Viewer *
* Sean Hodges, 2011               *
*                                 *
***********************************


------------
Introduction
------------

The aim was to complete an application for Android that would provide a table of tags for a 
given user, and provide a means to drill down into the tag details for each result. 

The requirements for this app are:

- Android 2.2 or above
- Supported screen sizes: QVGA, HVGA, WVGA
- The APK requires the INTERNET permission

The project in its current state was produced in approx. 20 man hours.


------
Design
------

The project consists of 2 activities and an ASyncTask:

* UsernameEntryActivity
The entry point for the application, allows user to enter a username and pass it to the 
UserTagListActivity.

* UserTagListActivity
Interacts with LoadUserTagsTask and displays the tag list on the screen. 
Selecting a row opens the selected tag details in the default browser.

* LoadUserTagsTask
A background task for interacting with the server and parsing the returned data


Other notable components:

HttpShazamDriver is responsible for communicating with the RSS server and retrieving the 
results, using the HttpClient provided in the Android platform.

RssFeedReader is a SAX parser for processing the RSS XML, it converts the into a set 
results into a set of POJO's for inclusion in the tag list activity.

Activity transitions are handled by the IntentDelegate, this keeps the application workflow in 
one place.

The tests are provided in the test/ directory of the project.


-------------------------------
Integration with Shazam Servers
-------------------------------

The RSS feed provides the list of tags, and all the accompanying information used in the tag list 
activity. The URL in this feed is used in the onClick event of the rows.

If the username was not found on the server, the RSS feed is replaced with an HTML page displaying  
an error message. We capture this event and report it to the user in a dialog. Afterwards they are 
returned to the previous activity.

The RSS request has a current connection timeout of 6 seconds. If this is exceeded, or if the RSS 
processing fails for any other reason than the "user not found" conditions are met, then the app 
displays a friendly error dialog to the user and returns them to the previous activity.

The parameter "no_mobile=1" is added to the tag detail page requests, to ensure the mobile filter 
is ignored and the correct page is returned in the browser. Without this, the website returns a 
special landing page for mobile devices.


-------------------
Future Improvements
-------------------

* Currently the error checking is quite basic, there could be further checking of the HTML error messages 
returned by the Shazam server, for example.

* The test suite could use a better testing framework like Robolectric. This was originally planned, but 
after some issues involving HttpClient the Android platform framework was used instead.

* The app could provide some more information to the user in the list, such as retrieving the album cover 
art for the results.

* On selecting a row, the user is automatically launched into the default Web browser. This can sometimes 
be disorienting to the user without a cue. Perhaps by presenting a warning dialog, or an action pop-up?

