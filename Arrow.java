import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arrow extends Actor
{
    GreenfootImage arrow = new GreenfootImage("images/Arrow1.png");;
    
    public void act()
    {
        setImage(arrow);
        arrow.scale(82, 82);
    }
}
