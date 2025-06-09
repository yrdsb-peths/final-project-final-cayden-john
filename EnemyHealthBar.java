import greenfoot.*;

public class EnemyHealthBar extends Actor {
    private int maxHealth;
    private int currentHealth;
    private int barWidth = 500;
    private int barHeight = 20;

    public EnemyHealthBar(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        updateImage();
    }

    public void updateHealth(int newHealth) {
        currentHealth = Math.max(0, newHealth);
        updateImage();
    }

    private void updateImage() {
        GreenfootImage img = new GreenfootImage(barWidth, barHeight);
        double percent = (double) currentHealth / maxHealth;

        if (percent > 0.6) {
            img.setColor(Color.GREEN);
        } else if (percent > 0.3) {
            img.setColor(Color.YELLOW);
        } else {
            img.setColor(Color.RED);
        }

        img.fillRect(0, 0, (int)(barWidth * percent), barHeight);
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, barWidth - 1, barHeight - 1);
        setImage(img);
    }
}
