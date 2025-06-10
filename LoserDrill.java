import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class LoserDrill extends Actor
{
    GreenfootImage[] idleRight = new GreenfootImage[2];
    GreenfootImage[] idleLeft = new GreenfootImage[2];
    GreenfootImage[] idleDown = new GreenfootImage[2];
    GreenfootImage[] idleUp = new GreenfootImage[2];

    SimpleTimer animationTimer = new SimpleTimer();

    String direction = "left";
    int idleIndex = 0;
    
    private int maxHealth = 25;
    private int currentHealth = maxHealth;
    private EnemyHealthBar healthBar;
    
    public void act()
    {
        if(getX() > 100 && getY() == 150){
            dashLeft();
            direction = "left";
        } 
        
        if(getX() < 100 && getY() < 420) {
            dashDown();
            direction = "down";
        }
        
        if(getY() > 420) {
            dashRight();
            direction = "right";
        }
        
        if(getX() > 880) {
            dashUp();
            direction = "up";
        }
        
        animate();
        checkArrowHit();
    }
    
    public LoserDrill(){
        for(int i = 0; i < idleLeft.length; i++) {
           idleLeft[i] = new GreenfootImage("images/Left Loser Driller/Lose driller " + i + ".png");
           idleLeft[i].scale(222, 222);
        }
        
        for(int i = 0; i < idleRight.length; i++) {
           idleRight[i] = new GreenfootImage("images/Left Loser Driller/Lose driller " + i + ".png");
           idleRight[i].mirrorHorizontally();
           idleRight[i].scale(222, 222);
        }
        
        for(int i = 0; i < idleDown.length; i++) {
           idleDown[i] = new GreenfootImage("images/Down Loser Driller/Down lose driller " + i + ".png");
           idleDown[i].scale(260, 260);
        }
        
        for(int i = 0; i < idleUp.length; i++) {
           idleUp[i] = new GreenfootImage("images/Up Loser Driller/Up lose driller " + i + ".png");
           idleUp[i].scale(260, 260);
        }
    }
    
    public void animate() {
        if(animationTimer.millisElapsed() < 125){
            return;
        }
        animationTimer.mark();
        
        if(direction.equals("left")) {
            setImage(idleLeft[idleIndex]);
        } else if(direction.equals("down")) {
            setImage(idleDown[idleIndex]);
        } else if(direction.equals("right")) {
            setImage(idleRight[idleIndex]);
        } else if(direction.equals("up")) {
            setImage(idleUp[idleIndex]);
        }
            
        idleIndex = (idleIndex + 1) % idleLeft.length;
    }
    
    public void dashLeft() {
        move(-8);
    }
    
    public void dashDown() {
        setLocation(getX(), getY() + 8);
    }
    
    public void dashRight() {
        move(8);
    }
    
    public void dashUp() {
        setLocation(getX(), getY() - 8);
    }
    
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
            WiseFarmer wiseFarmer = new WiseFarmer();
            getWorld().addObject(wiseFarmer, 850, 464);
            EnemyHealthBar farmerHealthBar = new EnemyHealthBar(25);
            getWorld().addObject(farmerHealthBar, 830, 30); 
            wiseFarmer.setHealthBar(farmerHealthBar);
            
            getWorld().removeObject(this);
        }
    }
    
        public void setHealthBar(EnemyHealthBar healthBar) {
        this.healthBar = healthBar;
    }
}
