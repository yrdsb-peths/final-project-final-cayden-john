import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WiseFarmer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EvilEdd extends Actor
{
    GreenfootImage[] idle = new GreenfootImage[4];


    SimpleTimer animationTimer = new SimpleTimer();


    int idleIndex = 0;

    public void act()
    {
        animateIdle();
    }
    public EvilEdd(){
       for(int i = 0; i < idle.length; i++) {
           idle[i] = new GreenfootImage("images/Evil Edd/Evil Edd " + i + ".png");
           idle[i].scale(355, 355);
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
