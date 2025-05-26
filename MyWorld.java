import greenfoot.*;

public class MyWorld extends World {
    public MyWorld() {
        super(960, 540, 1);
        Martin martin = new Martin();
        addObject(martin, 100, 500);
        setBackground("images/Battleground2.png");
        WiseFarmer wiseFarmer = new WiseFarmer();
        addObject(wiseFarmer, 700, 482);
    }
}
