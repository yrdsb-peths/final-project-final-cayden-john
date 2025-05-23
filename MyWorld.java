import greenfoot.*;

public class MyWorld extends World {
    public MyWorld() {
        super(1127, 557, 1);
        Martin martin = new Martin();
        addObject(martin, 100, 500);
        setBackground("images/Cave.png");
    }
}
