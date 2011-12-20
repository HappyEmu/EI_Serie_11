import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.awt.Font;
import java.io.*;

/**
 * This class implements a counter object that displays the number of sheep
 * and grass patches at a given point in time. It also writes the number of
 * grass patches and sheep in each simulation step to a file. 
 * 
 * You do not need to change this.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Counter extends Actor
{
    // Font size to be used for display text
    public static final float FONT_SIZE = 18.0f;
    
    // Width of the Counters displat window
    public static final int WIDTH = 300;
    
    // Height of the Counters displat window
    public static final int HEIGHT = 44;

    // File output
    BufferedWriter writer;
    
    /**
     * Default constructor 
     **/
    public Counter()
    {
        // display initial scores as zero 
        display("Number of Grass Patches: ", "Number of Sheeps: ", 0, 0);
        
        File file = new File("sheep_data.txt");
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch(IOException e)
        {}
    }
    
    /**
     * Act - do whatever the Counter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // At each time step, the counter updates and displays new scores of objects.
        
        World theField = getWorld();
        
        int grCount = theField.getObjects(Grass.class).size();
        int spCount = theField.getObjects(Sheep.class).size();
        
        // Display current scores
        display("Number of Grass Patches: ", "Number of Sheeps: ", grCount, spCount);
        
        String out = spCount + " " + grCount + System.getProperty("line.separator");
        try {
            writer.write(out);
        } catch(IOException e)
        {}
    }    
    
    /**
     * This is called by the world when it is stopped 
     * to flush the statistics file to disk.
     */
    public void writeStats()
    {
        try {
            writer.close();
        } catch(IOException e)
        {}
    }

    /**
     * Prepare and display the score board image.
     */
    private void display(String s1, String s2, int score1, int score2)
    {
        // Create an image instance
        GreenfootImage image = new GreenfootImage(WIDTH, HEIGHT);

        // Set fill color
        image.setColor(new Color(0, 0, 0, 100));
        
        // fill entire image with fill color
        image.fillRect(0, 0, WIDTH, HEIGHT);
        
        // set a new fill color
        image.setColor(new Color(255, 255, 255, 160));
        
        // fill interior region with new fill color
        image.fillRect(3, 3, WIDTH-6, HEIGHT-6);
        
        
        // set font for writing text.
        Font font = image.getFont();
        font = font.deriveFont(FONT_SIZE);
        image.setFont(font);
        image.setColor(Color.BLACK);
        
        // Write first part (number of grass patches) on the score board
        image.drawString(s1 + score1, 10, 18);
        
        // Write second part (number of sheeps) on the score board
        image.drawString(s2 + score2, 10, 36);
        
        // set new image for display
        setImage(image);
    }
}
