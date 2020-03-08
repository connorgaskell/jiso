import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Display extends JPanel {

    private final int tileCount = 32;
    private final int tileScale = 1;

    private float xOffset;
    private float yOffset;

    private float zoom = 4f;
    private float zoomScale = 10;

    private List<Tile> tiles = new ArrayList<>();
    private Matrix4 iso;

    private final float[] view = new float[3];
    private final float[] xRel = new float[] { -0.5f, -0.5f, +0.5f, +0.5f };
    private final float[] yRel = new float[] { -0.5f, +0.5f, +0.5f, -0.5f };

    public Display() {
        MouseInput mouseInput = new MouseInput(this);

        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);

        xOffset = -tileCount / 3;
        yOffset = -tileCount / 3;

        setBackground(Color.BLACK);
        createTileGrid();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        /*
         * Render Grid as Isometric
         */
        iso = createIsometricMatrix((tileScale * zoom) * (float)(Math.floor(this.getWidth() / 100)), ((float)Math.floor(this.getWidth() / 2)) + xOffset, ((float)Math.floor(this.getHeight() / 2)) + yOffset);

        /*
         * Draw the Grid to screen
         */
        for(int i = 0; i < tiles.size(); i++) {
            g.setColor(Color.black);
            if(!tiles.get(i).getIsTile()) {
                float[] model = tiles.get(i).getQuads();
                iso.transform(model, view);
            } else {
                Polygon p = new Polygon();
                for (int k = 0; k < 4; k++) {
                    float[] model = tiles.get(i).getQuads().clone();

                    model[0] += xRel[k];
                    model[2] += yRel[k];

                    iso.transform(model, view);

                    p.addPoint((int) view[0], (int) view[1]);
                }

                g.setColor(tiles.get(i).getColor());
                g.fillPolygon(p);

                g.setColor(Color.black);
                g.drawPolygon(p);
            }
        }
    }

    private void createTileGrid() {
        /*
         * Create the Grid
         */
        for (int y = 0; y < tileCount; y++) {
            for (int x = 0; x < tileCount; x++) {
                float[] point = new float[3];
                point[0] = (x) + yOffset;
                point[1] = 0.0f;
                point[2] = (y) + xOffset;

                tiles.add(new Tile(point, Boolean.TRUE));
            }
        }
    }

    private Matrix4 createIsometricMatrix(float scale, float x, float y) {
        Matrix4 iso = new Matrix4();
        iso.m00 = iso.m33 = 1.0f * scale;
        iso.m10 = iso.m12 = 0.5f * scale;
        iso.m11 = 2.0f * scale;
        iso.m02 = -1.0f * scale;
        iso.m21 = -0.05f * scale;
        iso.m03 = x;
        iso.m13 = y;
        return iso;
    }

    public void setZoom(float value) {
        value = value / zoomScale;
        zoom = zoom <= 1 && value < 0 ? 1 : zoom + value;
    }

    public void setOffset(float x, float y) {
        xOffset = xOffset + x;
        yOffset = yOffset + y;
    }

}
