# TheLastRaider
### ICS4U final project


top down zombie survival game, focusing on melee combat
map made with a modified version of an editor for a different game (LastRaiderEditor.zip)
map and code by James
audio and most art assets, and map renders by Nathan Desjardins

## Code Highlights
parts of the code that were more difficult for me:
A* path finding (/util/AStar.java, /util/Node.java)
Entity system, especially movable entities (/entities/moveable)
Gesture base attack system, it does not work that well, but it was new to me (/entities/movable/combat/Player.java) 

## Debug
press f3 to enter debug mode
this will show fps, player info, and hitboxes
pressing v will toggle collision for the player

## Story
The school has been infected with zombies overnight. They seem to have some generic airborne disease causing there zombification.
You, as the last combat ready Beal Raider must clear out the school of any zombies. The source of the infection seems to be coming 
from the gym, make your way down there and find out what is happening.

## How To Play

to go between floors, walk to the top/bottom of the staircase you would normally 

### movement
     wasd - move
     space - dash in current direction

escape - pause open and options

### combat
### click mode
     lmb - swipe
     rmb - jab
     hold lmb and rmb - spin
### motion mode
     rmb - turn towards mouse
     hold lmb and drag a
         line away from player - jab
         line perpendicular to player - swipe
         circle - spin