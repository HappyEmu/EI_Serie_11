import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class represents a field of grass populated by a flock of sheep. 
 * You do not need to change this.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Field extends World
{

    // Width of the sheep-field in cell-uints.
    public static final int FLD_WID = 50;

    // Height of the sheep-field in cell-uints.
    public static final int FLD_HGH = 38;

    public Counter counter;

 
    /**
     * Constructor.
     */
    public Field()
    {    
        // Create a new world with given width and height in cell-units. Set size of a cell size to 16x16 pixels.
        super(FLD_WID, FLD_HGH, 16); 

        // Add bricks to top 3 rows in the field.
        for (int i=0;i < 3 ;++i) {
            for (int j= 0; j < FLD_WID; ++j) {
                Brick bTemp = new Brick();
                addObject(bTemp, j,i);
            }
        }

        // Add bricks to the leftmost and rightmost columns of the field.
        for (int j=3; j < FLD_HGH; ++j) {
            Brick bTemp = new Brick();
            addObject(bTemp, 0, j);

            bTemp = new Brick();
            addObject(bTemp, FLD_WID-1,j);

        }
        
        // Add bricks to last row in the field.
        for (int j= 0; j < FLD_WID; ++j) {
            Brick bTemp = new Brick();
            addObject(bTemp, j,FLD_HGH-1);
        }
        
        // Make counter
        counter = new Counter();
        // Perepare field as intialized using the visual interface for Greenfoot.
        prepare();
    }

     /**
     * This function checks if a given location in the field is empty or occupied.
     * Input :
     *  x - x-index in cell units for given location.
     *  y - y-index in cell units for given location.
     *  
     *  Output : A boolean flag indicating if given location is empty or occupied.
     *  true  - empty
     *  false - occupied
     */
    public boolean isEmpty(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, null);

        return lTemp.isEmpty();
    }

    /**
     * This function checks if a given location has a brick  or not.
     * Input :
     *  x - x-index in cell units for given location.
     *  y - y-index in cell units for given location.
     *  
     *  Output : A boolean flag indicating if the presence/absence of a brick.
     *  true  - a brick present at given location.
     *  false - no brick present at given location.
     */
    public boolean hasBrickAt(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Brick.class);

        return !lTemp.isEmpty();
    }

    /**
     * This function checks if a given location has a sheep or not.
     * Input :
     *  x - x-index in cell units for given location.
     *  y - y-index in cell units for given location.
     *  
     *  Output : A boolean flag indicating if the presence/absence of a sheep.
     *  true  - a sheep present at given location.
     *  false - no sheep present at given location.
     */
    public boolean hasSheepAt(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Sheep.class);

        return !lTemp.isEmpty();
    }

    /**
     * This function checks if a given location has a grass-patch or not.
     * Input :
     *  x - x-index in cell units for given location.
     *  y - y-index in cell units for given location.
     *  
     *  Output : A boolean flag indicating if the presence/absence of a sheep.
     *  true  - a grass-patch present at given location.
     *  false - no grass-patch present at given location.
     */
    public boolean hasGrassAt(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Grass.class);

        return !lTemp.isEmpty();
    }
    
    public boolean hasFireAt(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Fire.class);

        return !lTemp.isEmpty();
    }
    
    public boolean hasRainAt(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Rain.class);

        return !lTemp.isEmpty();
    }
    
    public boolean hasWaterAt(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Water.class);

        return !lTemp.isEmpty();
    }

    /**
     * This function removes all the grass patches at a given location.
     * Input :
     *  x - x-index in cell units for given location.
     *  y - y-index in cell units for given location.
     */
    public void eatGrassAt(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Grass.class);

        if(!lTemp.isEmpty())
        {
            removeObject((Actor)lTemp.get(0));
        }
    }
    
    public void turnGrassToFire(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Grass.class);
        
        if(!lTemp.isEmpty())
        {
            removeObject((Actor)lTemp.get(0));
        }
        
        Fire f = new Fire();
        addObject(f, x, y);
    }
    
    public void turnRainToWater(int x, int y)
    {
        java.util.List lTemp = getObjectsAt(x, y, Rain.class);
        
        if(!lTemp.isEmpty())
        {
            removeObject((Actor)lTemp.get(0));
        }
        
        Water w = new Water();
        Water w1 = new Water();
        Water w2 = new Water();
        Water w3 = new Water();
        Water w4 = new Water();
        addObject(w, x, y);
        addObject(w1, x+1, y);
        addObject(w2, x-1, y);
        addObject(w3, x, y+1);
        addObject(w4, x, y-1);
    }
    
    public void seedGrass(int x, int y)
    {
        Grass newGrass = new Grass();
        addObject(newGrass, x, y);
    }
    
    public void spawnWaterAt(int x, int y)
    {
        Water newWater = new Water();
        addObject(newWater, x, y);
    }
    
    public void spawnRainAt(int x, int y)
    {
        Rain newRain = new Rain();
        addObject(newRain, x, y);
    }
    
    public void spawnFireAt(int x, int y)
    {
        Fire f = new Fire();
        addObject(f, x, y);
    }
    
    public void spawnLightningAt(int x, int y)
    {
        Lightning l = new Lightning();
        addObject(l, x, y);
    }

    /**
     * This function checks if a given location has a solid object (sheep/brick).
     * Input :
     *  x - x-index in cell units for given location.
     *  y - y-index in cell units for given location.
     *  
     *  Output : A boolean flag indicating if the presence/absence of a sheep.
     *  true  - a solid object present at given location.
     *  false - no solid object present at given location.
     */
    public boolean hasSolidAt(int x, int y)
    {
        return hasBrickAt(x, y) || hasSheepAt(x, y) || hasWaterAt(x, y);
    }

    /**
     * This method is called when the simulation is stopped
     */
    public void stopped()
    {
        counter.writeStats();
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     * 
     * This function is auto-generated by the Greenfoot development environment when 
     * you do "save world" by right-clicking on the world-instance in the main window.
     */
    private void prepare()
    {
        Sheep sheep = new Sheep();
        addObject(sheep, 14, 16);
        Sheep sheep2 = new Sheep();
        addObject(sheep2, 34, 11);
        sheep2.setDirection(true, true, true, true);
        sheep.setDirection(false, true, false, false);
        Grass grass = new Grass();
        addObject(grass, 20, 17);
        Grass grass2 = new Grass();
        addObject(grass2, 21, 17);
        Grass grass3 = new Grass();
        addObject(grass3, 21, 18);
        Grass grass4 = new Grass();
        addObject(grass4, 20, 18);
        Grass grass5 = new Grass();
        addObject(grass5, 20, 17);
        Grass grass6 = new Grass();
        addObject(grass6, 19, 16);
        Sheep sheep3 = new Sheep();
        addObject(sheep3, 9, 9);
        sheep3.setDirection(true, true, true, false);
        Sheep sheep4 = new Sheep();
        addObject(sheep4, 41, 25);
        sheep4.setDirection(false, true, true, false);
        addObject(counter, 6, 1);
        counter.setLocation(24, 1);
        counter.setLocation(11, 4);
        counter.setLocation(11, 1);
        counter.setLocation(9, 1);
        counter.setLocation(10, 1);
        counter.setLocation(44, 1);
        counter.setLocation(42, 1);
        counter.setLocation(41, 1);
        counter.setLocation(40, 1);
        counter.setLocation(38, 1);
        counter.setLocation(39, 1);
        removeObject(grass3);
        removeObject(grass5);
        removeObject(grass);
        removeObject(sheep);
        removeObject(grass6);
        removeObject(grass2);
        removeObject(grass4);
        removeObject(sheep3);
        removeObject(sheep2);
        removeObject(sheep4);
        Grass grass7 = new Grass();
        addObject(grass7, 41, 35);
        Grass grass8 = new Grass();
        addObject(grass8, 40, 34);
        Grass grass9 = new Grass();
        addObject(grass9, 39, 34);
        Grass grass10 = new Grass();
        addObject(grass10, 37, 33);
        Grass grass11 = new Grass();
        addObject(grass11, 36, 33);
        removeObject(grass11);
        Grass grass12 = new Grass();
        addObject(grass12, 36, 32);
        Grass grass13 = new Grass();
        addObject(grass13, 35, 33);
        Grass grass14 = new Grass();
        addObject(grass14, 35, 32);
        Grass grass15 = new Grass();
        addObject(grass15, 35, 31);
        Grass grass16 = new Grass();
        addObject(grass16, 34, 31);
        Grass grass17 = new Grass();
        addObject(grass17, 34, 30);
        Grass grass18 = new Grass();
        addObject(grass18, 33, 30);
        Grass grass19 = new Grass();
        addObject(grass19, 32, 30);
        Grass grass20 = new Grass();
        addObject(grass20, 35, 30);
        removeObject(grass20);
        Grass grass21 = new Grass();
        addObject(grass21, 35, 30);
        removeObject(grass21);
        Grass grass22 = new Grass();
        addObject(grass22, 35, 29);
        Grass grass23 = new Grass();
        addObject(grass23, 34, 29);
        Grass grass24 = new Grass();
        addObject(grass24, 33, 29);
        Grass grass25 = new Grass();
        addObject(grass25, 32, 29);
        Grass grass26 = new Grass();
        addObject(grass26, 31, 29);
        Grass grass27 = new Grass();
        addObject(grass27, 35, 28);
        Grass grass28 = new Grass();
        addObject(grass28, 34, 28);
        Grass grass29 = new Grass();
        addObject(grass29, 33, 28);
        Grass grass30 = new Grass();
        addObject(grass30, 32, 28);
        Grass grass31 = new Grass();
        addObject(grass31, 31, 28);
        Grass grass32 = new Grass();
        addObject(grass32, 30, 28);
        Grass grass33 = new Grass();
        addObject(grass33, 29, 28);
        Grass grass34 = new Grass();
        addObject(grass34, 28, 28);
        Grass grass35 = new Grass();
        addObject(grass35, 32, 27);
        Grass grass36 = new Grass();
        addObject(grass36, 31, 27);
        Grass grass37 = new Grass();
        addObject(grass37, 30, 27);
        Grass grass38 = new Grass();
        addObject(grass38, 29, 27);
        Grass grass39 = new Grass();
        addObject(grass39, 28, 27);
        Grass grass40 = new Grass();
        addObject(grass40, 31, 26);
        Grass grass41 = new Grass();
        addObject(grass41, 30, 26);
        Grass grass42 = new Grass();
        addObject(grass42, 29, 26);
        Grass grass43 = new Grass();
        addObject(grass43, 28, 26);
        Grass grass44 = new Grass();
        addObject(grass44, 27, 27);
        removeObject(grass44);
        Grass grass45 = new Grass();
        addObject(grass45, 27, 26);
        Grass grass46 = new Grass();
        addObject(grass46, 26, 26);
        Grass grass47 = new Grass();
        addObject(grass47, 25, 26);
        Grass grass48 = new Grass();
        addObject(grass48, 24, 26);
        Grass grass49 = new Grass();
        addObject(grass49, 29, 25);
        Grass grass50 = new Grass();
        addObject(grass50, 28, 25);
        Grass grass51 = new Grass();
        addObject(grass51, 27, 25);
        Grass grass52 = new Grass();
        addObject(grass52, 26, 25);
        Grass grass53 = new Grass();
        addObject(grass53, 25, 25);
        Grass grass54 = new Grass();
        addObject(grass54, 24, 25);
        Grass grass55 = new Grass();
        addObject(grass55, 23, 25);
        Grass grass56 = new Grass();
        addObject(grass56, 27, 24);
        Grass grass57 = new Grass();
        addObject(grass57, 25, 24);
        Grass grass58 = new Grass();
        addObject(grass58, 24, 24);
        Grass grass59 = new Grass();
        addObject(grass59, 23, 24);
        Grass grass60 = new Grass();
        addObject(grass60, 22, 24);
        Grass grass61 = new Grass();
        addObject(grass61, 21, 24);
        Grass grass62 = new Grass();
        addObject(grass62, 20, 24);
        Grass grass63 = new Grass();
        addObject(grass63, 24, 23);
        Grass grass64 = new Grass();
        addObject(grass64, 23, 23);
        Grass grass65 = new Grass();
        addObject(grass65, 22, 23);
        Grass grass66 = new Grass();
        addObject(grass66, 21, 23);
        Grass grass67 = new Grass();
        addObject(grass67, 20, 23);
        Grass grass68 = new Grass();
        addObject(grass68, 19, 23);
        Grass grass69 = new Grass();
        addObject(grass69, 21, 22);
        Grass grass70 = new Grass();
        addObject(grass70, 20, 22);
        Grass grass71 = new Grass();
        addObject(grass71, 19, 22);
        Grass grass72 = new Grass();
        addObject(grass72, 17, 22);
        Grass grass73 = new Grass();
        addObject(grass73, 16, 22);
        Grass grass74 = new Grass();
        addObject(grass74, 19, 19);
        Sheep sheep5 = new Sheep();
        addObject(sheep5, 31, 8);
        Sheep sheep6 = new Sheep();
        addObject(sheep6, 13, 28);
        sheep5.setDirection(true, true, false, false);
        sheep6.setDirection(true, false, false, true);
        Sheep sheep7 = new Sheep();
        addObject(sheep7, 41, 22);
        sheep7.setDirection(true, true, true, true);
        Sheep sheep8 = new Sheep();
        addObject(sheep8, 6, 14);
        sheep8.setDirection(false, true, true, true);
        Sheep sheep9 = new Sheep();
        addObject(sheep9, 27, 19);
        sheep9.setDirection(false, true, false, false);
        Sheep sheep10 = new Sheep();
        addObject(sheep10, 42, 11);
        sheep10.setDirection(true, true, false, false);
        Sheep sheep11 = new Sheep();
        addObject(sheep11, 26, 25);
        sheep11.setDirection(true, false, true, false);
        Sheep sheep12 = new Sheep();
        addObject(sheep12, 19, 22);
        sheep12.setDirection(false, true, false, false);
        Sheep sheep13 = new Sheep();
        addObject(sheep13, 11, 20);
        sheep13.setDirection(true, false, false, false);
        Sheep sheep14 = new Sheep();
        addObject(sheep14, 42, 30);
        sheep14.setDirection(true, true, true, true);
        Sheep sheep15 = new Sheep();
        addObject(sheep15, 21, 33);
        sheep15.setDirection(false, true, false, false);
        Sheep sheep16 = new Sheep();
        addObject(sheep16, 15, 9);
        sheep16.setDirection(false, true, true, true);
        Sheep sheep17 = new Sheep();
        addObject(sheep17, 23, 13);
        sheep17.setDirection(true, false, false, false);
        Sheep sheep18 = new Sheep();
        addObject(sheep18, 3, 34);
        sheep18.setDirection(true, true, true, true);
        
        User user = new User();
        addObject(user,0,0);
        
        WeatherController rc = new WeatherController();
        addObject(rc,0,0);
    }
}
