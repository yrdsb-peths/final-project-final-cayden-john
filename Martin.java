import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Martin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Martin extends Actor
{
    GreenfootImage martin = new GreenfootImage("images/png/Idle (1).png");
    public void act()
    {
<<<<<<< Updated upstream
        
=======
        if(Greenfoot.isKeyDown("left")) {
            move(-5);
        } else if(Greenfoot.isKeyDown("right")) {
            move(5);
        }
        animateMartin();
>>>>>>> Stashed changes
    }
    
    public Martin() {
       setImage(martin);
       martin.scale(155, 158);
    }
}
