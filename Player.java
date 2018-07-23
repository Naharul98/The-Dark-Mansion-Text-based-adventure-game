import java.util.*;
/**
 * This is the player class
 * this class defines the aspects related to the player of the game
 * this class implements the dynamic inventory system of the player, using a hashSet
 * it also implements a data structure called stack, which keeps track of the rooms the player has been in,
 * in order to implement the 'back' command
 * this class also defines the maximum weight limit the player can carry
 *
 * @author  A K M NAHARUL HAYAT
 * @version 2017.11.25
 */

public class Player
{
    private HashSet<String> itemsInInventory;//holds name of items currently held in inventory
    private int WEIGHTLIMIT = 100;//maximum unit of item weight the player can carry
    private int weightCurrentlyCarrying;//total weight of items currently held by the player's inventory 
    private Stack<Room> stackOfRooms;//holds all room the player has visited,(to implement the back function)

    /**
     * Initializes the inventory HashSet, the stack and also sets weight of inventory to 0
     */
    public Player()
    {
        itemsInInventory = new HashSet<String>();
        weightCurrentlyCarrying = 0;
        stackOfRooms = new Stack<Room>();
    }
    /**
     * @return The inventory hashset
     * (the one that was defined in the constructor).
     */
    public HashSet<String> getItemsInInventory()
    {
        return itemsInInventory;
    }
    /**
     * @return the maximum weight limit of the inventory
     * (the one that was defined in the constructor).
     */
    public int getWEIGHTLIMIT()
    {
        return WEIGHTLIMIT;
    }
    /**
     * @return the current weight of the player's inventory
     * (the one that was defined in the constructor).
     */
    public int getWeightCurrentlyCarrying()
    {
        return weightCurrentlyCarrying;
    }
    /**
     * this method sets the current inventory weight of the player by the amount specified in the constructor
     * @param the new weight of the inventory
     */
    public void setWeightCurrentlyCarrying(int weight)
    {
        weightCurrentlyCarrying = weight;
    }
     /**
     * @return the stack of the rooms the player has visited
     * (the one that was defined in the constructor).
     */
    public Stack<Room> getStackOfRooms()
    {
        return stackOfRooms;
    }  
}
