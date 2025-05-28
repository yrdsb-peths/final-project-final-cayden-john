import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Platform here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Platform extends Actor
{
    GreenfootImage image = new GreenfootImage("images/Platform.png");
    public void act()
    {
        
    }
    
    public Platform(int width, int height) {
        setImage(image);
        image.scale(width, height);
    }
}
