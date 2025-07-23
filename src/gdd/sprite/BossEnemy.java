package gdd.sprite;

import static gdd.Global.*;
import static gdd.Global.BOARD_WIDTH;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Graphics;


public class BossEnemy extends Enemy {
    private int health;
    private int animationFrame = 0;
    private static final int ANIMATION_SPEED = 20; // slower animation
    private int direction = 1; // 1 for right, -1 for left
    private int shootCooldown = 0;
    private static final int SHOOT_INTERVAL = 30; // frames between shots
    private static final int HITBOX_MARGIN = 40; // Amount to expand hitbox (centered)
    private int spawnX, spawnY; // Store initial spawn for correct image placement

    public BossEnemy(int x, int y) {
        super(x - 200, y);
        this.health = 3; // Boss has 3 HP
        this.spawnX = x;
        this.spawnY = y;
        setBossImage(IMG_ENEMY_BOSS);
    }

    private void setBossImage(String imgPath) {
        var ii = new ImageIcon(imgPath); // Use ufo_2.png for boss
        var scaledImage = ii.getImage().getScaledInstance(
            ii.getIconWidth() * SCALE_FACTOR * 2, // Boss is bigger
            ii.getIconHeight() * SCALE_FACTOR * 2,
            java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
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
        if (animationFrame < ANIMATION_SPEED) {
            setBossImage(IMG_ENEMY_BOSS);
        } else {
            setBossImage(IMG_ENEMY_BOSS_2);
        }

        // Handle shooting cooldown
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

    public Rectangle getHitbox() {
        int imgWidth = getImage().getWidth(null);
        int imgHeight = getImage().getHeight(null);
        int hitboxWidth = imgWidth + HITBOX_MARGIN;
        int hitboxHeight = imgHeight + HITBOX_MARGIN;

        // Center the hitbox around the boss image
        int hitboxX = x - (hitboxWidth - imgWidth) / 2;
        int hitboxY = y - (hitboxHeight - imgHeight) / 2;

        return new Rectangle(hitboxX, hitboxY, hitboxWidth, hitboxHeight);
    }


}
