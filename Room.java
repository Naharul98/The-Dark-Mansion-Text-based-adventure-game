import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "The Dark Mansion" application.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  A K M NAHARUL HAYAT
 * @version 2017.11.25
 */
public class Room 
{
    private String description;         //the description of the room
    private HashMap<String, Room> exits;// stores exits of this room.
    private HashSet<Items> itemsInRoom;//this hashset stores items in the rooms.
    
    /**
     * Create a room  object, every room has a unique description
     * Initially, it has no exits. 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        itemsInRoom = new HashSet<>();
    }
    /**
     * Define an item for this room.
     * @param itemName -  the item which is to be assigned to a room
     * @param room  The room to which the item will be put into.
     */
    public void addItem(Items itemName)
    {
        itemsInRoom.add(itemName);
    }
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    /**
     * @return the hashset which contains the items in the room
     * (the one that was defined in the constructor).
     */
    public HashSet<Items> getItemsInRoom()
    {
        return itemsInRoom;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + ".\n" + getItems();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    /**
     * Return a string listing the items in the current room, for example
     * "items: desk, key".
     * @return Items in the current room
     */
    public String getItems()
    {
        String returnString = "Items in the room: ";
        
        for(Items item : itemsInRoom) {
            returnString += " , " + item.getItemName();
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

