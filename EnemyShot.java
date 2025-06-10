import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class EnemyShot extends Actor {
    private int xSpeed;
    private int ySpeed;

    private GreenfootImage[] fireballFrames = new GreenfootImage[4];
    
    private int index = 0;
    private SimpleTimer animationTimer = new SimpleTimer();

    public EnemyShot(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        for (int i = 0; i < fireballFrames.length; i++) {
            fireballFrames[i] = new GreenfootImage("images/FireBall/Fireball " + i + ".png");
            fireballFrames[i].scale(100, 100);
        }

        setImage(fireballFrames[0]);
        animationTimer.mark();
    }

    public void act() {
        animate();
        setLocation(getX() + xSpeed, getY() + ySpeed);

        if (getX() > getWorld().getWidth() - 20 || getX() < 5 || getY() > getWorld().getHeight() - 50 || getY() < 5) {
            getWorld().removeObject(this);
        }
    }
    
    public void animate() {
        if (animationTimer.millisElapsed() >= 250) {
            index = (index + 1) % fireballFrames.length;
            setImage(fireballFrames[index]);
            animationTimer.mark();
        }
    }
}