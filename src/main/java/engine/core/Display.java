package engine.core;

import engine.input.KeyInput;
import engine.input.MouseInput;
import engine.script.IsoScript;
import engine.script.ScriptLoader;
import engine.vector.Vector2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Display extends JPanel {

    public Graphics2D graphics;
    public Vector2 selectedTile = new Vector2();

    private boolean displayCreated = false;

    private MouseInput mouseInput;
    private ScriptLoader scriptLoader;
    private BufferedImage objectImage, floorImage, highlightImage, menuImage;
    private Font mainFont;

    public Display() {
        mouseInput = new MouseInput(this);

        setFocusable(true);
        requestFocus();
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

    public Font getMainFont() {
        return mainFont;
    }

    public ArrayList<IsoScript> getScripts() {
        return scriptLoader.getLoadedScripts();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        this.graphics = g2d;

        if (!displayCreated) {
            startApplication();
            displayCreated = true;
        }

        IntStream.range(0, scriptLoader.getLoadedScripts().size()).forEach(i -> scriptLoader.getLoadedScripts().get(i).onDrawFrame());

        /*for(int i = 0; i < ui.getImages().size(); i++) {
            Image image = (Image)ui.getImages().get(i);
            displayImage(image);
            image.anchor(this, image.getBounds().x, image.getBounds().y, image.getAnchor());

            if(image.checkIntersection(mouseInput.x, mouseInput.y)) {
                image.setSize(new Vector2(image.getBounds().x - 5, image.getBounds().y - 5));
            } else {
                image.setSize(image.getBounds());
            }
        }*/
    }

    private void startApplication() {
        try {
            scriptLoader = new ScriptLoader();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) { }

        new Timer(1000 / 60, (ActionEvent e) -> repaint()).start();

        scriptLoader.startScripts(scriptLoader.getLoadedScripts());

        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        addMouseWheelListener(mouseInput);
        addKeyListener(new KeyInput(this));
    }

}
