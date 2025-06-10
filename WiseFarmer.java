import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WiseFarmer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WiseFarmer extends Actor
{
    GreenfootImage[] idleLeft = new GreenfootImage[2];
    GreenfootImage[] idleRight = new GreenfootImage[2];
    
    SimpleTimer animationTimer = new SimpleTimer();
    SimpleTimer shootTimer = new SimpleTimer();
    
    int idleIndex = 0;
    int nextShotDelay = Greenfoot.getRandomNumber(2000) + 500;
    
    private int maxHealth = 75;
    private int currentHealth = maxHealth;
    private EnemyHealthBar healthBar;
    int dashReset = 0;
    String facing = "left";
    int xSpeed = -5;

    public void act()
    {
        checkArrowHit();
        
        dashReset++;
        dash();
        
        if(facing.equals("left") && dashReset <= 1000 && getX() > 100) {
            animateIdleLeft();
            shoot();
        } else if (facing.equals("right") && dashReset <= 1000 && getX() < 830) {
            animateIdleRight();
            shoot();
        }
    }
    
    public WiseFarmer(){
        for(int i = 0; i < idleLeft.length; i++) {
           idleLeft[i] = new GreenfootImage("images/Wise Farmer/Wise Farmer " + i + ".png");
           idleLeft[i].scale(100, 100);
        }
        
        for(int i = 0; i < idleRight.length; i++) {
           idleRight[i] = new GreenfootImage("images/Wise Farmer/Wise Farmer " + i + ".png");
           idleRight[i].mirrorHorizontally();
           idleRight[i].scale(100, 100);
        }
    }
    
    public void animateIdleLeft() {
        if(animationTimer.millisElapsed() < 400){
            return;
        }
        animationTimer.mark();

        setImage(idleLeft[idleIndex]);
        idleIndex = (idleIndex + 1) % idleLeft.length;
    }

    public void animateIdleRight() {
        if(animationTimer.millisElapsed() < 400){
            return;
        }
        animationTimer.mark();

        setImage(idleRight[idleIndex]);
        idleIndex = (idleIndex + 1) % idleRight.length;
    }
    
    private void checkArrowHit() {
        for (Object obj : getIntersectingObjects(Arrow.class)) {
            Arrow arrow = (Arrow) obj;
            int hitboxRadius = 50;
            if (Math.abs(arrow.getX() - getX()) < hitboxRadius && Math.abs(arrow.getY() - getY()) < hitboxRadius) {

                getWorld().removeObject(arrow);
                takeDamage(1);
                break;
            }
        }
    }

    private void takeDamage(int amount) {
        currentHealth = Math.max(0, currentHealth - amount);
        
        if (healthBar != null) {
            healthBar.updateHealth(currentHealth);
        }

        if (currentHealth <= 0) {
            if (healthBar != null) {
                getWorld().removeObject(healthBar);
            }
            EndScreen win = new EndScreen();
            Greenfoot.setWorld(win);
            
            getWorld().removeObject(this);
        }
    }
    
    private void shoot() {
        if (shootTimer.millisElapsed() > nextShotDelay) {
            shootTimer.mark();
            nextShotDelay = Greenfoot.getRandomNumber(500) + 1000;

            EnemyShot shot = new EnemyShot(xSpeed, 0);
            getWorld().addObject(shot, getX(), getY());
        }
    }
    
    public void setHealthBar(EnemyHealthBar healthBar) {
        this.healthBar = healthBar;
    }
    
    public void dash() {
        if(facing.equals("left") && dashReset >= 1000) {
            move(-8);
            if(getX() < 95) {
                facing = "right";
                xSpeed = 5;
                dashReset = 0;
            }
        } else if (facing.equals("right") && dashReset >= 1000) {
            move(8);
            if(getX() > 795) {
                facing = "left";
                xSpeed = -5;
                dashReset = 0;
            }
        }
    }
}
