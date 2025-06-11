import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends World
{
    public TitleScreen()
    {    
        super(960, 540, 1); 
        
        setBackground("images/Title Screen.png");
        
        prepare();
    }
    
    public void act() {
        if(Greenfoot.isKeyDown("space")) {
            MyWorld gameWorld = new MyWorld();
            Greenfoot.setWorld(gameWorld);
        }
    }
    
    private void prepare()
    {
        Label label = new Label("Use \u2190 \u2191 \u2192 or W A D to move", 40);
        addObject(label,310,202);
        label.setLocation(327,202);
        Label label2 = new Label("Press <space> to Start and Shoot", 40);
        addObject(label2,289,287);
        label.setLocation(490,287);
        label2.setLocation(417,403);
        label2.setLocation(470,406);
        label.setLocation(465,306);
        label.setLocation(486,286);
        label.setLocation(469,308);
        label.setLocation(480,297);
        label.setLocation(476,297);
        label2.setLocation(484,387);
        label2.setLocation(452,399);
        label2.setLocation(485,394);
        label2.setLocation(465,395);
    }
}
