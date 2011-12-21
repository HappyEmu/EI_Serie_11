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
    private final static float NEWBORN_FOOD = 50.0f;
    private final static float MATERNITY_FOOD = 25.0f;
    private final static float DYING_THRESHOLD = 0.0f;
    private final static float BIRTH_THRESHOLD = 100.0f;
    private final static float FOOD_DEPLETION = 0.1f;
    
    
    private final static int MAX_IQ = 8;//15;
    private final static int MIN_IQ = 2;//5;
    protected final static int VISIBILITY_RANGE = 6;

    // Direction in which the sheep currently moves
    protected Direction dir;
    private float full;
    private int searchForTarget;
    
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
        this.searchForTarget = 0;
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
        Field theField = getWorld();
        
        int oldX = getX();
        int oldY = getY();
        
        if (--searchForTarget <= 0)
        {
            java.util.Random rand = new Random();
            searchForTarget = randomBetween(rand, MIN_IQ, MAX_IQ);
            java.util.List grass = getObjectsInRange(VISIBILITY_RANGE, Grass.class);
        
            if (!grass.isEmpty())
            {
                double minAbs = 100000.0;
                Grass nG = null;
           
                for (Object a : grass)
                {
                    int gX = ((Actor)a).getX();
                    int gY = ((Actor)a).getY();
                
                    int dx = gX - oldX;
                    int dy = gY - oldY;
                    double abs = Math.sqrt(dx*dx+gY*gY);
                    if (abs <= minAbs)
                    {
                        minAbs = abs;
                        nG = (Grass)a;
                    }
                }
            
                int dx = nG.getX() - oldX;
                int dy = nG.getY() - oldY;
            
                if (!(dx == 0 && dy == 0))
                {
                    if (dx > 0) this.dir.rightSteps = 1;
                    else if (dx == 0) this.dir.rightSteps = 0;
                    else if (dx < 0) this.dir.rightSteps = -1;
                    
                    if (dy > 0) this.dir.upSteps = 1;
                    else if (dy == 0) this.dir.upSteps = 0;
                    else if (dy < 0) this.dir.upSteps = -1;
                }            
            }
        }
        
        
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
       
        if(theField.isEmpty(newX, newY) || theField.hasRainAt(newX,newY))
        {
            setLocation(newX, newY);
        } else 
        {
   
            if(theField.hasSolidAt(newX, newY))
            {
     
                bounceFromSolid(theField, oldX, oldY, newX, newY);

            } else if(theField.hasGrassAt(newX, newY))
            {
                Grass toEat = (Grass)theField.getObjectAt(newX, newY, Grass.class);
                this.full += toEat.getNutrition();                            
                setLocation(newX, newY);

                theField.eatGrassAt(newX, newY);
            }
            else if (theField.hasFireAt(newX, newY) || theField.hasFireAt(oldX,oldY))
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
    
    private int randomBetween(Random r, int min, int max)
    {
        return min + (int)(r.nextFloat() * ((max - min) + 1));
    }
}
