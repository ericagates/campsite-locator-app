#  CS-360 Erica Gates
## Final Project Stepping Stone Additional API Assignment

1.	Home screen: Clicking button will take sure to User Login Activity

2.	Local storage component (Shared Preferences) is implemented here. The username and password can
be saved and will automatically populate after being saved. For Facebook Test user login:
user name: 	arhebdzyrv_1587703422@tfbnw.net
password: TestTest123

3.	The welcome activity gives the user the option to Search for a campsite, or Add/Update a Campsite.

4.	The Add Campsite Activity implement the Create, Update, and Delete operation for the Campsite
database. Initial data has not yet been added to database but can be added using the Add function.
The data is validated by requiring all fields to be entered for both the Add and Update buttons.
Only the campsite name is required to Delete. A scroll feature has been implemented as well as a
confirmation message using Toast. The “Upload Photo” button takes the user to their gallery to
select a photo to upload.
**NOTE** AT LEAST ONE PHOTO MUST BE IN GALLERY TO SELECT FROM. See StackOverflow on how to access gallery
photos using the Android emulator: [Link] https://stackoverflow.com/questions/13003477/no-images-found-in-gallery

5.	The Search Activity implements the Read operation on the Campsite table. It currently defaults
to search by name but can also be searched by feature or City, State. “Search for Campsites
Near Me” button gets the user's currently location and integrates with GoogleMaps API to display the
user's current location and 3 simulated campsites in California.
**NOTE** If maps does not show up please wipe any previous data using the Android Virtual Device Manager




