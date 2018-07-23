import java.util.*;
/**
 * Class Items - items in the game
 *
 *This class is part of the "The Dark Mansion" application. 
 *
 * A "Item" represents one item located in a specific room.
 * an item can be put in the player's inventory.
 * It can also be dropped in any room by the player from its inventory
 * Each item has a weight, some items can be carried by the players, others cant.
 * Items can also be examined by the player, to view it's details
 * Some pair of items can be used with each other by the player, throughout the game
 * 
 * @author  A K M NAHARUL HAYAT
 * @version 2017.11.25
 */
public class Items
{
    private String name;        // name of the item
    private String description; //description of the item
    private int weight;         //weight of the item
    private boolean pickable;   //this boolean variable contains if the item can be picked by the player or not
    
    /**
     * Create an 'Items' objects described by name and description. 
     * Initially, it is not assigned to any room
     * example of item - "key".
     * @param name - name of the item
     * @param description - description of the item
     * @param weight - weight of the item
     * @param pickable - if the item is carry-able by the player or not
     */
    public Items(String name, String description, int weight,boolean pickable)
    {
        // initialise instance variables
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.pickable = pickable;
       
    }
    /**
     * @return The name of the item
     * (the one that was defined in the constructor).
     */
    public String getItemName()
    {
        return name;
    }
    /**
     * @return The description of the item
     * (the one that was defined in the constructor).
     */
    public String getItemDescription()
    {
        return description;
    }
    /**
     * @return The weight of the item
     * (the weight that was defined in the constructor).
     */
    public int getWeight()
    {
        return weight;
    }
    /**
     * @return returns true if the item is pick-able by the player, otherwise, returns false
     * (the one that was defined in the constructor).
     */
    public boolean getPickable()
    {
        return pickable;
    }
    
}
