# cse-110-team-project-team-34
cse-110-team-project-team-34 created by GitHub Classroom

General Guidelines:

----------------------------------------------------------------------------------------------

When pulling/checkout

Try:
Opening Android Studio -> Checkout Project from VCS -> Github -> then fill in the details on the popup

There might be situation that you might not be able to load 2 modules (click on the remove modules option)
and then build. Check that you can see a preview of a layout before doing anything

Option 2: If the above doesn't work
download git bash from here https://git-scm.com/downloads

open git bash -> navigate to your android project directory -> use the following command
git clone https://github.com/YOUR-USERNAME/YOUR-REPOSITORY
it will be: git clone https://github.com/CSE-110-Winter-2018/cse-110-team-project-team-34.git

then open the project on Android Studio (the same issue with the module will arise)

before every session also do: git pull origin master


When pushing,

the master branch is protected so you cannot push to it

you should push to your own branch
if you have never created your own branch
use (obviously, replace my name with yours): git checkout -b developer_Samprith

then add any files with: git add <filename>
then commit with: git commit -m <message-include your task number>
then push with: git push --set-upstream origin <remote-branch-name>


Then get someone to review your code and merge,
after every merge make sure that the master branch builds/compiles and works as expected


-----------------------------------------------------------------------------------------


Control Flow

HomeActivity ------Press Albums--------> ListActivity (display list of albums)
HomeActivity ------Press Artists-------> ArtistActivity (display list Albums and Songs button)
HomeActivity ------Press Songs---------> ListActivity (display list of songs)

ListActivity (with list of albums) -----Press Album-------> List Activity (with list of songs)
ListActivity (with list of songs)  -----Press Song--------> PlaySongActivity (and song starts playing)

ArtistActivity -----Press Albums------> ListActivity (display albums)
ArtistActivity -----Press Songs-------> ListActivity (display songs)

ToggleFlashBack Mode anytime ---------> PlayFlashBackActivity (and song starts playing)

----------------------------------------------------------------------------------------------
Super Useful API: https://developer.android.com/reference/android/media/MediaMetadataRetriever.html


