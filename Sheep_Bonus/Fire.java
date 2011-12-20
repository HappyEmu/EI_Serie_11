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
            field.turnGrassToFire(x+1,y);
        if (field.hasGrassAt(x-1,y))
            field.turnGrassToFire(x-1,y);
        if (field.hasGrassAt(x,y+1))
            field.turnGrassToFire(x,y+1);
        if (field.hasGrassAt(x,y-1))
            field.turnGrassToFire(x,y-1);
            
        if (--life <= 0)
        {
            this.die();
            return;
        }
    }
    
    public void die()
    {
        getWorld().removeObject(this);
    }
}
