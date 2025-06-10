import greenfoot.*;

public class Martin extends Actor {
    GreenfootImage[] idleRight = new GreenfootImage[6];
    GreenfootImage[] idleLeft = new GreenfootImage[6];
    GreenfootImage[] walkRight = new GreenfootImage[8];
    GreenfootImage[] walkLeft = new GreenfootImage[8];
    GreenfootImage[] attackRight = new GreenfootImage[9];
    GreenfootImage[] attackLeft = new GreenfootImage[9];
    GreenfootImage[] hurtRight = new GreenfootImage[4];
    GreenfootImage[] hurtLeft = new GreenfootImage[4];
    GreenfootImage[] deathRight = new GreenfootImage[4];
    GreenfootImage[] deathLeft = new GreenfootImage[4];

    SimpleTimer animationTimer = new SimpleTimer();
    String facing = "right";
    boolean isWalking = false;

    private int jumpHeight;
    private int gravity = 1;
    private boolean isOnGround = true;
    private int feetOffset = 172;

    int idleIndex = 0;
    int walkIndex = 0;

    SimpleTimer shootCooldown = new SimpleTimer();

    private boolean isAttacking = false;
    private int attackIndex;

    private int health = 12;
    private HealthBar healthBar;

    private boolean isHurt = false;
    private int hurtIndex = 0;
    private SimpleTimer hurtTimer = new SimpleTimer();
    private SimpleTimer damageCooldown = new SimpleTimer();

    int deathIndex = 0;
    boolean isDead = false;

    public Martin() {
        for(int i = 0; i < idleRight.length; i++) {
            idleRight[i] = new GreenfootImage("images/Idle Hero/idle" + i + ".png");
            idleRight[i].scale(400, 400);
        }

        for(int i = 0; i < idleLeft.length; i++) {
            idleLeft[i] = new GreenfootImage("images/Idle Hero/idle" + i + ".png");
            idleLeft[i].mirrorHorizontally();
            idleLeft[i].scale(400, 400);
        }

        for(int i = 0; i < walkRight.length; i++) {
            walkRight[i] = new GreenfootImage("images/Hero Walk/walk" + i + ".png");
            walkRight[i].scale(400, 400);
        }

        for(int i = 0; i < walkLeft.length; i++) {
            walkLeft[i] = new GreenfootImage("images/Hero Walk/walk" + i + ".png");
            walkLeft[i].mirrorHorizontally();
            walkLeft[i].scale(400, 400);
        }

        for(int i = 0; i < attackRight.length; i++) {
            attackRight[i] = new GreenfootImage("images/Hero Attack/attack" + i + ".png");
            attackRight[i].scale(400, 400);
        }

        for(int i = 0; i < attackLeft.length; i++) {
            attackLeft[i] = new GreenfootImage("images/Hero Attack/attack" + i + ".png");
            attackLeft[i].mirrorHorizontally();
            attackLeft[i].scale(400, 400);
        }

        for (int i = 0; i < hurtRight.length; i++) {
            hurtRight[i] = new GreenfootImage("images/Hero Hurt/hurt" + i + ".png");
            hurtRight[i].scale(400, 400);
        }

        for (int i = 0; i < hurtLeft.length; i++) {
            hurtLeft[i] = new GreenfootImage("images/Hero Hurt/hurt" + i + ".png");
            hurtLeft[i].mirrorHorizontally();
            hurtLeft[i].scale(400, 400);
        }

        for (int i = 0; i < deathRight.length; i++) {
            deathRight[i] = new GreenfootImage("images/Hero Death/death" + i + ".png");
            deathRight[i].scale(400, 400);
        }

        for (int i = 0; i < deathLeft.length; i++) {
            deathLeft[i] = new GreenfootImage("images/Hero Death/death" + i + ".png");
            deathLeft[i].mirrorHorizontally(); 
            deathLeft[i].scale(400, 400);
        }

        hurtTimer.mark();
        damageCooldown.mark();
        animationTimer.mark();
        setImage(idleRight[0]);
        shootCooldown.mark();
    }

    public void act() {
        gravity();

        if (isDead) {
            gravity(); 
            checkGroundCollision();
            animateDeath();
            return;
        }

        if (!isHurt && damageCooldown.millisElapsed() > 1000) {
            EnemyShot shot = (EnemyShot)getOneIntersectingObject(EnemyShot.class);
            if (shot != null) {
                int dx = getX() - shot.getX();
                int dy = getY() - shot.getY();
                int distance = (int)Math.sqrt(dx * dx + dy * dy);
                if (distance < 60) {
                    getWorld().removeObject(shot);
                    isHurt = true;
                    hurtIndex = 0;
                    damageCooldown.mark();
                    takeDamage();
                }
            }
        }

        EvilEdd evilEdd = (EvilEdd)getOneIntersectingObject(EvilEdd.class);
        if (evilEdd != null && !isHurt && damageCooldown.millisElapsed() > 1000) {
            int dx = getX() - evilEdd.getX();
            int dy = getY() - evilEdd.getY();
            int distance = (int)Math.sqrt(dx * dx + dy * dy);
            if (distance < 125) {
                isHurt = true;
                hurtIndex = 0;
                damageCooldown.mark();
                takeDamage();
            }
        }
        
        LoserDrill loserDrill = (LoserDrill)getOneIntersectingObject(LoserDrill.class);
        if (loserDrill != null && !isHurt && damageCooldown.millisElapsed() > 1000) {
            int dx = getX() - loserDrill.getX();
            int dy = getY() - loserDrill.getY();
            int distance = (int)Math.sqrt(dx * dx + dy * dy);
            if (distance < 90) { 
                isHurt = true;
                hurtIndex = 0;
                damageCooldown.mark();
                takeDamage();
            }
        }
        
        WiseFarmer wiseFarmer = (WiseFarmer)getOneIntersectingObject(WiseFarmer.class);
        if (wiseFarmer != null && !isHurt && damageCooldown.millisElapsed() > 1000) {
            int dx = getX() - wiseFarmer.getX();
            int dy = getY() - wiseFarmer.getY();
            int distance = (int)Math.sqrt(dx * dx + dy * dy);
            if (distance < 50) { 
                isHurt = true;
                hurtIndex = 0;
                damageCooldown.mark();
                takeDamage();
            }
        }

        if (isHurt) {
            animateHurt();
        }

        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up")) {
            jump();
        }

