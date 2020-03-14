package engine.ui;

import engine.vector.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Image extends UIComponent {

    private BufferedImage image;
    private Vector2 size;
    private Vector2 bounds;
    private Anchor anchor;

    public Image(BufferedImage image, Vector2 size, Anchor anchor, Vector2 position, String name) {
        super(name, position);
        this.image = image;
        this.size = size;
        this.bounds = size;
        this.anchor = anchor;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getBounds() {
        return bounds;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public boolean checkIntersection(int mouseX, int mouseY) {
        if(new Rectangle(position.x, position.y, bounds.x, bounds.y).contains(new Point(mouseX, mouseY))) {
            return true;
        }
        return false;
    }

    public void onMouseOver() {

    }

}
