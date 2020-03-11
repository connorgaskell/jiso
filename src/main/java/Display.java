import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Display extends JPanel {

    private int tileColumnOffset, tileRowOffset;
    private int originX, originY;
    private int xTiles = 32, yTiles = 32;
    public int selectedTileX = -1, selectedTileY = -1;

    private float xOffset, yOffset;
    private float zoom = 1f, zoomScale = 10;

    private MouseInput mouseInput;
    private BufferedImage objectImage, floorImage, highlightImage;

    private ArrayList<Tile> tiles = new ArrayList<>();

    public Display() {
        mouseInput = new MouseInput(this);

        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);

        setDoubleBuffered(true);
        setBackground(Color.darkGray);

        try {
            objectImage = ImageIO.read(this.getClass().getResource("TileObject.png"));
            floorImage = ImageIO.read(this.getClass().getResource("FloorTile.png"));
            highlightImage = ImageIO.read(this.getClass().getResource("HighlightTile.png"));
        } catch (IOException e) { }

        createDefaultMap();

        new Timer(1000 / 60, (ActionEvent e) -> {
            repaint();
        }).start();
    }

    private void createDefaultMap() {
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                tiles.add(new Tile(x, y, floorImage));
            }
        }

        tiles.get(0).addGameObject(new GameObject(objectImage, false, 0));

        sortTiles();
    }

    public void addTile(int x, int y) {
        tiles.add(new Tile(x, y, floorImage));
        sortTiles();
    }

    public void addObject(int x, int y) {
        getTile(x, y).addGameObject(new GameObject(objectImage, false, 0));
    }

    private Tile getTile(int x, int y) {
        return tiles.stream().filter(var -> var.getX() == x && var.getY() == y).collect(Collectors.toList()).get(0);
    }

    private void sortTiles() {
        tiles.sort((Tile t1, Tile t2) -> (Integer.compare(t2.getX(), t1.getX())));
        tiles.sort(Comparator.comparingInt(Tile::getY));
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

        System.out.println(selectedTileX + " | " + selectedTileY);

        try {
            for(int i = 0; i < tiles.size(); i++) {
                drawFloor(g, tiles.get(i).getX(), tiles.get(i).getY(), tiles.get(i).getImage());
            }

            if(selectedTileX >= 0 && selectedTileX < xTiles && selectedTileY >= 0 && selectedTileY < yTiles) drawImage(g, selectedTileX, selectedTileY, 4, highlightImage);

            for (int i = 0; i < tiles.size(); i++) {
                for (int j = 0; j < tiles.get(i).getGameObjects().size(); j++) {
                    drawObject(g, tiles.get(i).getX(), tiles.get(i).getY(), tiles.get(i).getGameObjects().get(j).getObjectImage());
                }
            }
        } catch (Exception e) { }

    }

    private void drawFloor(Graphics g, int x, int y, BufferedImage image) { drawFloor(g, x, y, 0, image); }

    private void drawObject(Graphics g, int x, int y, BufferedImage image) { drawObject(g, x, y, 16, image); }

    private void drawFloor(Graphics g, int x, int y, int heightOffset, BufferedImage image) { drawImage(g, x, y, heightOffset, image); }

    private void drawObject(Graphics g, int x, int y, int heightOffset, BufferedImage image) { drawImage(g, x, y, heightOffset, image); }

    private void drawImage(Graphics g, int x, int y, int heightOffset, BufferedImage image) {
        int offX = x * tileColumnOffset / 2 + y * tileColumnOffset / 2 + originX;
        int offY = y * tileRowOffset / 2 - x * tileRowOffset / 2 + originY;

        g.drawImage(image, offX, offY - (int) ((28 + heightOffset) * zoom), (int) (image.getWidth() * zoom), (int) (image.getHeight() * zoom), null);
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

    public void setZoom(float value) {
        value = value / zoomScale;
        zoom = zoom <= 1 && value < 0 ? 1 : zoom + value;
    }

    public void setOffset(float x, float y) {
        setOffset(x, y, 250);
    }

    public void setOffset(float x, float y, float max) {
        max = (max * zoom) * (xTiles / 10);
        xOffset = xOffset >= max * 2 && x > 0 ? max * 2 : xOffset <= -max * 2 && x < 0 ? -max * 2 : xOffset + x;
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
