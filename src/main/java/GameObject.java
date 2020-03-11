import java.awt.image.BufferedImage;

public class GameObject {

    private BufferedImage objectImage;
    private boolean canPlaceOn;
    private int heightOffset;

    public GameObject(BufferedImage objectImage, boolean canPlaceOn, int heightOffset) {
        this.objectImage = objectImage;
        this.canPlaceOn = canPlaceOn;
        this.heightOffset = heightOffset;
    }

    public void setObjectImage(BufferedImage image) {
        this.objectImage = image;
    }

    public void setCanPlaceOn(boolean canPlaceOn) {
        this.canPlaceOn = canPlaceOn;
    }

    public void setHeightOffset(int heightOffset) {
        this.heightOffset = heightOffset;
    }

    public BufferedImage getObjectImage() {
        return objectImage;
    }

    public boolean getCanPlaceOn() {
        return canPlaceOn;
    }

    public int getHeightOffset() {
        return getHeightOffset();
    }

}
