package gdd.sprite;

import static gdd.Global.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Player extends Sprite {

    private static final int START_X = 270;
    private static final int START_Y = 540;
    private int width;
    private int currentSpeed = 2;
    private int shotLevel = 1;

    private Rectangle bounds = new Rectangle(175, 135, 17, 32);

    private int animationFrame = 0;
    private static final int ANIMATION_SPEED = 10; // frames per image

    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        setPlayerImage(IMG_PLAYER);
        setX(START_X);
        setY(START_Y);
    }

    private void setPlayerImage(String imgPath) {
        var ii = new ImageIcon(imgPath);
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    public int getSpeed() {
        return currentSpeed;
    }

    public int setSpeed(int speed) {
        if (speed < 1) {
            speed = 1; // Ensure speed is at least 1
        }
        this.currentSpeed = speed;
        return currentSpeed;
    }

    public int getShotLevel() {
        return shotLevel;
    }

    public void setShotLevel(int shotLevel) {
        if (shotLevel < 1) {
            shotLevel = 1; // Ensure shot level is at least 1
        }
        this.shotLevel = shotLevel;
    }

    @Override
    public void act() {
        x += dx;

        // Animate spaceship
        animationFrame = (animationFrame + 1) % (ANIMATION_SPEED * 2);
        if (animationFrame < ANIMATION_SPEED) {
            setPlayerImage(IMG_PLAYER);
        } else {
            setPlayerImage(IMG_PLAYER_2);
        }

        if (x <= 2) {
            x = 2;
        }

        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -currentSpeed;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = currentSpeed;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }
}
