import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Display extends JPanel {

    private int tileColumnOffset, tileRowOffset;
    private int originX, originY;
    private int xTiles = 10, yTiles = 10;
    private int selectedTileX = -1, selectedTileY = -1;

    private float xOffset, yOffset;
    private float zoom = 1f, zoomScale = 10;

    private MouseInput mouseInput;
    private BufferedImage floorImage, highlightImage;

    public Display() {
        mouseInput = new MouseInput(this);

        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);

        setBackground(Color.darkGray);

        try {
            floorImage = ImageIO.read(this.getClass().getResource("FloorTile.png"));
            highlightImage = ImageIO.read(this.getClass().getResource("HighlightTile.png"));
        } catch (IOException e) { }

        new Timer(1000 / 60, (ActionEvent e) -> {
            repaint();
        }).start();
    }

    private void createTileMap(Graphics g) {
        tileColumnOffset = (int)(64 * zoom);
        tileRowOffset = tileColumnOffset / 2;

        originX = (int)((getWidth() / 2 - xTiles * tileColumnOffset / 2) + xOffset);
        originY = (int)((getHeight() / 2) + yOffset);

        int mouseX = mouseInput.x - tileColumnOffset / 2 - originX;
        int mouseY = mouseInput.y - tileRowOffset / 2 - originY;

        selectedTileY = (int)Math.round((mouseY + mouseX * 0.5) / tileRowOffset);
        selectedTileX = -(int)Math.round((mouseY - mouseX * 0.5) / tileRowOffset);

        for(int x = xTiles - 1; x >= 0; x--) {
            for(int y = 0; y < yTiles; y++) {
                drawTile(g, x, y, Color.white);
                drawImage(g, x, y, floorImage);
            }
        }

        if(selectedTileX >= 0 && selectedTileX < xTiles && selectedTileY >= 0 && selectedTileY < yTiles) drawImage(g, selectedTileX, selectedTileY, 4, highlightImage);
    }

    private void drawTile(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        int offX = x * tileColumnOffset / 2 + y * tileColumnOffset / 2 + originX;
        int offY = y * tileRowOffset / 2 - x * tileRowOffset / 2 + originY;

        g.drawLine(offX, offY + tileRowOffset / 2, offX + tileColumnOffset / 2, offY);
        g.drawLine(offX + tileColumnOffset / 2, offY, offX + tileColumnOffset, offY + tileRowOffset / 2);
        g.drawLine(offX + tileColumnOffset, offY + tileRowOffset / 2, offX + tileColumnOffset / 2, offY + tileRowOffset);
        g.drawLine(offX + tileColumnOffset / 2, offY + tileRowOffset, offX, offY + tileRowOffset / 2);
    }

    private void drawImage(Graphics g, int x, int y, BufferedImage image) {
        drawImage(g, x, y, 0, image);
    }

    private void drawImage(Graphics g, int x, int y, int heightOffset, BufferedImage image) {
        int offX = x * tileColumnOffset / 2 + y * tileColumnOffset / 2 + originX;
        int offY = y * tileRowOffset / 2 - x * tileRowOffset / 2 + originY;

        g.drawImage(image, offX, offY - (int) ((28 + heightOffset) * zoom), (int) (image.getWidth() * zoom), (int) (image.getHeight() * zoom), null);
    }

    public void setZoom(float value) {
        value = value / zoomScale;
        zoom = zoom <= 1 && value < 0 ? 1 : zoom + value;
    }

    public void setOffset(float x, float y) {
        setOffset(x, y, 250);
    }

    public void setOffset(float x, float y, float max) {
        max = max * zoom;
        xOffset = xOffset >= max && x > 0 ? max : xOffset <= -max && x < 0 ? -max : xOffset + x;
        yOffset = yOffset >= max && y > 0 ? max : yOffset <= -max && y < 0 ? -max : yOffset + y;
    }

    public void dragOffset(float distanceX, float distanceY, float dragSpeed) {
        setOffset(distanceX * dragSpeed, distanceY * dragSpeed);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        createTileMap(g);
        dragOffset(mouseInput.dragDistanceX, mouseInput.dragDistanceY, 2.0f);
    }

}
