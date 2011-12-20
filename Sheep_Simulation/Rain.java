import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Rain extends Actor
{
    private static final int MAX_LIFETIME = 5;
    
    private int life;
    
    public Rain()
    {
        this.life = MAX_LIFETIME;
    }
    
    public void act() 
    {
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
