package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class ZigZagEnemy extends Enemy {
    private int zigzagDirection = 1; // 1 for right, -1 for left
    private int zigzagStep = 0;
    private static final int ZIGZAG_WIDTH = 60; // pixels to move before switching direction
    private int startX;
    private int animationFrame = 0;
    private static final int ANIMATION_SPEED = 10; // frames per image

    public ZigZagEnemy(int x, int y) {
        super(x, y);
        this.startX = x;
        setZigZagEnemyImage();
    }

    private void setZigZagEnemyImage() {
        String imgPath;
        if (animationFrame < ANIMATION_SPEED) {
            imgPath = IMG_ENEMY_RED;
        } else {
            imgPath = IMG_ENEMY_RED_2;
        }
        var ii = new ImageIcon(imgPath);
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
    }

    @Override
    public void act(int direction) {
        // Move down as usual
        this.y += 3;
        // Zig-zag left and right
        this.x += zigzagDirection * 2; // speed of zig-zag
        zigzagStep += Math.abs(zigzagDirection * 2);
        if (zigzagStep >= ZIGZAG_WIDTH) {
            zigzagDirection *= -1;
            zigzagStep = 0;
        }
        // Animate zigzag enemy
        animationFrame = (animationFrame + 1) % (ANIMATION_SPEED * 2);
        setZigZagEnemyImage();


        if (this.y >= 700){
            this.setDying(true);
        }
    }

    @Override
    public void act() {
        act(0);
    }
} 