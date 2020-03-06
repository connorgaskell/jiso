import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class Display extends JPanel {

    private final int tileCount = 16;
    private final int tileScale = 1;

    private float xOffset;
    private float yOffset;

    private float zoom = 4f;
    private float zoomScale = 10;

    public Display() {
        MouseInput mouseInput = new MouseInput(this);

        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);

        xOffset = -tileCount / 3;
        yOffset = -tileCount / 3;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);

        List<float[]> quads = new ArrayList<>();
        List<Boolean> isTile = new ArrayList<>();

        /*
         * Create the Grid
         */
        for (int y = 0; y < tileCount; y++) {
            for (int x = 0; x < tileCount; x++) {
                float[] point = new float[3];
                point[0] = (x) + yOffset;
                point[1] = 0.0f;
                point[2] = (y) + xOffset;

                quads.add(point);
                isTile.add(Boolean.TRUE);
            }
        }

        /*
         * Render Grid as Isometric
         */
        Matrix4 iso = createIsometricMatrix((tileScale * zoom) * (this.getWidth() / 100), (this.getWidth() / 2) + xOffset, (this.getHeight() / 2) + yOffset);

        final float[] view = new float[3];

        final float[] xRel = new float[] { -0.5f, -0.5f, +0.5f, +0.5f };
        final float[] yRel = new float[] { -0.5f, +0.5f, +0.5f, -0.5f };

        /*
         * Draw the Grid to screen
         */
        for (int i = 0; i < quads.size(); i++) {
            g2d.setColor(Color.black);

            if (!isTile.get(i).booleanValue()) {
                float[] model = quads.get(i).clone();

                iso.transform(model, view);

                //g2d.fillOval((int) view[0] - 2, (int) view[1] - 2, 5, 5);
            } else {
                Polygon p = new Polygon();
                for (int k = 0; k < 4; k++) {
                    float[] model = quads.get(i).clone();

                    // translate to tile corners in model space
                    model[0] += xRel[k] * 0.95F;
                    model[2] += yRel[k] * 0.95F;

                    iso.transform(model, view);

                    p.addPoint((int) view[0], (int) view[1]);
                }

                g2d.drawPolygon(p);
            }
        }

    }

    private Matrix4 createIsometricMatrix(float scale, float x, float y) {
        // --> 1.0, 0.0, -1.0, 0.0 // x
        // --> 0.5, 2.0, 0.5, 0.0 // y
        // --> 0.0, -0.05, 0.0, 0.0 // depth
        // --> 0.0, 0.0, 0.0, 1.0 // [nothing]

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
        repaint();
    }

}