        checkGroundCollision();

        if (isAttacking) {
            animateAttack();
            return;
        }

        isWalking = false;

        if(Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left")) {
            move(-5);
            facing = "left";
            isWalking = true;
        } else if(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right")) {
            move(5);
            facing = "right";
            isWalking = true;
        }

        if (isWalking) {
            animateWalk();
        } else {
            animateIdle();
        }

        if (Greenfoot.isKeyDown("space") && shootCooldown.millisElapsed() > 500) {
            isAttacking = true;
            attackIndex = 0;
            shootCooldown.mark();
        }
    }

    public void animateIdle() {
        if(animationTimer.millisElapsed() < 100){
            return;
        }
        animationTimer.mark();

        if(facing.equals("right")) {
            setImage(idleRight[idleIndex]);
        } else {
            setImage(idleLeft[idleIndex]);
        }
        idleIndex = (idleIndex + 1) % idleRight.length;
    }

    public void animateWalk() {
        if(animationTimer.millisElapsed() < 100) {
            return;
        }
        animationTimer.mark();

        if(facing.equals("right")) {
            setImage(walkRight[walkIndex]);
        } else {
            setImage(walkLeft[walkIndex]);
        }
        walkIndex = (walkIndex + 1) % walkRight.length;
    }

    public void animateAttack() {
        if(animationTimer.millisElapsed() < 50) {
            return;
        }
        animationTimer.mark();

        if(facing.equals("right")) {
            setImage(attackRight[attackIndex]);
        } else {
            setImage(attackLeft[attackIndex]);
        }

        if(attackIndex == 7) {
            Arrow arrow = new Arrow(facing);
            getWorld().addObject(arrow, getX(), getY());
        }

        attackIndex++;

        if(attackIndex >= attackRight.length) {
            isAttacking = false;
            attackIndex = 0;
        }
    }

    public void gravity() {
        setLocation(getX(), getY() + jumpHeight);
        jumpHeight += gravity;
        int feetY = getY() + (getImage().getHeight() / 2) - feetOffset;
        int floorY = getWorld().getHeight();

        if (feetY >= floorY) {
            setLocation(getX(), floorY - ((getImage().getHeight() / 2) - feetOffset));
            jumpHeight = 0;
            isOnGround = true;
        } else if (!isOnGround) {
            isOnGround = false;
        }
    }

    public void checkGroundCollision() {
        if (jumpHeight >= 0) {
            int checkOffset = (getImage().getHeight() / 2) - feetOffset + 5;
            Actor ground = getOneObjectAtOffset(0, checkOffset, Platform.class);

            if (ground != null) {
                int groundTopY = ground.getY() - ground.getImage().getHeight() / 2;
                int feetY = getY() + (getImage().getHeight() / 2) - feetOffset;

                if (feetY > groundTopY) {
                    setLocation(getX(), groundTopY - ((getImage().getHeight() / 2) - feetOffset));
                    jumpHeight = 0;
                    isOnGround = true;
                }
            } else {
                if (getY() + (getImage().getHeight() / 2) - feetOffset < getWorld().getHeight()) {
                    isOnGround = false;
                }
            }
        }
    }

    public void jump() {
        if (isOnGround) {
            jumpHeight = -20;
            isOnGround = false;
        }
    }

    public void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public void takeDamage() {
        health--;
        if (healthBar != null) {
            healthBar.updateHealth(health);
        }

        if (health <= 0 && !isDead) {
            isDead = true;
            deathIndex = 0;
            animationTimer.mark();
        }
    }

    public void animateHurt() {
        if (hurtTimer.millisElapsed() < 100) {
            return;
        }
        hurtTimer.mark();

        if (facing.equals("right")) {
            setImage(hurtRight[hurtIndex]);
        } else {
            setImage(hurtLeft[hurtIndex]);
        }

        hurtIndex++;
        if (hurtIndex >= hurtRight.length) {
            isHurt = false;
            hurtIndex = 0;
        }
    }

    public void animateDeath() {
        if (animationTimer.millisElapsed() < 150) {
            return;
        }

        if (facing.equals("right")) {
            if (deathIndex < deathRight.length) {
                setImage(deathRight[deathIndex]);
                deathIndex++;
                animationTimer.mark();
            }
        } else {
            if (deathIndex < deathLeft.length) {
                setImage(deathLeft[deathIndex]);
                deathIndex++;
                animationTimer.mark();
            }
        }

        if (deathIndex >= deathRight.length) {
            Greenfoot.stop();
        }
    }
}
