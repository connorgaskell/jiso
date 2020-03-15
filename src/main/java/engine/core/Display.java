package engine.core;

import engine.input.MouseInput;
import engine.objects.Camera;
import engine.objects.GameObject;
import engine.objects.Tile;
import engine.script.ScriptLoader;
import engine.ui.*;
import engine.ui.Button;
import engine.ui.Image;
import engine.ui.Label;
import engine.vector.Vector2;

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

    public Graphics graphics;
    private int tileColumnOffset, tileRowOffset;
    private Vector2 origin = new Vector2();
    private int numTiles = 32;
    public Vector2 selectedTile = new Vector2();

    Camera camera = new Camera(numTiles);

    private boolean displayCreated = false;

    private MouseInput mouseInput;
    private UI ui;
    private ScriptLoader scriptLoader;
    private BufferedImage objectImage, floorImage, highlightImage, menuImage;
    private Font mainFont;

    private ArrayList<Tile> tiles = new ArrayList<>();

    public Display() {
        mouseInput = new MouseInput(this, camera);

        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);

        setLayout(null);
        setDoubleBuffered(true);
        setBackground(Color.darkGray);

        try {
            objectImage = ImageIO.read(this.getClass().getResource("../../Images/TileObject.png"));
            floorImage = ImageIO.read(this.getClass().getResource("../../Images/FloorTile.png"));
            highlightImage = ImageIO.read(this.getClass().getResource("../../Images/HighlightTile.png"));
            menuImage = ImageIO.read(this.getClass().getResource("../../Images/TestImage.png"));

            mainFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("../../Fonts/yoster.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(mainFont);
        } catch (IOException | FontFormatException e) { }

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
            Label label = new Label(message, mainFont, 14.0f, Color.white, Anchor.TOP_LEFT, new Vector2(8, 8), "lblObjPlaceWarning");

            if(!ui.getLabels().contains(label)) ui.addLabel(label);
            label.destroyComponentAfterTime(ui.getLabels(),1000);
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
        tileColumnOffset = (int)(64 * camera.getZoom());
        tileRowOffset = tileColumnOffset / 2;

        origin.x = (int)((getWidth() / 2 - numTiles * tileColumnOffset / 2) + camera.getOffsetX());
        origin.y = (int)((getHeight() / 2) + camera.getOffsetY());

        int mouseX = mouseInput.x - tileColumnOffset / 2 - origin.x;
        int mouseY = mouseInput.y - tileRowOffset / 2 - origin.y;

        selectedTile.y = (int)Math.round((mouseY + mouseX * 0.5) / tileRowOffset);
        selectedTile.x = -(int)Math.round((mouseY - mouseX * 0.5) / tileRowOffset);

        try {
            for(int i = 0; i < tiles.size(); i++) {
                drawFloor(tiles.get(i).getX(), tiles.get(i).getY(), tiles.get(i).getImage());
            }

            if(selectedTile.x >= 0 && selectedTile.x < numTiles && selectedTile.y >= 0 && selectedTile.y < numTiles) drawImage(selectedTile.x, selectedTile.y, 4, highlightImage);

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

        graphics.drawImage(image, offX, offY - (int) ((28 + heightOffset) * camera.getZoom()), (int) (image.getWidth() * camera.getZoom()), (int) (image.getHeight() * camera.getZoom()), null);
    }

    public void drawGrid(int x, int y, Color color) {
        graphics.setColor(color);
        int offX = x * tileColumnOffset / 2 + y * tileColumnOffset / 2 + origin.x;
        int offY = y * tileRowOffset / 2 - x * tileRowOffset / 2 + origin.y;

        graphics.drawLine(offX, offY + tileRowOffset / 2, offX + tileColumnOffset / 2, offY);
        graphics.drawLine(offX + tileColumnOffset / 2, offY, offX + tileColumnOffset, offY + tileRowOffset / 2);
        graphics.drawLine(offX + tileColumnOffset, offY + tileRowOffset / 2, offX + tileColumnOffset / 2, offY + tileRowOffset);
        graphics.drawLine(offX + tileColumnOffset / 2, offY + tileRowOffset, offX, offY + tileRowOffset / 2);
    }

    private void displayImage(Image image) {
        graphics.drawImage(image.getImage(), image.position.x, image.position.y, image.getSize().x, image.getSize().y, null);
    }

    public Font getMainFont() {
        return mainFont;
    }

    public MouseInput getMouseInput() {
        return mouseInput;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = g;
        if(!displayCreated) createDefaultMap(); displayCreated = true;
        createTileMap();
        camera.dragOffset(mouseInput.dragDistance.x, mouseInput.dragDistance.y, 2.0f);

        for(int i = 0; i < ui.getImages().size(); i++) {
            Image image = (Image)ui.getImages().get(i);
            displayImage(image);
            image.anchor(this, image.getBounds().x, image.getBounds().y, image.getAnchor());

            if(image.checkIntersection(mouseInput.x, mouseInput.y)) {
                image.setSize(new Vector2(image.getBounds().x - 5, image.getBounds().y - 5));
            } else {
                image.setSize(image.getBounds());
            }
        }
    }

    private void createDefaultMap() {
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                tiles.add(new Tile(x, y, floorImage));
            }
        }

        ui = new UI(this);

        tiles.get(0).addGameObject(new GameObject(objectImage, false, false, 0));
        sortTiles();

        ui.addLabel(new Label("", mainFont, 20.0f, Color.white, Anchor.CENTER, new Vector2(0, 0), ""));
        //ui.addImage(new Image(menuImage, new Vector2(200, 200), Anchor.CENTER, new Vector2(0, 0), "imgTest"));

        Image image = new Image(floorImage, new Vector2(64, 64), Anchor.CENTER, new Vector2(0, 0), "imgTest");
        Button button = new Button(image, new Vector2(0, 0), Anchor.BOTTOM, "");
        button.getButton().addActionListener(e -> {
            button.setAnchor(Anchor.CENTER_RIGHT);
        });

        Image image2 = new Image(floorImage, new Vector2(64, 64), Anchor.CENTER, new Vector2(0, 0), "imgTest2");
        Button button2 = new Button(image2, new Vector2(0, 0), Anchor.BOTTOM, "");
        button2.setOffset(1.25f, 0);
        button2.getButton().addActionListener(e -> {
            button2.setAnchor(Anchor.TOP);
        });

        ui.addButton(button);
        ui.addButton(button2);

        Label label = new Label("Game - Version 0.0.1", mainFont, 12.0f, Color.white, Anchor.BOTTOM_LEFT, new Vector2(0, 0), "lblVersion");
        if(!ui.getLabels().contains(label)) ui.addLabel(label);

        try {
            scriptLoader = new ScriptLoader();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) { }

        new Timer(1000 / 60, (ActionEvent e) -> {
            if(scriptLoader != null) {
                for (int i = 0; i < scriptLoader.getLoadedScripts().size(); i++) {
                    scriptLoader.getLoadedScripts().get(i).onDrawFrame();
                }
            }
            repaint();
            if(ui != null) ui.renderUI();
        }).start();
    }

}
