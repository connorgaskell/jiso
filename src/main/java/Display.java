import javax.swing.*;
import java.awt.*;

public class Display extends JPanel {

    private int tileColumnOffset, tileRowOffset;
    private int originX, originY;
    private int xTiles = 10, yTiles = 10;
    private int selectedTileX = -1, selectedTileY = -1;

    private float xOffset;
    private float yOffset;

    private float zoom = 1f;
    private float zoomScale = 10;

    private MouseInput mouseInput;

    public Display() {
        mouseInput = new MouseInput(this);

        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);

        setBackground(Color.BLACK);
    }

    private void createTileMap(Graphics g) {
        tileColumnOffset = (int)(64 * zoom);
        tileRowOffset = tileColumnOffset / 2;

        originX = (int)((getWidth() / 2 - xTiles * tileColumnOffset / 2) + xOffset);
        originY = (int)((getHeight() / 2) + yOffset);

        mouseInput.x = mouseInput.x - tileColumnOffset / 2 - originX;
        mouseInput.y = mouseInput.y - tileRowOffset / 2 - originY;

        selectedTileY = (int)Math.round((mouseInput.y + mouseInput.x * 0.5) / tileRowOffset);
        selectedTileX = -(int)Math.round((mouseInput.y - mouseInput.x * 0.5) / tileRowOffset);

        System.out.println(selectedTileX + " | " + selectedTileY);

        for(int x = 0; x < xTiles; x++) {
            for(int y = 0; y < yTiles; y++) {
                drawTile(g, x, y, Color.white);
            }
        }

        if(selectedTileX >= 0 && selectedTileX < xTiles && selectedTileY >= 0 && selectedTileY < yTiles) drawTile(g, selectedTileX, selectedTileY, Color.yellow);
    }

    private void drawTile(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        int offX = x * tileColumnOffset / 2 + y * tileColumnOffset / 2 + originX;
        int offY = y * tileRowOffset / 2 - x * tileRowOffset / 2 + originY;

        if(x == selectedTileX && y == selectedTileY) {
            g.setColor(Color.yellow);
        } else {
            g.setColor(Color.white);
        }

        g.drawLine(offX, offY + tileRowOffset / 2, offX + tileColumnOffset / 2, offY);
        g.drawLine(offX + tileColumnOffset / 2, offY, offX + tileColumnOffset, offY + tileRowOffset / 2);
        g.drawLine(offX + tileColumnOffset, offY + tileRowOffset / 2, offX + tileColumnOffset / 2, offY + tileRowOffset);
        g.drawLine(offX + tileColumnOffset / 2, offY + tileRowOffset, offX, offY + tileRowOffset / 2);
    }

    public void setZoom(float value) {
        value = value / zoomScale;
        zoom = zoom <= 1 && value < 0 ? 1 : zoom + value;
    }

    public void setOffset(float x, float y) {
        xOffset = xOffset + x;
        yOffset = yOffset + y;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        createTileMap(g);
    }

}
