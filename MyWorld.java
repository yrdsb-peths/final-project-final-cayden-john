import greenfoot.*;

public class MyWorld extends World {
    public MyWorld() {
        super(1127, 557, 1);
        Martin martin = new Martin();
        addObject(martin, 300, 200);
        setBackground("images/Cave.png");
    }
}
