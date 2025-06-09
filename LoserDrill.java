import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class LoserDrill extends Actor
{
    GreenfootImage[] idleRight = new GreenfootImage[2];
    GreenfootImage[] idleLeft = new GreenfootImage[2];
    GreenfootImage[] idleDown = new GreenfootImage[2];
    GreenfootImage[] idleUp = new GreenfootImage[2];

    SimpleTimer animationTimer = new SimpleTimer();

    String direction = "left";
    int idleIndex = 0;
    
    public void act()
    {
        if(getX() > 100 && getY() == 150){
            dashLeft();
            direction = "left";
        } 
        
        if(getX() < 100 && getY() < 420) {
            dashDown();
            direction = "down";
        }
        
        if(getY() > 420) {
            dashRight();
            direction = "right";
        }
        
        if(getX() > 880) {
            dashUp();
            direction = "up";
        }
        
        animate();
    }
    
    public LoserDrill(){
        for(int i = 0; i < idleLeft.length; i++) {
           idleLeft[i] = new GreenfootImage("images/Left Loser Driller/Lose driller " + i + ".png");
           idleLeft[i].scale(222, 222);
        }
        
        for(int i = 0; i < idleRight.length; i++) {
           idleRight[i] = new GreenfootImage("images/Left Loser Driller/Lose driller " + i + ".png");
           idleRight[i].mirrorHorizontally();
           idleRight[i].scale(222, 222);
        }
        
        for(int i = 0; i < idleDown.length; i++) {
           idleDown[i] = new GreenfootImage("images/Down Loser Driller/Down lose driller " + i + ".png");
           idleDown[i].scale(260, 260);
        }
        
        for(int i = 0; i < idleUp.length; i++) {
           idleUp[i] = new GreenfootImage("images/Up Loser Driller/Up lose driller " + i + ".png");
           idleUp[i].scale(260, 260);
        }
    }
    
    public void animate() {
        if(animationTimer.millisElapsed() < 125){
            return;
        }
        animationTimer.mark();
        
        if(direction.equals("left")) {
            setImage(idleLeft[idleIndex]);
        } else if(direction.equals("down")) {
            setImage(idleDown[idleIndex]);
        } else if(direction.equals("right")) {
            setImage(idleRight[idleIndex]);
        } else if(direction.equals("up")) {
            setImage(idleUp[idleIndex]);
        }
            
        idleIndex = (idleIndex + 1) % idleLeft.length;
    }
    
    public void dashLeft() {
        move(-8);
    }
    
    public void dashDown() {
        setLocation(getX(), getY() + 8);
    }
    
    public void dashRight() {
        move(8);
    }
    
    public void dashUp() {
        setLocation(getX(), getY() - 8);
    }
}
