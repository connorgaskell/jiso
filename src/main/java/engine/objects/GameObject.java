package engine.objects;

import engine.vector.Vector2;

import java.awt.image.BufferedImage;

public class GameObject {

    private BufferedImage objectImage;
    private boolean canPlaceOn;
    private boolean isWalkable;
    private int heightOffset;
    private Vector2 position = new Vector2();

    public GameObject(Vector2 position, BufferedImage objectImage, boolean canPlaceOn, boolean isWalkable, int heightOffset) {
        this.objectImage = objectImage;
        this.canPlaceOn = canPlaceOn;
        this.heightOffset = heightOffset;
        this.position = position;
    }

    public void setPosition(Vector2 position) { this.position = position; }

    public void setObjectImage(BufferedImage image) {
        this.objectImage = image;
    }

    public void setCanPlaceOn(boolean canPlaceOn) {
        this.canPlaceOn = canPlaceOn;
    }

    public void setHeightOffset(int heightOffset) {
        this.heightOffset = heightOffset;
    }

    public Vector2 getPosition() { return position; }

    public BufferedImage getObjectImage() {
        return objectImage;
    }

    public boolean getCanPlaceOn() {
        return canPlaceOn;
    }

    public int getHeightOffset() {
        return heightOffset;
    }

}
