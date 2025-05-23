import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Martin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Martin extends Actor
{
    GreenfootImage[] idleRight = new GreenfootImage[6];
    GreenfootImage[] idleLeft = new GreenfootImage[6];
    SimpleTimer animationTimer = new SimpleTimer();
    String facing = "right";
    
    public void act()
    {
        if(Greenfoot.isKeyDown("left")) {
            move(-5);
            facing = "left";
        } else if(Greenfoot.isKeyDown("right")) {
            move(5);
            facing = "right";
        }
        
        animateMartin();
    }
    
    public Martin() {
        for(int i = 0; i < idleRight.length; i++) {
           idleRight[i] = new GreenfootImage("images/Idle Hero/idle" + i + ".png");
           idleRight[i].scale(400, 400);
        }
        
        for(int i = 0; i < idleLeft.length; i++) {
           idleLeft[i] = new GreenfootImage("images/Idle Hero/idle" + i + ".png");
           idleLeft[i].mirrorHorizontally();
           idleLeft[i].scale(400, 400);
        }
       
        animationTimer.mark();
       
        setImage(idleRight[0]);
    }
    
    int imageIndex = 0;
    public void animateMartin() {
        if(animationTimer.millisElapsed() < 100) {
            return;
        }
        animationTimer.mark();
        
        if(facing.equals("right")) {
            setImage(idleRight[imageIndex]);
            imageIndex = (imageIndex + 1) % idleRight.length;
        } else {
            setImage(idleLeft[imageIndex]);
            imageIndex = (imageIndex + 1) % idleLeft.length;
        }
    }
}
