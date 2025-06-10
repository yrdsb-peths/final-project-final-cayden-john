import greenfoot.*;

public class MyWorld extends World {
    public MyWorld() {
        super(960, 540, 1);
        Platform platform = new Platform(125, 50);
        addObject(platform, 230, 375);
        Platform platform1 = new Platform(960,40);
        addObject(platform1, 480, getHeight()-10);
        prepare();
        
        Martin martin = new Martin();
        addObject(martin,100,482);

        setBackground("images/Battleground2.png");
        EvilEdd evilEdd = new EvilEdd();
        addObject(evilEdd, 480, 158);
        
        
        HealthBar healthBar = new HealthBar(12);
        addObject(healthBar, 130, 525); 
        

        EnemyHealthBar eddHealthBar = new EnemyHealthBar(57);
        addObject(eddHealthBar, 500, 30); 

        
        evilEdd.setHealthBar(eddHealthBar);
        martin.setHealthBar(healthBar);
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Platform platform2 = new Platform(125, 50);
        addObject(platform2,711,375);
    }
}
