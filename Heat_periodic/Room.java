import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents a room whose temperature is modified as per
 * user-interaction or heat diffusion.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Room extends World
{

    /**
     * Constructor for objects of class Room.
     * 
     */
    public Room()
    {    
        // Create a new world with 800x800 cells with a cell size of 1x1 pixels.
        super(800, 800, 1); 

        prepare();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        Heater heater = new Heater();
        addObject(heater, 161, 194);
    }
}
