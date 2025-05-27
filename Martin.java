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
    GreenfootImage[] walkRight = new GreenfootImage[8];
    GreenfootImage[] walkLeft = new GreenfootImage[8];

    SimpleTimer animationTimer = new SimpleTimer();
    String facing = "right";
    boolean isWalking = false;

    
    private int jumpHeight;         
    private int gravity = 1;           
    private boolean isOnGround = true; 
    
    int idleIndex = 0;
    int walkIndex = 0;

    SimpleTimer shootCooldown = new SimpleTimer();
    
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

        for(int i = 0; i < walkRight.length; i++) {
           walkRight[i] = new GreenfootImage("images/Hero Walk/walk" + i + ".png");
           walkRight[i].scale(400, 400);
        }

        for(int i = 0; i < walkLeft.length; i++) {
           walkLeft[i] = new GreenfootImage("images/Hero Walk/walk" + i + ".png");
           walkLeft[i].mirrorHorizontally();
           walkLeft[i].scale(400, 400);
        }

        animationTimer.mark();
        setImage(idleRight[0]);
        
        shootCooldown.mark();
    }

    public void act()
    {
        isWalking = false;

        if(Greenfoot.isKeyDown("left")) {
            move(-5);
            facing = "left";
            isWalking = true;
        } else if(Greenfoot.isKeyDown("right")) {
            move(5);
            facing = "right";
            isWalking = true;
        }

        if (isWalking) {
            animateWalk();
        } else {
            animateIdle();
        }
        
        if (Greenfoot.isKeyDown("up")) {
            jump();
        }
    
        gravity();
        
        if(Greenfoot.isKeyDown("space")) {
            shootArrow();
        }
    }

    public void animateIdle() {
        if(animationTimer.millisElapsed() < 100){
            return;
        }
        animationTimer.mark();

        if(facing.equals("right")) {
            setImage(idleRight[idleIndex]);
            idleIndex = (idleIndex + 1) % idleRight.length;
        } else {
            setImage(idleLeft[idleIndex]);
            idleIndex = (idleIndex + 1) % idleLeft.length;
        }
    }

    public void animateWalk() {
        if(animationTimer.millisElapsed() < 100) {
            return;
        }
        animationTimer.mark();

        if(facing.equals("right")) {
            setImage(walkRight[walkIndex]);
            walkIndex = (walkIndex + 1) % walkRight.length;
        } else {
            setImage(walkLeft[walkIndex]);
            walkIndex = (walkIndex + 1) % walkLeft.length;
        }
    }
    
    public void jump() {
        if (isOnGround) {
            jumpHeight = -20; 
            isOnGround = false;
        }
    }
    
    public void gravity() {
        setLocation(getX(), getY() + jumpHeight);
        jumpHeight += gravity;
    
        if (getY() >= 500) {
            setLocation(getX(), 500);
            jumpHeight = 0;
            isOnGround = true;
        }
    }
    
    public void shootArrow() {
        if(shootCooldown.millisElapsed() < 300) {
            return;
        }
        shootCooldown.mark();
        
        Arrow arrow = new Arrow(facing); 
        getWorld().addObject(arrow, getX(), getY());
    }
}
