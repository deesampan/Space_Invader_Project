package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class BossEnemy extends Enemy {
    private int health;
    private int animationFrame = 0;
    private static final int ANIMATION_SPEED = 20; // slower animation
    private int direction = 1; // 1 for right, -1 for left
    private int shootCooldown = 0;
    private static final int SHOOT_INTERVAL = 40; // frames between shots

    public BossEnemy(int x, int y) {
        super(x, y);
        this.health = 3; // Boss has 3 HP
        setBossImage();
    }

    private void setBossImage() {
        var ii = new ImageIcon(IMG_ENEMY_BOSS); // Use ufo_2.png for boss
        var scaledImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR * 2, // Boss is bigger
            ii.getIconHeight() * SCALE_FACTOR * 2,
            java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);


        this.x = x - (scaledImage.getWidth(null) / 2);
        this.y = y - (scaledImage.getHeight(null) / 2);
    }

    @Override
    public void act(int ignored) {
        // Move horizontally
        x += direction * 3; // Boss speed
        if (x <= BORDER_LEFT) {
            x = BORDER_LEFT;
            direction = 1;
        } else if (x + getImage().getWidth(null) >= BOARD_WIDTH - BORDER_RIGHT) {
            x = BOARD_WIDTH - BORDER_RIGHT - getImage().getWidth(null);
            direction = -1;
        }
        // Animate boss spaceship
        animationFrame = (animationFrame + 1) % (ANIMATION_SPEED * 2);
        setBossImage();
        // Handle shooting
        if (shootCooldown > 0) shootCooldown--;
    }

    public boolean canShoot() {
        return shootCooldown == 0;
    }
    public void resetShootCooldown() {
        shootCooldown = SHOOT_INTERVAL;
    }

    public gdd.sprite.Shot createShot() {
        // Shot appears from the center bottom of the boss
        int shotX = x + getImage().getWidth(null) / 2;
        int shotY = y + getImage().getHeight(null);
        return new gdd.sprite.Shot(shotX, shotY, 1); // 1 = downward
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            setDying(true);
        }
    }

    public int getHealth() {
        return health;
    }

    @Override
    public int getX() {
        return x - 50; // expand hitbox left
    }
    @Override
    public int getY() {
        return y - 50; // expand hitbox up
    }
    public int getWidth() {
        return getImage().getWidth(null) + 40; // expand width
    }
    public int getHeight() {
        return getImage().getHeight(null) + 40; // expand height
    }
} 