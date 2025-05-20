import greenfoot.*;

public class MyWorld extends World {
    public MyWorld() {
        super(1152, 500, 1);
        Martin martin = new Martin();
        addObject(martin, 300, 200);
    }
}
