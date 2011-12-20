import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Fire extends Actor
{
    protected static final int MAX_LIFETIME = 5;
    
    protected int life;
    
    public Fire() {
        this.life = MAX_LIFETIME;
    }
    
    public void act() 
    {
        Field field = (Field)getWorld();

        int x = getX();
        int y = getY();
        
        if (field.hasGrassAt(x+1,y))
        {
            Grass grass = (Grass)field.getObjectAt(x+1,y, Grass.class);
            float h = grass.getHumidity();
            if (Greenfoot.getRandomNumber(100) > (int)(100.0f*(h/ Grass.MAX_HUMIDITY))) field.turnGrassToFire(x+1,y);
        }
        if (field.hasGrassAt(x-1,y))
        {
            Grass grass = (Grass)field.getObjectAt(x-1,y, Grass.class);
            float h = grass.getHumidity();
            if (Greenfoot.getRandomNumber(100) > (int)(100.0f*(h/ Grass.MAX_HUMIDITY))) field.turnGrassToFire(x-1,y);
        }
        if (field.hasGrassAt(x,y+1))
        {
            Grass grass = (Grass)field.getObjectAt(x,y+1, Grass.class);
            float h = grass.getHumidity();
            if (Greenfoot.getRandomNumber(100) > (int)(100.0f*(h/ Grass.MAX_HUMIDITY))) field.turnGrassToFire(x,y+1);
        }
        if (field.hasGrassAt(x,y-1))
        {
            Grass grass = (Grass)field.getObjectAt(x,y-1, Grass.class);
            float h = grass.getHumidity();
            if (Greenfoot.getRandomNumber(100) > (int)(100.0f*(h/ Grass.MAX_HUMIDITY))) field.turnGrassToFire(x,y-1);
        }
            
        if (--life <= 0)
        {
            this.kill();
            return;
        }
    }
    
    public void kill()
    {
        getWorld().removeObject(this);
    }
}
