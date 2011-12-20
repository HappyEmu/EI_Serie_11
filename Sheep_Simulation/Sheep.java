import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * This class implements sheep objects.
 * 
 * A sheep constantly moves in the field and eats grass that comes in its way.
 * 
 * You should implement additional behavior of the sheep:
 * - The sheep needs to store the amount of food it has in its stomach.
 * - If its stomach is full to a certain level it multiplies.
 * - If its stomach becomes empty, it dies.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Sheep extends Actor
{
    private final static float NEWBORN_FOOD = 25.0f;
    private final static float MATERNITY_FOOD = 50.0f;
    private final static float DYING_THRESHOLD = 0.0f;
    private final static float BIRTH_THRESHOLD = 75.0f;
    private final static float FOOD_DEPLETION = 0.1f;
    private final static float FOOD_INCREMENT = 1.0f;

    // Direction in which the sheep currently moves
    protected Direction dir;
    private float full;
    
    /**
     * This class represents the direction of motion for a sheep.
     **/
    public static class Direction 
    { 
        int upSteps;    
        int rightSteps;
        

        public Direction(int aUp, int aRight)
        {
            this.upSteps = aUp;
            this.rightSteps = aRight;
        }
    };

    /**
     * Default Constructor.
     * 
     * You need to extend this to initialize the amount of food in the 
     * sheep's stomach when it is created.
     */
    public Sheep ()
    {
        // By default set direction to 0-0, i.e., no motion.
        dir = new Direction(0,0);

        // Initially the sheep should have some fixed amount of food in its stomach.
        this.full = NEWBORN_FOOD;
    }

    /**
     * Act - do whatever the Sheep wants to do. This method is called in each step of 
     * the simulation after the simulation has been started by clicking the 'Run' button 
     * in the Greenfoot window.
     * 
     * You need to extend this method to add the additional sheep behavior, that is,
     * eating grass, multiplying, or dying.
     */
    public void act()
    {
        // Get a reference to the field that the sheep is living on.
        Field theField = getWorld();

        // Move the sheep to its new position.
        int oldX = getX();
        int oldY = getY();
        int newX = oldX + dir.rightSteps;
        int newY = oldY + dir.upSteps;
        
        this.full -= FOOD_DEPLETION;
             
        if (this.full >= BIRTH_THRESHOLD)
        {
            Sheep s = new Sheep();
            Random rand = new Random();
            s.setDirection(rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean(),rand.nextBoolean());
            getWorld().addObject(s, getX(), getY());
            
            this.full = MATERNITY_FOOD;
        }
       
        // Check if the new location is empty or not.
        if(theField.isEmpty(newX, newY) || theField.hasRainAt(newX,newY))
        {
            // New location is empty. Move there.
            setLocation(newX, newY);
        } else 
        {
            // New location is not empty.
            // Check if there is a solid object at the new location. 
            // (Sheep and bricks are solid objects, grass is not a solid object in this exercise.)
            if(theField.hasSolidAt(newX, newY))
            {
                // New location contains a solid object already.
                
                // Change the direction of motion for sheep as if its reflecting from the solid at the new location.               
                bounceFromSolid(theField, oldX, oldY, newX, newY);

            } else if(theField.hasGrassAt(newX, newY))
            {
                // New location does not contain a solid object.
                // Instead, it contains grass.
                
                // Move to this location.                                
                setLocation(newX, newY);
                // Eat the grass here.
                theField.eatGrassAt(newX, newY);
                
                this.full += FOOD_INCREMENT;
            }
            else if (theField.hasFireAt(newX, newY)||theField.hasFireAt(oldX,oldY))
            {
                Fire f1 = new PermaFire();
                Fire f2 = new PermaFire();
                Fire f3 = new PermaFire();
                Fire f4 = new PermaFire();
                
                int x = getX();
                int y = getY();
                
                getWorld().addObject(f1,x+1,y);
                getWorld().addObject(f2,x-1,y);
                getWorld().addObject(f3,x,y+1);
                getWorld().addObject(f4,x,y-1);
                
                FireSheep fireSheep = new FireSheep(f1,f2,f3,f4);              
                Random rand = new Random();
                fireSheep.setDirection(-this.dir.rightSteps,-this.dir.upSteps);
                getWorld().addObject(fireSheep,oldX,oldY);
                
                getWorld().removeObject(this);
                return;
            }
        }
        
        if (this.full <= DYING_THRESHOLD)
        {
            getWorld().removeObject(this);
        }
    }
    
    /**
     * This function changes the direction of motion of the sheep as if it were reflecting back
     * from a solid object in its path. It is called when the sheep collides with a solid object.
     * You don't need to change this.
     * */
    protected void bounceFromSolid(Field theField, int oldX, int oldY, int newX, int newY)
    {
            
        if (dir.rightSteps == 0)
        {  
            // Sheep is moving only in the vertical direction
            // Flip its vertical direction of motion
            dir.upSteps *= -1;
        } else if (dir.upSteps ==0)
        {
            // Sheep is moving only in the horizontal direction
            // Flip its horzontal direction of motion
            dir.rightSteps *= -1;
        } else
        {
            // Sheep is moving along both the axes.
            
            
            // If there is no solid neighbour along the vertical direction of motion
            // The apparent wall is vertical
            // flip the horizontal direction of motion
            if (!theField.hasSolidAt(oldX, newY))
            {
                dir.rightSteps *= -1;
            }

            // If there is no solid neighbour along the horinzontal direction of motion 
            // The apparent wall is horizontal
            // flip the vertical direction of motion
            if (!theField.hasSolidAt(newX, oldY))
            {
                dir.upSteps *= -1;
            }

            // If there are solid neighbours along both the axes in the direction of motion
            // Flip both horizontal and vertical direction of motion
            if (theField.hasSolidAt(oldX, newY) && theField.hasSolidAt(newX, oldY))
            {
                dir.rightSteps *= -1;
                dir.upSteps *= -1;
            }
        }
    }
    
    /**
     * This function typecasts the returned world object to Field class. You don't need to change this.
     **/
    public Field getWorld()
    {
        return (Field)super.getWorld();
    }
    
    /**
     * This function sets the motion direction for the sheep in terms of step sizes. This is called by the 
     * "field" object to initialize the sheep. You don't need to change this.
     * 
     * Inputs :
     * moveVertical   : boolean flag indicating if the sheep should move in vertical direction
     * moveHorizontal : boolean flag indicating if the sheep should move in horizontal direction
     * toTop          : boolean flag indicating if the sheep should move upwards or downwards in the field.
     *                  This flag is used only if 'moveVertical' is set to true.
     * toLeft         : boolean flag indicating if the sheep should move towards left or right in the field.
     *                  This flag is used only if 'moveHorizontal' is set to true.
     */
    public void setDirection(boolean moveVertical, boolean moveHorizontal, boolean toTop, boolean toLeft)
    {
        // Step size is a single unit in a given direction.
        if (moveVertical) 
        {
            if (toTop)
            {
                dir.upSteps = -1;
            } else
            {
                dir.upSteps =  1;
            }
        }
        
        if (moveHorizontal) 
        {
            if (toLeft)
            {
                dir.rightSteps = -1;
            } else
            {
                dir.rightSteps =  1;
            }
        }
        
    }
    
    public void setDirection(int x, int y)
    {
        this.dir.rightSteps = x;
        this.dir.upSteps = y;
    }
}
