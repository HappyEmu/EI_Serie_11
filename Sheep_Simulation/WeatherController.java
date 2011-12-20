import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

public class WeatherController extends Actor
{
    private static final int RAIN_AMOUNT = 2;
    private static final int RAIN_DURATION = 300;
    private static final int MIN_SIZE = 8;
    private static final int MAX_SIZE = 16;
    
    private static final float RAIN_PROBABILITY = 0.15f;
    private static final float LIGHTNING_PROBABILITY = 0.01f;
    
    private boolean raining;
    private int xStart;
    private int yStart;
    private int duration;
    private int size;
    
    public WeatherController()    
    {
        this.duration = 0;
        this.xStart = 0;
        this.yStart = 0;
        this.size = 0;
        this.raining = false;
    }
    
    public void act() 
    {
        
        Random rand = new Random();
        Field world = (Field)getWorld();
        
        if (--this.duration <= 0)
        {
            size = randomBetween(rand, MIN_SIZE, MAX_SIZE);
            xStart = randomBetween(rand, 2, Field.FLD_WID-size-2);
            yStart = randomBetween(rand, 4, Field.FLD_HGH-size-2);           
            
            raining = (rand.nextInt(100) < (int)(RAIN_PROBABILITY*100.0f)) ? true : false; 
            this.duration = RAIN_DURATION;
        }
        
        if (!raining) return;
        
        for (int i = 0; i < RAIN_AMOUNT; i++)
        {
            int x = randomBetween(rand, xStart, xStart+size-1);
            int y = randomBetween(rand, yStart, yStart+size-1);
            
            if (world.hasWaterAt(x,y))
                continue;
            if (world.hasRainAt(x,y))
                world.turnRainToWater(x,y);
            else
                world.spawnRainAt(x,y);
        }
        
        if (rand.nextInt(100) < (int)(LIGHTNING_PROBABILITY*100.0f))
        {
            int x = randomBetween(rand, 1, Field.FLD_WID-size-1);
            int y = randomBetween(rand, 3, Field.FLD_HGH-size-1);
            
            world.spawnLightningAt(x,y);
        }
    }
    
    private int randomBetween(Random r, int min, int max)
    {
        return min + (int)(r.nextFloat() * ((max - min) + 1));
    }
}
