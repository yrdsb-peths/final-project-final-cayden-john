import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class EvilEdd extends Actor
{
    GreenfootImage[] idle = new GreenfootImage[4];
    SimpleTimer animationTimer = new SimpleTimer();
    SimpleTimer shootTimer = new SimpleTimer();

    int idleIndex = 0;
    int nextShotDelay = Greenfoot.getRandomNumber(2000) + 500; // 0.5–2.5s

    public EvilEdd(){
        for(int i = 0; i < idle.length; i++) {
            idle[i] = new GreenfootImage("images/Evil Edd/Evil Edd " + i + ".png");
            idle[i].scale(355, 355);
        }
        shootTimer.mark();
    }

    public void act()
    {
        animateIdle();
        maybeShoot();
    }

    private void animateIdle() {
        if(animationTimer.millisElapsed() < 125){
            return;
        }
        animationTimer.mark();
        setImage(idle[idleIndex]);
        idleIndex = (idleIndex + 1) % idle.length;
    }

    private void maybeShoot() {
        if (shootTimer.millisElapsed() > nextShotDelay) {
            shootTimer.mark();
            nextShotDelay = Greenfoot.getRandomNumber(2000) + 500;

            // Only shoot downward
            int dx = Greenfoot.getRandomNumber(11) - 5;  // -5 to 5
            int dy = Greenfoot.getRandomNumber(5) + 1;   // 1 to 5 (always positive)

            EnemyShot shot = new EnemyShot(dx, dy);
            getWorld().addObject(shot, getX(), getY());
        }
    }
}
