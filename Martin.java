import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Martin extends Actor
{
    GreenfootImage[] idleRight = new GreenfootImage[6];
    GreenfootImage[] idleLeft = new GreenfootImage[6];
    GreenfootImage[] walkRight = new GreenfootImage[8];
    GreenfootImage[] walkLeft = new GreenfootImage[8];
    GreenfootImage[] attackRight = new GreenfootImage[9];
    GreenfootImage[] attackLeft = new GreenfootImage[9];

    SimpleTimer animationTimer = new SimpleTimer();
    String facing = "right";
    boolean isWalking = false;

    private int jumpHeight;         
    private int gravity = 1;           
    private boolean isOnGround = true; 
    private int feetOffset = 170; 

    int idleIndex = 0;
    int walkIndex = 0;

    SimpleTimer shootCooldown = new SimpleTimer();

    private boolean isAttacking = false;
    private int attackIndex;

    public Martin()
    {
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

        for(int i = 0; i < attackRight.length; i++) {
            attackRight[i] = new GreenfootImage("images/Hero Attack/attack" + i + ".png");
            attackRight[i].scale(400,400);
        }

        for(int i = 0; i < attackLeft.length; i++) {
            attackLeft[i] = new GreenfootImage("images/Hero Attack/attack" + i + ".png");
            attackLeft[i].mirrorHorizontally();
            attackLeft[i].scale(400, 400);
        }

        animationTimer.mark();
        setImage(idleRight[0]);

        shootCooldown.mark();
    }

    public void act() {
        gravity();
    
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up")) {
            jump();
        }
    
        checkGroundCollision();
    
        if (isAttacking) {
            animateAttack();
            return;  
        }
    
        isWalking = false;
    
        if(Greenfoot.isKeyDown("a")|| Greenfoot.isKeyDown("left")) {
            move(-5);
            facing = "left";
            isWalking = true;
        } else if(Greenfoot.isKeyDown("d")|| Greenfoot.isKeyDown("right")) {
            move(5);
            facing = "right";
            isWalking = true;
        }
    
        if (isWalking) {
            animateWalk();
        } else {
            animateIdle();
        }
    
        if (Greenfoot.isKeyDown("space") && shootCooldown.millisElapsed() > 500) {
            isAttacking = true;
            attackIndex = 0;
            shootCooldown.mark();
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

    public void animateAttack() {
        if(animationTimer.millisElapsed() < 50) {
            return;
        }
        animationTimer.mark();

        if(facing.equals("right")) {
            setImage(attackRight[attackIndex]);
        } else {
            setImage(attackLeft[attackIndex]);
        }

        if(attackIndex == 7) {
            Arrow arrow = new Arrow(facing);
            getWorld().addObject(arrow, getX(), getY());
        }

        attackIndex++;

        if(attackIndex >= attackRight.length) {
            isAttacking = false;
            attackIndex = 0;
        }
    }

    public void gravity() {
        setLocation(getX(), getY() + jumpHeight);
        jumpHeight += gravity;
        int feetY = getY() + (getImage().getHeight() / 2) - feetOffset;
        int floorY = getWorld().getHeight();

        if (feetY >= floorY) {
            setLocation(getX(), floorY - ((getImage().getHeight() / 2) - feetOffset));
            jumpHeight = 0;
            isOnGround = true;
        } else if (!isOnGround) {
            isOnGround = false;
        }
    }

    public void checkGroundCollision() {
        if (jumpHeight >= 0) {
            int checkOffset = (getImage().getHeight() / 2) - feetOffset + 5;
            Actor ground = getOneObjectAtOffset(0, checkOffset, Platform.class);
    
            if (ground != null) {
                int groundTopY = ground.getY() - ground.getImage().getHeight() / 2;
                int feetY = getY() + (getImage().getHeight() / 2) - feetOffset;
    
                if (feetY > groundTopY) {
                    setLocation(getX(), groundTopY - ((getImage().getHeight() / 2) - feetOffset));
                    jumpHeight = 0;
                    isOnGround = true;
                }
            } else {
                if (getY() + (getImage().getHeight() / 2) - feetOffset < getWorld().getHeight()) {
                    isOnGround = false;
                }
            }
        }
    }


    public void jump() {
        if (isOnGround) {
            jumpHeight = -20; 
            isOnGround = false;
        }
    }
}
