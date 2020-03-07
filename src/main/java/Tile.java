import java.awt.*;

public class Tile {

    private float[] quads;
    private boolean isTile;
    private Color color;

    public Tile(float[] quads, boolean isTile) {
        this.quads = quads;
        this.isTile = isTile;
        color = Color.WHITE;
    }

    public void setQuads(float[] quads) {
        this.quads = quads;
    }

    public void setIsTile(boolean isTile) {
        this.isTile = isTile;
    }

    public boolean getIsTile() {
        return isTile;
    }

    public float[] getQuads() {
        return quads;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
