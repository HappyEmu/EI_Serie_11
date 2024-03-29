import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents objects for grass patches.
 * A grass patch object is non-solid so solid objects can occupt its location.
 * Grass patches grow and expand in their neighborhood, and they die at a certain
 * age, that is, after a certain number of simulation steps.
 * 
 * You do not need to change this class.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Grass extends Actor
{
    
    // Wait time before grass would take some action.
    protected int waitTime;
    
    protected float humidity;
    
    protected float nutrition;
    
    protected int age;

    // Age after which a grass patch may grow.
    protected static final int GROWTH_AGE = 10;
    
    // Age after which a grss patch dies.
    protected static final int MAX_AGE = 20;
   
    protected static final float MAX_HUMIDITY = 100.0f;
    protected static final float MIN_HUMIDITY = 30.0f;
    protected static final float HUMIDITY_DEPLETION_FACTOR = 0.5f;
    protected static final float HUMIDITY_POUR_FACTOR = 10.0f;
    
    protected static final float MAX_NUTRITION = 1.0f;

    /**
     * Default Constructor.
     **/
    public Grass()
    {
        // set age to zero and wait time to 10.
        age = 0;
        waitTime = 10;
        humidity = MAX_HUMIDITY;
        nutrition= MAX_NUTRITION*((float)humidity)/MAX_HUMIDITY;
    }
    
    public Grass(float hum)
    {
        age = 0;
        waitTime = 10;
        humidity = hum;
        nutrition= MAX_NUTRITION*((float)humidity)/MAX_HUMIDITY;
    }
    
    /**
     * Act - do whatever the Grass wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        Field theWorld = (Field)getWorld();
        
         humidity -= HUMIDITY_DEPLETION_FACTOR;
         nutrition = MAX_NUTRITION*((float)humidity)/MAX_HUMIDITY;
        
        if (humidity <= MIN_HUMIDITY)
        {   
            theWorld.seedDryGrass(getX(), getY());
            this.kill();
            return;
        }
        // Check if it is time for action
        if (--waitTime <= 0)
        {
            
            // set new wait time to a random value between 5 to 9
            waitTime = 5 + Greenfoot.getRandomNumber(5);
            
           

            // Check if this grass patch has reached an age to grow.
            // If it has reached the age to grow, with a chance 1 in four grow it.
            if (++age >= GROWTH_AGE && 0 == (Greenfoot.getRandomNumber(4)))
            {// random chance 1 in 4

                // Check if the left neighbouring location is empty.
                if (null == getOneObjectAtOffset(-1, 0, null) )
                {
                    // It is empty.
                    // Add a new grass patch to it.
                    Grass g = new Grass();
                    theWorld.addObject(g, getX()-1, getY());
                }
                
                // Check if the top neighbouring location is empty.
                if (null == getOneObjectAtOffset(0, -1, null))
                {
                    // It is empty.
                    // Add a new grass patch to it.
                    Grass g = new Grass();
                    theWorld.addObject(g, getX(), getY()-1);
                }

                // Check if the bottom neighbouring location is empty.
                if (null == getOneObjectAtOffset(0, 1, null))
                {
                    // It is empty.
                    // Add a new grass patch to it.
                    Grass g = new Grass();
                    theWorld.addObject(g, getX(), getY()+1);
                }

                // Check if the right neighbouring location is empty.
                if (null == getOneObjectAtOffset( 1, 0, null))
                {
                    // It is empty.
                    // Add a new grass patch to it.
                    Grass g = new Grass();
                    theWorld.addObject(g, getX()+1, getY());
                }
            }
        } 
    }
    
    public void pour()
    {
        this.humidity += HUMIDITY_POUR_FACTOR;
    }
    
    public void dry()
    {
        this.humidity -= HUMIDITY_POUR_FACTOR; 
    }
    
    public void kill()
    {
        getWorld().removeObject(this);
    }
    
    public float getNutrition()
    {
        return nutrition;
    }
}
