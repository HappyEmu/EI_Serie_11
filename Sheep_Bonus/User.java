import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class User extends Actor
{
    public void act() 
    {
        Field field = (Field)getWorld();
        
        if(Greenfoot.mousePressed(null))
        {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse.getButton() == 1)
            {
                int x = mouse.getX();
                int y = mouse.getY();
               
                if (field.hasGrassAt(x,y))
                    field.turnGrassToFire(x,y);
            }
            if (mouse.getButton() == 3)
            {
                int x = mouse.getX();
                int y = mouse.getY();
               
                if (field.isEmpty(x,y))
                    field.seedGrass(x,y);
            }
       }
    }    
}
