import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class StrongShot extends Actor {
    
    private final int ANIMATION_SPEED = 150; // Millis for animation
    private int xSpeed;
    private int ySpeed;

    private GreenfootImage[] StrongShot = new GreenfootImage[4];
    
    private int index = 0;
    private SimpleTimer animationTimer = new SimpleTimer();

    public StrongShot(int xSpeed, int ySpeed) {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        for (int i = 0; i < StrongShot.length; i++) {
            StrongShot[i] = new GreenfootImage("images/StrongShot/StrongShot" + i + ".png");
            StrongShot[i].scale(100, 100);
        }

        setImage(StrongShot[0]);
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
        if (animationTimer.millisElapsed() >= ANIMATION_SPEED) {
            index = (index + 1) % StrongShot.length;
            setImage(StrongShot[index]);
            animationTimer.mark();
        }
    }
}