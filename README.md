# The-Dark-Mansion-Text-based-adventure-game

## Game Description:

The Dark Mansion, is a text based adventure game, that uses text inputs from the player as ‘commands’ in order to simulate a puzzle solving, interactive fiction. The player assumes the role of a protagonist who wakes up in a dark abandoned mansion, trapped and not knowing how he got there. He must now explore and find a way to escape the mansion.

## To Run the Game:
Create an object of the 'Game' class, then call the 'play()' method within the class

## Gameplay:
The game features 6 different rooms, including one magical room, which upon entering, the player gets teleported to a random room in the game! 

Each room has different number of items that the player can examine to find out more about its detail. Some items can be carried by the player, whereas others cannot be carried! Each item has their own respective weight units. The player has a maximum unit of weight (100 units) that he can carry in his backpack. The player can also check his inventory contents at any time during the game, in addition, he can also drop item from his inventory in any room he wants, and he can also pick it up again from the room in which he dropped the item in the first place.

If the player manages to escape the mansion, then he wins, however, he has to first figure out how to unlock the secret exit way located in one of the room. In order to do so, he has to explore different items, and possibly put them together (for example, use key with safe) in order to achieve the goal.

The game features 9 major intuitive commands, that can be used by the player to interact with the game/game environment, in order to explore, solve puzzles and finally escape the mansion!

## Game Command Instructions:
| Command  | Usage Example | Command Description |
| ------- | ------------- | ----------------------- |
| go  | go <Direction>  | Use this command navigate through different rooms |
| take  | take <item name>  | Use this command to take an item from the room into the backpack  |
| examine  | examine <item name>  | Use this command to examine a particular item in a room |
| drop  | drop <item name>  | Use this command to drop a particular itemthe player is currently carrying in the backpack  |
| inventory  | inventory  | Use this command to check the contents of the player’s backpack and also to know the current total weight its carrying |
| use  | Use <item in inventory> <object to use the item with>  | Use this command to use one item in the inventory with another item in the room. For example – ‘use key safe’ to unlock the safe using the key  |
| back  | back  | Use this command to take the player to the last room he has been in (can be used repeatedly to go backwards through all the rooms the player has been in, right to the beginning of the game) |
| quit  | quit  | Use this command to quit the game  |
| help  | help  | Use this command to display all the commandwords and their instructions |

