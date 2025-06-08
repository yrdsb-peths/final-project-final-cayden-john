import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class EnemyShot extends Actor
{
    private double dx;
    private double dy;
    private final double SPEED = 6.5;

    private GreenfootImage[] fireballFrames = new GreenfootImage[4];
    private int frameIndex = 0;
    private SimpleTimer animationTimer = new SimpleTimer();

    public EnemyShot(int dxInput, int dyInput) {
        double length = Math.sqrt(dxInput * dxInput + dyInput * dyInput);
        if (length == 0) {
            dx = 0;
            dy = SPEED; 
        } else {
            dx = (dxInput / length) * SPEED;
            dy = (dyInput / length) * SPEED;
        }

 
        for (int i = 0; i < fireballFrames.length; i++) {
            fireballFrames[i] = new GreenfootImage("images/FireBall/Fireball " + i + ".png");
            fireballFrames[i].scale(100, 100); 
        }

        setImage(fireballFrames[0]);
        animationTimer.mark();
    }

    public void act()
    {
        moveShot();
        animate();

        if (getX() > getWorld().getWidth() - 20 || getX() < 5 || getY() > getWorld().getHeight() - 50 || getY() < 5) {
            getWorld().removeObject(this);
            return;
        }
    }

    private void moveShot() {
        setLocation((int)(getX() + dx), (int)(getY() + dy));
    }

    private void animate() {
        if (animationTimer.millisElapsed() < 250) {
            return;
        }

        frameIndex = (frameIndex + 1) % fireballFrames.length;
        setImage(fireballFrames[frameIndex]);
        animationTimer.mark();
    }
}
