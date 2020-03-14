package engine.objects;

public class Camera {

    private int numTiles;
    private float xOffset, yOffset;
    private float zoom = 1f, zoomScale = 10;

    public Camera(int numTiles) {
        this.numTiles = numTiles;
    }

    public void setZoom(float value) {
        value = value / zoomScale;
        zoom = zoom <= 1 && value < 0 ? 1 : zoom + value;
    }

    public void setOffset(float x, float y) {
        setOffset(x, y, 250);
    }

    public void setOffset(float x, float y, float max) {
        max = (max * zoom) * (numTiles / 10);
        xOffset = xOffset >= max * 2 && x > 0 ? max * 2 : xOffset <= -max * 2 && x < 0 ? -max * 2 : xOffset + x;
        yOffset = yOffset >= max && y > 0 ? max : yOffset <= -max && y < 0 ? -max : yOffset + y;
    }

    public float getZoom() {
        return zoom;
    }

    public float getZoomScale() {
        return  zoomScale;
    }

    public float getOffsetX() {
        return xOffset;
    }

    public float getOffsetY() {
        return yOffset;
    }

    public void dragOffset(float distanceX, float distanceY, float dragSpeed) {
        setOffset(distanceX * dragSpeed, distanceY * dragSpeed);
    }

}
