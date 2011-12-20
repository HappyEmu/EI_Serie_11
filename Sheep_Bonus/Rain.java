import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Rain extends Actor
{
    private static final int MAX_LIFETIME = 7;
    
    private int life;
    
    public Rain()
    {
        this.life = MAX_LIFETIME;
    }
    
    public void act() 
    {
        int x = getX();
        int y = getY();
        
        Field world = (Field)getWorld();
        world.clearCell(x, y, Fire.class);
        
        if (world.hasGrassAt(x,y)) 
            world.pourGrass(x, y);        
        if (world.hasWaterAt(x,y))
            world.resaturateWater(x, y);        
        
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
