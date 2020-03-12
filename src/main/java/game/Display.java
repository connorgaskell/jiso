package game;

import ui.Anchor;
import ui.Label;
import ui.UIComponent;
import vector.Vector2;

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

    public Graphics g;
    private int tileColumnOffset, tileRowOffset;
    private Vector2 origin = new Vector2();
    private int xTiles = 32, yTiles = 32;
    public Vector2 selectedTile = new Vector2();

    private float xOffset, yOffset;
    private float zoom = 1f, zoomScale = 10;

    private MouseInput mouseInput;
    private BufferedImage objectImage, floorImage, highlightImage;
    private Font mainFont;

    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<UIComponent> labels = new ArrayList<>();

    public Display() {
        mouseInput = new MouseInput(this);

        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);

        setDoubleBuffered(true);
        setBackground(Color.darkGray);

        try {
            objectImage = ImageIO.read(this.getClass().getResource("../Images/TileObject.png"));
            floorImage = ImageIO.read(this.getClass().getResource("../Images/FloorTile.png"));
            highlightImage = ImageIO.read(this.getClass().getResource("../Images/HighlightTile.png"));

            mainFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("../Fonts/yoster.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(mainFont);
        } catch (IOException | FontFormatException e) { }

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

        tiles.get(0).addGameObject(new GameObject(objectImage, false, false, 0));
        sortTiles();

        labels.add(new Label(this, "", mainFont, 20.0f, Color.white, new Vector2(0, 0)));
    }

    public void addTile(int x, int y) {
        tiles.add(new Tile(x, y, floorImage));
        sortTiles();
    }

    public void addObject(int x, int y) {
        try {
            getTile(x, y).addGameObject(new GameObject(objectImage, false, false, 0));
        } catch(Exception e) {
            String message = "You cannot place an object here!";
            Label label = new Label(this, message, mainFont, 14.0f, Color.white, Anchor.BOTTOM_RIGHT, new Vector2(8, 6));

            if(!labels.contains(label)) labels.add(label);
            label.destroyComponentAfterTime(labels,1000);
        }
    }

    private Tile getTile(int x, int y) {
        return tiles.stream().filter(var -> var.getX() == x && var.getY() == y).collect(Collectors.toList()).get(0);
    }

    private void sortTiles() {
        tiles.sort((Tile t1, Tile t2) -> (Integer.compare(t2.getX(), t1.getX())));
        tiles.sort(Comparator.comparingInt(Tile::getY));
    }

    private void createTileMap() {
        tileColumnOffset = (int)(64 * zoom);
        tileRowOffset = tileColumnOffset / 2;

        origin.x = (int)((getWidth() / 2 - xTiles * tileColumnOffset / 2) + xOffset);
        origin.y = (int)((getHeight() / 2) + yOffset);

        int mouseX = mouseInput.x - tileColumnOffset / 2 - origin.x;
        int mouseY = mouseInput.y - tileRowOffset / 2 - origin.y;

        selectedTile.y = (int)Math.round((mouseY + mouseX * 0.5) / tileRowOffset);
        selectedTile.x = -(int)Math.round((mouseY - mouseX * 0.5) / tileRowOffset);

        //System.out.println(selectedTileX + " | " + selectedTileY);

        try {
            for(int i = 0; i < tiles.size(); i++) {
                drawFloor(tiles.get(i).getX(), tiles.get(i).getY(), tiles.get(i).getImage());
            }

            if(selectedTile.x >= 0 && selectedTile.x < xTiles && selectedTile.y >= 0 && selectedTile.y < yTiles) drawImage(selectedTile.x, selectedTile.y, 4, highlightImage);

            for (int i = 0; i < tiles.size(); i++) {
                for (int j = 0; j < tiles.get(i).getGameObjects().size(); j++) {
                    drawObject(tiles.get(i).getX(), tiles.get(i).getY(), tiles.get(i).getGameObjects().get(j).getObjectImage());
                }
            }
        } catch (Exception e) { }

    }

    private void drawFloor(int x, int y, BufferedImage image) { drawFloor(x, y, 0, image); }

    private void drawObject(int x, int y, BufferedImage image) { drawObject(x, y, 16, image); }

    private void drawFloor(int x, int y, int heightOffset, BufferedImage image) { drawImage(x, y, heightOffset, image); }

    private void drawObject(int x, int y, int heightOffset, BufferedImage image) { drawImage(x, y, heightOffset, image); }

    private void drawImage(int x, int y, int heightOffset, BufferedImage image) {
        int offX = x * tileColumnOffset / 2 + y * tileColumnOffset / 2 + origin.x;
        int offY = y * tileRowOffset / 2 - x * tileRowOffset / 2 + origin.y;

        g.drawImage(image, offX, offY - (int) ((28 + heightOffset) * zoom), (int) (image.getWidth() * zoom), (int) (image.getHeight() * zoom), null);
    }

    private void drawTile(int x, int y, Color color) {
        g.setColor(color);
        int offX = x * tileColumnOffset / 2 + y * tileColumnOffset / 2 + origin.x;
        int offY = y * tileRowOffset / 2 - x * tileRowOffset / 2 + origin.y;

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

    public void displayLabel(Label label) {
        try {
            g.setColor(label.getColor());
            g.setFont(label.getFont());
            Font resizedFont = g.getFont().deriveFont(label.getSize());
            g.setFont(resizedFont);
            g.drawString(label.getText(), label.position.x, label.position.y);
        } catch(Exception e) { }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;
        createTileMap();
        dragOffset(mouseInput.dragDistance.x, mouseInput.dragDistance.y, 2.0f);

        for(int i = 0; i < labels.size(); i++) {
            displayLabel((Label)labels.get(i));
            labels.get(i).anchor(g.getFontMetrics().stringWidth(((Label) labels.get(i)).getText()), 0, ((Label) labels.get(i)).getAnchor());
        }
    }

}
