import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Arrow extends Actor
{
    GreenfootImage arrow = new GreenfootImage("images/Arrow1.png");;
    private int speed = 10;
    private String direction;
    
    public void act()
    {
        if(direction.equals("right")) {
            setLocation(getX() + speed, getY());
        } else {
            setLocation(getX() - speed, getY());
        }
        
        if(getX() > getWorld().getWidth() - 5 || getX() < 5) {
            getWorld().removeObject(this);
        }
    }
    
    public Arrow(String direction) {
        this.direction = direction;
        arrow.scale(82, 82);
        
        if(direction.equals("left")) {
            arrow.mirrorHorizontally();
        }
        
        setImage(arrow);
    }
}
