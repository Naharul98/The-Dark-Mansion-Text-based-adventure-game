import java.util.*;
import java.lang.*; 
/**
 *  This class is the main class of the "The Dark Mansion" application.
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *  This main class creates and initialises all the others: it creates all
 *  rooms and items. Also creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * @author  A K M NAHARUL HAYAT
 * @version 2017.11.25
 */
public class Game 
{
    private Parser parser; //this variable contains the parser object(see parser class)
    private Room currentRoom;//this variable holds the room in which the current player is in
    private Player currentPlayer;//the current player
    private HashSet<Items> interactableItems;//holds item objects of items the user the interact/examine
    private HashMap<String, Items> useableItems;//holds pair of items that can be used with each other
    private ArrayList<Room> listOfRooms;//contains list of rooms on which the player can teleport upon entrance in the teleporter room
    private Room endRoom;//contains the room, which if the can find a way into, he/she can win the game
    private Room teleporterRoom;//contains the room which, upon entrance, teleports the player to a random room in the list above
    
    /**
     * Create the game and initialise all of it's variables.
     * initializes the hashsets, hashmaps and arraylist
     * Creates the two major room, named as teleporter room and end room.In addition, also creates the other non-major rooms
     */
    public Game() 
    {
        currentPlayer = new Player();
        interactableItems = new HashSet<Items>();
        useableItems = new HashMap<String, Items>();
        listOfRooms = new ArrayList<>();
        teleporterRoom = new Room("you just entered the teleporter room, you are now magically transported to a random room");
        setUpRooms();
        endRoom = new Room("you enter the secret room and walk through a passage, at the end of the passage you see light, you walk through the passage and now you are outside, and free! Congratulations!");
        parser = new Parser();
    }
    /**
     * This method sets up the rooms for the gameplay.
     * It creates all the rooms and link their exits together.
     * Creates items to be put in their respective rooms
     */
    private void setUpRooms()
    {
        Room startingRoom, mainHall, bedRoom, bathRoom, artifactRoom ;
        // create the rooms
        startingRoom = new Room("\nYou see a dark doorway to the north");
        mainHall = new Room("you are in a vast dark hall\nthere are doors to the east and north with light coming through it");
        bedRoom = new Room("you are in a large bedroom");
        bathRoom = new Room("you are in small a bathroom with a deadbody on a bathtub");
        artifactRoom = new Room("you are in a large room, with a desk. It is glowing in the middle of the desk for some reason");
        // initialise room exits
        startingRoom.setExit("north", mainHall);
        mainHall.setExit("west", bedRoom);
        mainHall.setExit("south", startingRoom);
        mainHall.setExit("east", artifactRoom);
        mainHall.setExit("north", teleporterRoom);
        bedRoom.setExit("east", mainHall);
        bedRoom.setExit("north", bathRoom);
        bathRoom.setExit("south", bedRoom);
        artifactRoom.setExit("west",mainHall);
        //add room to listOfRooms arrayList
        //this list contains the rooms which the player can be teleported to, upon entrance to the teleporter room
        listOfRooms.add(startingRoom);
        listOfRooms.add(mainHall);
        listOfRooms.add(bedRoom);
        listOfRooms.add(bathRoom);
        listOfRooms.add(artifactRoom);
        //Create items objects to be put into the rooms
        Items safe = new Items("safe", "It is a medium sized safe, It is locked, It needs a key to open!",150,false);
        Items deadBody = new Items("deadbody", "A dead body is lying on the bathtub, there seems to be a key in the pocket of the dead man",120,false);
        Items key = new Items("key", "a key lying on the bathtub floor",10,true);
        Items stone = new Items("stone","a large black stone is lying on the floor, it looks heavy", 80, true);
        Items desk = new Items("desk", "a large desk lies in the room, In the middle of the desk, there is a place for a star shaped object to be fitted in",170,false);
        //add the created items to the interactable items hashset
        //which contains set of items the player can interact/examine
        interactableItems.add(desk);
        interactableItems.add(safe);
        interactableItems.add(key);
        interactableItems.add(stone);
        interactableItems.add(deadBody);
        //put pair useable items in the hashmap.
        //this hashmap contains pair of items the user can put together
        //for example - key can be used with the safe.
        useableItems.put("key", safe);
        useableItems.put("artifact", desk);
        //set up the item objects in their respective room for the gameplay
        mainHall.addItem(safe);
        bathRoom.addItem(deadBody);
        bathRoom.addItem(key);
        startingRoom.addItem(stone);
        artifactRoom.addItem(desk);
        // start game at the starting room
        currentRoom = startingRoom;
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.     
        boolean finished = false;
        while (! finished) 
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.");
    }
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("THE DARK MANSION");
        System.out.println("You wake up in a dark,unknown mansion, not knowing how you got there");
        System.out.println("You now have to find your way out of the mansion");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        //check unknown command
        if(command.isUnknown()) 
        {
            System.out.println("I don't know what you mean...");
            return false;
        }
        //process the commands according to the command word
        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("examine")) {
            examine(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("inventory")) {
            inventory(command);
        }
        else if (commandWord.equals("use")) {
            use(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You wake up in an unknown place, not knowing how you got there");
        System.out.println("find your way out!");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    /** 
     * implementations of the 'go' command:
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        if(command.hasThirdWord()) 
        {
            // if there is a third word in the command...
            System.out.println("please specify 1 single room to go to");
            return;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentPlayer.getStackOfRooms().push(currentRoom); //push the current room the player is in, in the stack
            currentRoom = nextRoom;
            //checks if the player has succesfully managed to enter the end game room
            //if so, then prints out that the player has won the game
            if(currentRoom == endRoom)
            {
                System.out.println("you have succesfully managed to escape and you win!");
                System.out.println("CONGRATULATIONS!");
            }
            //*checks if the player entered the teleporter room
            //*if the player has, then it teleports the player randomly in one of the rooms
            //*clears the stack, so the user cannot go back
            else if(currentRoom == teleporterRoom)
            {
                Random rand = new Random();
                Room teleportedRoom;
                teleportedRoom = listOfRooms.get(rand.nextInt(listOfRooms.size()));
                currentRoom = teleportedRoom;
                System.out.println(teleporterRoom.getShortDescription());
                System.out.println(currentRoom.getLongDescription());
                currentPlayer.getStackOfRooms().clear();//clears the stack, so the user cannot go back after being teleported
            }
            else
            {
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
     /** 
     * implementations of the 'take' command:
     * Tries to put a specific item in the player's inventory
     * Checks if the item is carrry able by the player
     * Checks if the use hasn't reached weight limit in back pack.
     * If all check conditions are satisfied, then removes the item from the room
     * and places the item in the player's inventory
     * otherwise, Prints error message accordingly
     */
    private void take(Command command) 
    {
        //if there is no second word in the command, then we dont know what to take
        if(!command.hasSecondWord()) 
        {
            System.out.println("take what?");
            return;
        }
        //if there is a third word in the command, then we dont know which single item to take
        if(command.hasThirdWord()) 
        {
            System.out.println("please specify one single item to take");
            return;
        }
        String item = command.getSecondWord();
        //checks if there is such an item in the command which is carry-able by the player
        if(pickableCheck(item) == null)
        {
            System.out.println("no such item to pick");
        }
        //if the item is not carry-able by the player
        else if (pickableCheck(item) == false)
        {
        System.out.println("you cannot carry this item with you");
        }
        else if(pickableCheck(item) == true)
        {
            //if inventory weight limit is reached, then the player is asked to drop some items in order to take it.
            if(itemEquipableCheck(item) == false)
            {
                System.out.println("you have reached maximum number of weight in your backpack. You have to drop some items to make room");
            }
            //checks if item is equipable in the player's inventory based on its current weight
            else if(itemEquipableCheck(item) == true)
            {
                if(checkItemInRoom(item) == true)
                {
                    Items itemToTake;
                    itemToTake = getItemFromName(item);
                    currentRoom.getItemsInRoom().remove(itemToTake);//removes the item in the room in order to add it to the inventory
                    currentPlayer.getItemsInInventory().add(item);//the removed item is added to the player's inventory
                    int weight = getItemWeight(item);
                    int weightCurrentlyCarrying = currentPlayer.getWeightCurrentlyCarrying();
                    weightCurrentlyCarrying = weightCurrentlyCarrying + weight;//updates the current inventory weight
                    currentPlayer.setWeightCurrentlyCarrying(weightCurrentlyCarrying);
                    System.out.println("you took the "  + item);
                }
                else
                {
                    System.out.println("no such item to pick in the room");
                }
            }
        }
    }
    /** 
     * implementation of the 'examine' command:
     * Tries to examine the details of a specific item in the room
     */
     private void examine(Command command) 
    {
        //if there is no second word in the command, then we dont know what to take
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("examine what?");
            return;
        }
        //if there is a third word in the command, player is told to input name of one item to examine 
        if(command.hasThirdWord()) 
        {
            System.out.println("please specify one single item to examine");
            return;
        }
        String item = command.getSecondWord();
        //checks if there is item in the room.
        //if there is, it prints out the details of that particular item
        for(Items items : interactableItems)
        {
            if(item.equals(items.getItemName()))
            {
                System.out.println(items.getItemDescription());
                return;
            }
        }
        //if there is no such item in the room, it prints out the following line
        System.out.println("no such thing in the room to examine");
    }
    /** 
     * implementation of the 'inventory' command:
     * prints out all the item currently held in the player's inventory
     * also prints out the current inventory weight, and the weight limit of the inventory
     */
    private void inventory(Command command) 
    {
        if(command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("did not recognize what to do with the inventory");
            System.out.println("help - type only 'inventory' to check inventory");
            return;
        }
        for(String item : currentPlayer.getItemsInInventory())
        {
            System.out.println(item + " ");
           
        }
         System.out.println("weight currently carrying = " + currentPlayer.getWeightCurrentlyCarrying());
         System.out.println("Maximum units of weight the backpack can hold = " + currentPlayer.getWEIGHTLIMIT());
    }
    /** 
     * implementation of the 'use' command:
     * implements the 'using one item with another' function in the game
     * Checks the 'useableItems' Hashmap to verify if the 2 pair of items specified in the command is useable with each other.
     * if they are, then implements the 'use' function for each pair of items.
     * if they are not useable with each other, it prints out a message accordingly
     */
    private void use(Command command) 
    {
        //if no second word, then we dont know what to use
        if(!command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("use which item with what?");
            System.out.println("type only 'inventory' to check inventory");
            return;
        }
        //if no third word, then we dont know what to use the specified item in the second word, with
        if(!command.hasThirdWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("use " + command.getSecondWord() + "with what?");
            System.out.println("Command format help - use 'item_name' 'object_name'");
            return;
        }
        String itemToUse = command.getSecondWord();
        String object = command.getThirdWord();
        //Checks if the item to use is in the inventory first of all
        if(!itemInventoryCheck(itemToUse))
        {
            System.out.println("you have no " + itemToUse + " In your inventory");
            return;
        }
        try{
        if(itemInventoryCheck(itemToUse))
        {
            //verify if the pair of items specified in the commands can be used with each other
            
            if(object.equals((useableItems.get(itemToUse).getItemName())) == true)
            {
                //implements the use function 
                if(itemToUse.equals("key") && currentRoom.getItems().contains((useableItems.get(itemToUse).getItemName())))
                {
                    
                    Items artifact = new Items("artifact","a star shaped artifact is found inside the safe",30,true);
                    interactableItems.add(artifact);
                    currentRoom.addItem(artifact);
                    System.out.println("you have opened the safe, inside there is a star shaped artifact");
                    System.out.println(currentRoom.getLongDescription());

                }
                //implements the use function
                else if(itemToUse.equals("artifact") && currentRoom.getItems().contains((useableItems.get(itemToUse).getItemName())))
                {
                    currentRoom.setExit("north", endRoom);
                    System.out.println("you have put the artifact in the star shaped location in the desk\n a secret door opens on the north of the room\n inside the door you see a passage");
                    System.out.println(currentRoom.getLongDescription());
                }
                else
                {
                     System.out.println("you can't use" + itemToUse + " here");
                }
            }
            else
            {
               System.out.println("you can't use this item here");
            }
        }
        }
        
         catch(Exception e)
        {
            System.out.println("you can't use this item here");
        }
        
    }
    /**
     * implementation of the 'back' command:
     * takes the player back to the previous room he has been in
     * note-if the player uses the teleporter room, he can't go back 
     * Immediately after teleportation, as he was teleported by magic
     */
    private void back(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("back what?");
            System.out.println("help - type only back to go the previous room");
            return;
        }
        //checks if stack is empty
        if(currentPlayer.getStackOfRooms().empty() == true)
        {
            System.out.println("there is no place to go back");
        }
        else
        {
            Room backRoom = currentPlayer.getStackOfRooms().pop();
            currentRoom = backRoom ;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    /** 
     * implementation of the 'drop' command:
     * implements dropping a specified item from the inventory in the current room of the player
     */
    private void drop(Command command) 
    {
        //if there is no second word, we don't know which item to drop
        if(!command.hasSecondWord()) 
        {
            System.out.println("drop what?");
            return;
        }
        //if there is a third word, the player is asked to specify one single item to drop
        if(command.hasThirdWord()) 
        {
            System.out.println("please specify one single item to drop");
            return;
        }
        String item = command.getSecondWord();
        //checks whether the item to drop, is in the players inventory
        if(itemInventoryCheck(item) == true)
        {
            Items itemToDrop;
            for(Items x : interactableItems)
            {
                if(x.getItemName().equals(item))
                {
                    itemToDrop = x;
                    int weight = itemToDrop.getWeight();
                    //decrements the player inventory weight, by the weight of the item which is dropped
                    int weightCurrentlyCarrying = currentPlayer.getWeightCurrentlyCarrying();
                    weightCurrentlyCarrying = weightCurrentlyCarrying - weight;
                    currentPlayer.setWeightCurrentlyCarrying(weightCurrentlyCarrying);
                    //the dropped item is now in put the current room the player is in
                    currentRoom.addItem(itemToDrop);
                    //the dropped item is removed from the player inventory
                    currentPlayer.getItemsInInventory().remove(itemToDrop.getItemName());
                    System.out.println("you dropped " + itemToDrop.getItemName() + " in the room");
                }
            }
        }
        //if the item to drop is not in the player's inventory, then prints out a message accordingly
        else
        {
            System.out.println("no item with name =  " + item + " found in backpack to drop");
        }
    }

    /**
     * #helper method#
     * Checks if the specified item is in the player's inventory of not, returns true/false according to it
     * @param the name of the item to check if it is currently in the inventory of the player or not
     * @return true if the item is in the player's inventory
     * @return false if not in the player's inventory
     */
    private boolean itemInventoryCheck(String itemName)
    {
        for(String x : currentPlayer.getItemsInInventory())
        {
            if(x.equals(itemName))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * #helper method#
     * returns the weight of the item name specified in the parameter
     * @param the name of the item of which the weight is to be returned
     * @return the weight of the item according to the item name in the 
     * @return returns 0, if the specified item in the parameter is not found
     */
    private int getItemWeight(String itemName)
    {
        for(Items x : interactableItems)
        {
            if(x.getItemName().equals(itemName))
            {
                return x.getWeight();
            }
        }
        return 0;
    }
    /**
     * #helper method#
     * Checks if a given item is carry able by the player
     * @param the name of the item to check if it is carry able by the player
     * @return true if the item is carryable, returns false if not, returns null if the item in the parameter is not found
     */
    private Boolean pickableCheck(String itemName)
    {
        for(Items x: interactableItems)
        {
            if(x.getItemName().equals(itemName))
            {
                if(x.getPickable() == true)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        } 
        return null;
    }
    /**
     * #helper method#
     * returns item object corresponding to the name of the item provided in the parameter
     * @param the name of the item
     * @return item object corresponding to the name
     * @return returns null if the item name provided does not correspond to any item object
     */
    private Items getItemFromName(String itemName)
    {
        for(Items x : interactableItems)
        {
            if(x.getItemName().equals(itemName))
            {
                return x;
            }
        }
        return null;
    }
    /**
     * #helper method#
     * checks if the item name provided in the parameter, corresponds to any items in the room
     * @param the name of the item
     * @return true if there is an item in the current room with the name provided in the parameter
     * @return returns false if the item name provided does not correspond to any item object in the room
     */
    private boolean checkItemInRoom(String itemName)
    {
        HashSet<Items> currentItemsInRoom = new HashSet<Items>();
        
        for(Items item : currentRoom.getItemsInRoom())
        {
            if(item.getItemName().equals(itemName))
            {
                return true;
            }
        }
        return false;
    }
    /**
     * #helper method#
     * Checks if a item is equipable in the inventory by the player based on current inventory weight and the item weight
     * @param the name of the item to check if it is equipable by the player in the inventory based on its weight
     * @return true if the item is equipable in the inventory
     * @return false if not equipable, returns null if the item in the parameter is not found
     */
    private Boolean itemEquipableCheck(String item)
    {
        for(Items x: interactableItems)
        {
            if(x.getItemName().equals(item))
            {
                if((currentPlayer.getWeightCurrentlyCarrying() + x.getWeight()) <= currentPlayer.getWEIGHTLIMIT())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        } 
        return null;
    }
    
}
