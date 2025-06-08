import greenfoot.*;

public class EvilEdd extends Actor {
    GreenfootImage[] idle = new GreenfootImage[4];
    SimpleTimer animationTimer = new SimpleTimer();
    SimpleTimer shootTimer = new SimpleTimer();

    int idleIndex = 0;
    int nextShotDelay = Greenfoot.getRandomNumber(2000) + 500;

    private int maxHealth = 25;
    private int currentHealth = maxHealth;
    private EnemyHealthBar healthBar;

    public EvilEdd() {
        for (int i = 0; i < idle.length; i++) {
            idle[i] = new GreenfootImage("images/Evil Edd/Evil Edd " + i + ".png");
            idle[i].scale(355, 355);
        }
        shootTimer.mark();
    }

    public void act() {
        animateIdle();
        maybeShoot();
        checkArrowHit();
    }

    private void animateIdle() {
        if (animationTimer.millisElapsed() < 125) return;
        animationTimer.mark();
        setImage(idle[idleIndex]);
        idleIndex = (idleIndex + 1) % idle.length;
    }

    private void maybeShoot() {
        if (shootTimer.millisElapsed() > nextShotDelay) {
            shootTimer.mark();
            nextShotDelay = Greenfoot.getRandomNumber(2000) + 500;

            int dx = Greenfoot.getRandomNumber(11) - 5;
            int dy = Greenfoot.getRandomNumber(5) + 1;

            EnemyShot shot = new EnemyShot(dx, dy);
            getWorld().addObject(shot, getX(), getY());
        }
    }

    private void checkArrowHit() {
        for (Object obj : getIntersectingObjects(Arrow.class)) {
            Arrow arrow = (Arrow) obj;
            int hitboxRadius = 80;
            if (Math.abs(arrow.getX() - getX()) < hitboxRadius &&
                Math.abs(arrow.getY() - getY()) < hitboxRadius) {

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

    public int getMaxHealth() {
        return maxHealth;
    }
}
