IntroFinal-DogFight
===================

This is the game I made for my Intro-Java class project final.  This program utilizes AI, hit-detection of rotated objects, and particle effects

Amit Saxena, 5/5/13

This program is a WWII fighter plane game where the users can chooses to play as 3 countries: Germany, USA, or Japan.
This program is meant for entertainment and the pleasure in beating your friends.
Anyone is capable of using this program, it is designed for all age groups and anyone who wants to take a quick 5 minute break


Features
* Up to 3 players can play the game at a time, they can play as the mentioned factions listed above. 
*  When the program is run a menu will appear where the users can…
	-  choose allegiance
	- number of wingmen
        - choose which Player they are(each player has different controls)
* Once the “start” button is clicked, each user tries to shoot down all enemy planes to win the game.
  - start button doesnt start the game if a player is managing more than one team(not allowed)
* Each plane will be equipped with a machine gun which rapidly fires bullets
* If any team kills 4 enemy planes, they get 1 reinforcement.
* If the currently loosing team losses 3 friendly planes, they also get 1 reinforcement 



Instructions
Note: planes are always moving forward, the user tilts the angle of plane to turn it
Player 1: ‘W’ key tilts the plane forward
          ‘S’ key tilts the plane downward
          '1' key shoots bullets
           

Player 2: ‘I’ numpad key tilts the plane forward     
          ‘K’ numpad key tilts the plane downward
           SpaceBar shoots bullets
           

Player 3: Up Arrow key tilts the plane forward
          Down Arrow key tilts the plane downward
	  '/' Key shoots bullets

Press 'P' to pause the game
          


Class List


Wingman: Extends FighterPlan and represents the AI(wingman) planes.  They recieve coordinates from FlightPlan and follow them

FlightPlan: This class decides its wingman's current route (whether it is chasing enemyplanes,locating a new enemyplane,or flying randomly)

FighterPlane: An abstract class that has all the basic abilities all types of fighterplanes can do

UserPlane: The class extends FighterPlane and has all the abilities that users can do to their plane

KeyInteraction: Manages the control fields of Userplanes when ever a specific key is pressed

Ammo: Reperesents the machine gun bullets which all fighterplanes can shoot

Menu: The frame of the program, here users can modify settings for their current game

CenterPanel: Contains the image of the plane in the Menu, makes all the TeamChooses objects and gets information from them

TeamChoose: This class represents each team present in the program, here users can decide which player(or no player)
             each team will be and how much wingman each team has

MenuTitle: The title of the Menu

GamePanel: Represents the actual game, makes and manages all the userplanes and AI planes. 

Team: Each country(team) has one of these objects.  Manages how many kills and losses each team has, and has
information on how to make a new FighterPlane of its country

                     

