import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameOver extends World
{

    /**
     * Constructor for objects of class GameOver.
     * 
     */
    public GameOver()
    {    
        super(960, 540, 1); 
        setBackground("images/Game Over.png");
        Label label = new Label("Press <space> to Try Again", 40);
        addObject(label,480, 350);
    }

    public void act() {
        if(Greenfoot.isKeyDown("space")) {
            TitleScreen reset = new TitleScreen();
            Greenfoot.setWorld(reset);
        }
    }
}
