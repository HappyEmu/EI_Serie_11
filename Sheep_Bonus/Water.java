import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Water extends Actor
{
    private static final float MAX_HUMIDITY = 150.0f;
    private static final float EVAPORATION_RATE = 2.0f;
    private static final float SATURATION_RATE = 20.0f;
    
    private float humidity;
    
    public Water()
    {
        this.humidity = MAX_HUMIDITY;
    }
    
    public void act() 
    {
        if ((humidity -= EVAPORATION_RATE) <= 0) this.kill();
    }
    
    public void kill()
    {
        Field field = (Field)getWorld();
        field.seedGrass(this.getX(),this.getY());
        getWorld().removeObject(this);
    }
    
    public float getHumidity()
    {
        return humidity;
    }
    
    public void saturate()
    {
        humidity += SATURATION_RATE;
    }
}
