package gdd.sprite;

import java.awt.Image;

abstract public class Sprite {

    protected boolean visible;
    protected Image image;
    protected boolean dying;
    protected int visibleFrames = 10;

    protected int x;
    protected int y;
    protected int dx;

    public Sprite() {
        visible = true;
    }

    abstract public void act();

    public boolean collidesWith(Sprite other) {
        // if (other == null || !this.isVisible() || !other.isVisible()) {
        // return false;
        // }
        // return this.getX() < other.getX() + other.getImage().getWidth(null)
        // && this.getX() + this.getImage().getWidth(null) > other.getX()
        // && this.getY() < other.getY() + other.getImage().getHeight(null)
        // && this.getY() + this.getImage().getHeight(null) > other.getY();
        if (other == null || !this.isVisible() || !other.isVisible()) {
            return false;
        }
        int margin = 5; // expand hitbox by 10 pixels on each side

        int thisLeft = this.getX() - margin;
        int thisRight = this.getX() + this.getImage().getWidth(null) + margin;
        int thisTop = this.getY() - margin;
        int thisBottom = this.getY() + this.getImage().getHeight(null) + margin;

        int otherLeft = other.getX() - margin;
        int otherRight = other.getX() + other.getImage().getWidth(null) + margin;
        int otherTop = other.getY() - margin;
        int otherBottom = other.getY() + other.getImage().getHeight(null) + margin;

        return thisLeft < otherRight
                && thisRight > otherLeft
                && thisTop < otherBottom
                && thisBottom > otherTop;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void visibleCountDown() {
        if (visibleFrames > 0) {
            visibleFrames--;
        } else {
            visible = false;
        }
    }

    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }
}
