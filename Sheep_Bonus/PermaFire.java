import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class PermaFire extends Fire
{
    private static final int MAX_LIFETIME = 500;
    
    public PermaFire()
    {
        super();
        this.life = MAX_LIFETIME;
    }

    public void act() 
    {
        super.act();
    }    
}
