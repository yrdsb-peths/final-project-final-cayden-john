import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WiseFarmer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LoserDrill extends Actor
{
    GreenfootImage[] idle = new GreenfootImage[2];


    SimpleTimer animationTimer = new SimpleTimer();


    int idleIndex = 0;

    public void act()
    {
        animateIdle();
    }
    public LoserDrill(){
       for(int i = 0; i < idle.length; i++) {
           idle[i] = new GreenfootImage("images/Right Lose Driller/Lose driller " + i + ".png");
           idle[i].scale(222, 222);
        }
    }
    public void animateIdle() {
        if(animationTimer.millisElapsed() < 125){
            return;
        }
        animationTimer.mark();


            setImage(idle[idleIndex]);
            idleIndex = (idleIndex + 1) % idle.length;
    }
}
