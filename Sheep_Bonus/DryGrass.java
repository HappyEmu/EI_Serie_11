import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class DryGrass extends Grass
{

    private static final float MAX_HUMIDITY = 50.0f;
    private static final float MIN_HUMIDITY = 0.0f;
    private static final float HUMIDITY_DEPLETION_FACTOR = 0.1f;
    private static final float HUMIDITY_POUR_FACTOR = 8.0f;
    
    private static final float MAX_NUTRITION = 0.25f;
    
    public DryGrass()
    {
        humidity = MAX_HUMIDITY;
        nutrition= MAX_NUTRITION*((float)humidity)/MAX_HUMIDITY;
    }
    
    public void act()
    {
        Field world = (Field)getWorld();
        humidity -= HUMIDITY_DEPLETION_FACTOR;
        nutrition = MAX_NUTRITION*((float)humidity)/MAX_HUMIDITY;
              
        if (humidity <= MIN_HUMIDITY)
            this.kill();
        if (humidity > MAX_HUMIDITY)
        {
            world.seedGrass(getX(),getY(), 50.0f);
            this.kill();
        }
    }
}
