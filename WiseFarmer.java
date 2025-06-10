import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WiseFarmer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WiseFarmer extends Actor
{
    GreenfootImage[] idle = new GreenfootImage[2];

    SimpleTimer animationTimer = new SimpleTimer();
    
    int idleIndex = 0;
    
    private int maxHealth = 25;
    private int currentHealth = maxHealth;
    private EnemyHealthBar healthBar;

    public void act()
    {
        animateIdle();
        checkArrowHit();
    }
    
    public WiseFarmer(){
       for(int i = 0; i < idle.length; i++) {
           idle[i] = new GreenfootImage("images/Wise Farmer/Wise Farmer " + i + ".png");
           idle[i].scale(100, 100);
        }
    }
    
    public void animateIdle() {
        if(animationTimer.millisElapsed() < 400){
            return;
        }
        animationTimer.mark();


        setImage(idle[idleIndex]);
        idleIndex = (idleIndex + 1) % idle.length;
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
            getWorld().removeObject(this);
        }
    }
    
    
    public void setHealthBar(EnemyHealthBar healthBar) {
        this.healthBar = healthBar;
    }
}
