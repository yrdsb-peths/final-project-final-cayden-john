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
    
    private int maxHealth = 52;
    private int currentHealth = maxHealth;
    private EnemyHealthBar healthBar;
    
    String movementStage = "topRight"; 
    int dashPattern = 0;
    boolean patternComplete = true;
    
    public void act()
    {
        chooseDashPattern();
        animate();
        checkArrowHit();
    }
    
    
    //animations for LoserDrill
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
    
    //Animating based on direction facing
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
        move(-10);
    }
    
    public void dashDown() {
        setLocation(getX(), getY() + 10);
    }
    
    public void dashRight() {
        move(10);
    }
    
    public void dashUp() {
        setLocation(getX(), getY() - 10);
    }
    
    //check for arrow collision
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

    //take damage and dies when out of health
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
            EnemyHealthBar farmerHealthBar = new EnemyHealthBar(75);
            getWorld().addObject(farmerHealthBar, 500, 30); 
            wiseFarmer.setHealthBar(farmerHealthBar);
            
            getWorld().removeObject(this);
        }
    }
    
    public void setHealthBar(EnemyHealthBar healthBar) {
        this.healthBar = healthBar;
    }

    // first dash pattern, left -> down -> right -> up
    public void dashPattern1() {
        if (movementStage.equals("topRight")) {
            if (getX() > 95 && getY() == 150) {
                dashLeft();
                direction = "left";
            } else {
                movementStage = "downLeft";
            }
        }

        if (movementStage.equals("downLeft")) {
            if (getX() <= 95 && getY() < 420) {
                dashDown();
                direction = "down";
            } else {
                movementStage = "bottomRight";
            }
        }

        if (movementStage.equals("bottomRight")) {
            if (getY() != 415) {
                setLocation(getX(), 415);
            }
            if (getX() < 875) {
                dashRight();
                direction = "right";
            } else {
                movementStage = "upRight";
            }
        }

        if (movementStage.equals("upRight")) {
            if (getX() != 875) {
                setLocation(875, getY());
            }
            if (getY() > 150) {
                dashUp();
                direction = "up";
            } else {
                patternComplete = true;
                movementStage = "topRight"; 
            }
        }
    }

    //second dash pattern, left -> stops at the middle and goes down -> right -> up
    public void dashPattern2() {
        if (movementStage.equals("topRight")) {
            if (getY() != 150) {
                setLocation(getX(), 150);
            }
            if (getX() > 495) {
                dashLeft();
                direction = "left";
            } else {
                movementStage = "midDown";
            }
        }

        if (movementStage.equals("midDown")) {
            if (getX() != 495) {
                setLocation(495, getY());
            }
            if (getY() < 300) {
                dashDown();
                direction = "down";
            } else {
                movementStage = "midRight";
            }
        }

        if (movementStage.equals("midRight")) {
            if (getY() != 300) {
                setLocation(getX(), 150);
            }
            if (getX() < 875) {
                dashRight();
                direction = "right";
            } else {
                movementStage = "upToTopRight";
            }
        }

        if (movementStage.equals("upToTopRight")) {
            if (getX() != 95) {
                setLocation(875, getY());
            }
            if (getY() > 150) {
                dashUp();
                direction = "up";
            } else {
                patternComplete = true;
                movementStage = "topRight"; 
            }
        }
    }

    //third dash pattern, left -> down -> right -> left -> right -> up 
    public void dashPattern3() {
        if (movementStage.equals("topRight")) {
            if (getX() > 95 && getY() == 150) {
                dashLeft();
                direction = "left";
            } else {
                movementStage = "downLeft";
            }
        }

        if (movementStage.equals("downLeft")) {
            if (getX() <= 95 && getY() < 420) {
                dashDown();
                direction = "down";
            } else {
                movementStage = "bottomRight";
            }
        }

        if (movementStage.equals("bottomRight")) {
            if (getY() != 415) {
                setLocation(getX(), 415);
            }
            if (getX() < 875) {
                dashRight();
                direction = "right";
            } else {
                movementStage = "bottomLeft";
            }
        }

        if (movementStage.equals("bottomLeft")) {
            if (getY() != 415) {
                setLocation(getX(), 415);
            }
            if (getX() > 95) {
                dashLeft();
                direction = "left";
            } else {
                movementStage = "backToBottomRight";
            }
        }

        if (movementStage.equals("backToBottomRight")) {
            if (getY() != 415) {
                setLocation(getX(), 415);
            }
            if (getX() < 875) {
                dashRight();
                direction = "right";
            } else {
                movementStage = "upToTopRight";
            }
        }

        if (movementStage.equals("upToTopRight")) {
            if (getX() != 875) {
                setLocation(875, getY());
            }
            if (getY() > 150) {
                dashUp();
                direction = "up";
            } else {
                patternComplete = true;
                movementStage = "topRight";  
            }
        }
    }

    //Randomly choose a dash pattern to play
    public void chooseDashPattern() {
        if (patternComplete) {
            setLocation(875, 150);
            movementStage = "topRight";
            dashPattern = Greenfoot.getRandomNumber(3) + 1;
            patternComplete = false;
        }

        if (!patternComplete) {
            if (dashPattern == 1) {
                dashPattern1();
            } else if (dashPattern == 2) {
                dashPattern2();
            } else {
                dashPattern3();
            }
        }
    }
}
