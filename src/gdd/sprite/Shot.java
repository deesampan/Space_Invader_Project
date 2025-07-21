package gdd.sprite;

import static gdd.Global.*;
import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private static final int H_SPACE = 20;
    private static final int V_SPACE = 1;
    private int direction = -1; // -1 = up (player), 1 = down (enemy)

    public Shot() {
    }

    public Shot(int x, int y) {
        this(x, y, -1); // Default: player shot
    }

    public Shot(int x, int y, int direction) {
        this.direction = direction;
        initShot(x, y);
    }

    private void initShot(int x, int y) {

        var ii = new ImageIcon(IMG_SHOT);

        // Scale the image to use the global scaling factor
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * SCALE_FACTOR,
                ii.getIconHeight() * SCALE_FACTOR, 
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);

        setX(x + H_SPACE);
        setY(y - V_SPACE);
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public void act() {
        // Move shot up or down
        if (direction == -1) {
            y -= 20;
        } else {
            y += 10;
        }
    }
}
