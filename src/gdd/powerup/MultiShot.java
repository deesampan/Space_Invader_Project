package gdd.powerup;

import static gdd.Global.*;
import gdd.sprite.Player;
import javax.swing.ImageIcon;

public class MultiShot extends PowerUp {

    public MultiShot(int x, int y) {
        super(x, y);
        // Set image
        var ii = new ImageIcon(IMG_POWERUP_MULTISHOT);
        var scaledImage = ii.getImage().getScaledInstance(ii.getIconWidth() * 1,
                ii.getIconHeight() * 1,
                java.awt.Image.SCALE_SMOOTH);
        setImage(scaledImage);
        // Center the power-up horizontally
        this.x = x - (scaledImage.getWidth(null) / 2);
        this.y = y - (scaledImage.getHeight(null) / 2);
    }

    @Override
    public void act() {
        // MultiShot specific behavior can be added here
        // For now, it just moves down the screen
        this.y += 2; // Move down by 2 pixel each frame
    }

    @Override
    public void upgrade(Player player) {
        // Upgrade the player with multi-shot ability
        if (player.getShotLevel() >= 4) {
            this.die();
            return; // Do not upgrade if already at max shot level
        }
        player.setShotLevel(player.getShotLevel() + 1); // Increase player's shot level by 1
        this.die(); // Remove the power-up after use
    }

}
