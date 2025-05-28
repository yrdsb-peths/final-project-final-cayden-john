import greenfoot.*;

public class MyWorld extends World {
    public MyWorld() {
        super(960, 540, 1);
        Martin martin = new Martin();
        addObject(martin,100,500);

        setBackground("images/Battleground2.png");
        LoserDrill loserDrill = new LoserDrill();
        addObject(loserDrill,300, 200);
        //WiseFarmer wiseFarmer = new WiseFarmer();
        //addObject(wiseFarmer, 700, 482);
        //EvilEdd evilEdd = new EvilEdd();
        //addObject(evilEdd, 480, 250);

        Platform platform = new Platform();
        addObject(platform, 230, 375);
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        Platform platform2 = new Platform();
        addObject(platform2,711,375);
    }
}
