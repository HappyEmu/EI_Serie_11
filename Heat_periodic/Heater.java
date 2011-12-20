import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;

/**
 * This class represents a Heater object that peforms and visualizes 
 * a 2D heat diffusion simulation.
 *
 * Start the simulation by clicking the run button. You can re-initialize the 
 * simulation by left-clicking anywhere in the output window.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Heater extends Actor
{
    // Number of horizontal grid points
    public static final int TILE_WIDTH = 40;
    
    // Number of vertical grid points
    public static final int TILE_HEIGHT = 40;

    // The simulation grid. Each entry in this field stores
    // a temperature value that changes over time.
    private float[][] heat;

    // Cell width in pixel units for visualization
    private int cellWidth;
    private int cellHeight;

    // A boolean flag indicating if heater is initialized or not
    private boolean heaterInitiated;

    private static final float r = 0.01f;//k / (h * h);

    /**
     * Default Constructor.
     */
    public Heater()
    {
       heaterInitiated = false;
    }

    /**
     * This function initializes the heater.
     */
    private void initHeater()
    {
        // Allocate memory for the simulation grid
        heat = new float[TILE_HEIGHT][TILE_WIDTH];

        // Get the size of the world window and compute the size of
        // one simulation cell in pixels. This is used for visualization
        // purposes.
        int worldWidth = getWorld().getWidth();
        int worldHeight = getWorld().getHeight();

        cellWidth  = (int) Math.ceil(worldWidth*1.0/TILE_WIDTH);
        cellHeight = (int) Math.ceil(worldHeight*1.0/TILE_HEIGHT);
        
        // Init the temperature distribution on the grid
        initTemperature();

        // Initialize the visualization
        setImage( new GreenfootImage(worldWidth, worldHeight));
        super.setLocation(worldWidth/2, worldHeight/2);

    }


    /**
     * Act - do whatever the Heater wants to do. This method is called in each
     * simulation step. The simulation is started by clicking the "run" button
     * at the bottom of the Greenfoot window.
     */
    public void act()
    {
        // Check if the heater is initialized or not. Initialize if necessary.
        if (!heaterInitiated)
        {
            initHeater();
            heaterInitiated = true;

        }
        
        
        // Check if the user has pressed a mouse button
        if(Greenfoot.mousePressed(null))
        {
            // Mouse is in pressed state. 
            
            // Get mouse information.
            MouseInfo mouse = Greenfoot.getMouseInfo();

            // Check if left button is pressed or not. 1 means left button.
            if (1 == mouse.getButton())
            {
                // Re-initialize room tempetature with random values.
                initTemperature();
            } else 
            // Check if right button is pressed. 3 means left button.
            if (3 == mouse.getButton())
            {   
                // By right clicking, the user can "paint" heat values on 
                // the grid. 
                
                // Get cell location that user is pointing at.
                int x = mouse.getX()/cellWidth;
                int y = mouse.getY()/cellHeight;

                x = (x < 0)? 0 : ( x >= TILE_WIDTH) ? TILE_WIDTH - 1 : x ;
                y = (y < 0)? 0 : ( y >= TILE_HEIGHT) ? TILE_HEIGHT - 1 : y ;

                // Set heat at the cell location pointed by the user to 1.
                heat[y][x] = 1.0f;
            }

        } else
        {
            // No user event. Continue to diffuse the temperature.
            // Add your code to the function called below.  
            diffuseTemperature();
        }

        displayTemperature();
    }

    /**
     * This function computes the heat diffusion in each time step. 
     * 
     * You need to change this code to implement heat diffusion. 
     * This is just for demonstration purposes!
     */
    private void diffuseTemperature()
    {        
        // "Dummy" implementation. You need to change this!
        float[][] newHeatMap = new float[TILE_HEIGHT][TILE_WIDTH];
        
        float oldTemp = 0.0f;
        float sampleRight = 0.0f;
        float sampleLeft = 0.0f;
        float sampleUp = 0.0f;
        float sampleDown = 0.0f;
        float newTemp = 0.0f;
        
        for(int i=0; i < TILE_HEIGHT; i++)
        {
            for(int j=0; j < TILE_WIDTH; j++)
            {
                oldTemp = heat[i][j];

                sampleRight = heat[i][mod(j+1,TILE_WIDTH)];
                sampleLeft = heat[i][mod(j-1,TILE_WIDTH)];
                sampleUp = heat[mod(i+1,TILE_HEIGHT)][j];
                sampleDown = heat[mod(i-1,TILE_HEIGHT)][j];

                newTemp = oldTemp+r*((sampleRight-oldTemp)+(sampleLeft-oldTemp)+(sampleUp-oldTemp)+(sampleDown-oldTemp));
                
                //Clamp
                if(newTemp < 0.0f) newTemp = 0.0f;
                
                newHeatMap[i][j] = newTemp;
            }
        }
        
        for(int i=0; i < TILE_HEIGHT; i++)
        {
            for(int j=0; j < TILE_WIDTH; j++)
            {
                heat[i][j]=newHeatMap[i][j];
            }
        }       
    }
    
    private int mod(int a, int b)
    {
        return (((a%b)+b)%b);
    }
    /**
     * This function initializes the temperature with a random value 
     * between 0 and 1 for each grid cell.
     * 
     */
    private void initTemperature()
    {
        // Initialize all cells with a random heat value.
        for(int i=0; i<TILE_HEIGHT; i += 1)
        {
            for(int j=0; j<TILE_WIDTH; j += 1)
            {
                // This is the array storing the heat values.
                heat[i][j] = Greenfoot.getRandomNumber(Integer.MAX_VALUE)*1.0f/(Integer.MAX_VALUE - 1);
            }
        }
    }
    
    /**
     * This function displays the room temperature using a certain coloring scheme.
     * 
     */
    private void displayTemperature()
    {
        // Draw the heat array.
        GreenfootImage image = new GreenfootImage(getWorld().getWidth(), getWorld().getHeight());

        for(int i=0; i < TILE_HEIGHT; i += 1)
        {
            for(int j=0; j < TILE_WIDTH; j += 1)
            {
                // The heat values (between 0 and 1) are transformed into
                // colors. Each color consists of red, green, and blue
                // components. This procedure makes hot (heat==1) red and
                // cold (heat==0) blue.
                float temp;
                int r,g,b;

                temp = heat[i][j];

                r = Math.round(255 * temp);
                b = Math.round(255*(1 - temp));
                g = Math.round(128*(1 - temp));

                image.setColor(new Color(r, g, b));
                image.fillRect(j*cellWidth, i*cellHeight, cellWidth, cellHeight);
            }
        } // end of for loops

        setImage(image);

    }

}
