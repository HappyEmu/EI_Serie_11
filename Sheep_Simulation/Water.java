import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Water extends Actor
{
    private static final int MAX_LIFETIME = 150;
    
    private int life;
    
    public Water()
    {
        this.life = MAX_LIFETIME;
    }
    
    public void act() 
    {
        if (--life <= 0) this.kill();
    }
    
    public void kill()
    {
        Field field = (Field)getWorld();
        field.seedGrass(this.getX(),this.getY());
        getWorld().removeObject(this);
    }
}
