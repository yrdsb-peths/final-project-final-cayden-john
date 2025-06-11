import greenfoot.*;

public class EvilEdd extends Actor {
    GreenfootImage[] idle = new GreenfootImage[4];
    
    SimpleTimer animationTimer = new SimpleTimer();
    SimpleTimer shootTimer1 = new SimpleTimer();
    SimpleTimer shootTimer2 = new SimpleTimer();


    int idleIndex = 0;
    int nextShotDelay;

    private int maxHealth = 25;
    private int currentHealth = maxHealth;
    private EnemyHealthBar healthBar;

    public EvilEdd() {
        for (int i = 0; i < idle.length; i++) {
            idle[i] = new GreenfootImage("images/Evil Edd/Evil Edd " + i + ".png");
            idle[i].scale(255, 255);
        }
        shootTimer1.mark();
        shootTimer2.mark();
    }

    public void act() {
        animateIdle();
        maybeShoot();
        maybeShoot1();
        checkArrowHit();
    }

    private void animateIdle() {
        if (animationTimer.millisElapsed() < 125) {
            return;
        }
        
        animationTimer.mark();
        setImage(idle[idleIndex]);
        idleIndex = (idleIndex + 1) % idle.length;
    }

    //Possibly shoots regular shot (using randomizer)
    private void maybeShoot() {
        if (shootTimer1.millisElapsed() > Greenfoot.getRandomNumber(100) + 500) {
            shootTimer1.mark();
            int xSpeed = Greenfoot.getRandomNumber(11) - 5;
            int ySpeed = Greenfoot.getRandomNumber(7) + 1;
            EnemyShot shot = new EnemyShot(xSpeed, ySpeed);
            getWorld().addObject(shot, getX(), getY());
        }
    }
    
    //Possibly shoots super shot (using randomizer)
    private void maybeShoot1() {
        if (shootTimer2.millisElapsed() > Greenfoot.getRandomNumber(100000) + 650) {
            shootTimer2.mark();
            int xSpeed = Greenfoot.getRandomNumber(11) - 5;
            int ySpeed = Greenfoot.getRandomNumber(7) + 1;
            StrongShot strongShot = new StrongShot(xSpeed, ySpeed);
            getWorld().addObject(strongShot, getX(), getY());
        }
    }

    //checks for arrow collision
    private void checkArrowHit() {
        for (Object obj : getIntersectingObjects(Arrow.class)) {
            Arrow arrow = (Arrow) obj;
            int hitboxRadius = 80;
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
            LoserDrill loserDrill = new LoserDrill();
            getWorld().addObject(loserDrill,800, 150);
            EnemyHealthBar drillHealthBar = new EnemyHealthBar(25);
            getWorld().addObject(drillHealthBar, 500, 30); 
            loserDrill.setHealthBar(drillHealthBar);
            
            getWorld().removeObject(this);
        }
    }

    public void setHealthBar(EnemyHealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
}
