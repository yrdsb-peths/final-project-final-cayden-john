import greenfoot.*;

public class HealthBar extends Actor {
    private int maxHealth;
    private int currentHealth;
    private int barWidth = 200; 
    private int barHeight = 20;

    public HealthBar(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        updateImage();
    }

    public void updateHealth(int health) {
        this.currentHealth = Math.max(0, health);
        updateImage();
    }

    private void updateImage() {
        GreenfootImage img = new GreenfootImage(barWidth, barHeight);
        double healthPercent = (double) currentHealth / maxHealth;

        if (healthPercent > 0.6) {
            img.setColor(Color.GREEN);
        } else if (healthPercent > 0.3) {
            img.setColor(Color.YELLOW);
        } else {
            img.setColor(Color.RED);
        }

        img.fillRect(0, 0, (int)(barWidth * healthPercent), barHeight);
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, barWidth - 1, barHeight - 1);
        setImage(img);
    }
}
