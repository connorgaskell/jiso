package game;

import engine.objects.World;
import engine.script.IsoScript;
import engine.ui.Anchor;
import engine.ui.Label;
import engine.ui.UI;
import engine.vector.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameMap extends IsoScript {

    private World world;
    private BufferedImage objectImage, floorImage, highlightImage;
    private UI ui;
    private Vector2 mousePosition = new Vector2();

    @Override
    public void onStart() {
        try {
            objectImage = ImageIO.read(this.getClass().getResource("../Images/TileObject.png"));
            floorImage = ImageIO.read(this.getClass().getResource("../Images/FloorTile.png"));
            highlightImage = ImageIO.read(this.getClass().getResource("../Images/HighlightTile.png"));
        } catch (IOException e) { }

        world = new World(getDisplay(), ((GameCamera)getScript("game.GameCamera")).getCamera());
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                world.addTile(x, y, floorImage);
            }
        }

        world.addObject(0, 0, objectImage);

        ui = ((GameHUD)getScript("game.GameHUD")).getUI();
        Label versionLabel = label(ui, "Game - Version 0.0.1", 12.0f, Color.WHITE, Anchor.BOTTOM_LEFT);
        versionLabel.padding = new Vector2(5, 5);
    }

    @Override
    public void onDrawFrame() {
        try {
            for(int i = 0; i < world.getTiles().size(); i++) {
                world.drawFloor(world.getTiles().get(i).getX(), world.getTiles().get(i).getY(), world.getTiles().get(i).getImage());
            }

            if(world.getSelectedTile(mousePosition).x >= 0 && world.getSelectedTile(mousePosition).x < world.getNumTiles() && world.getSelectedTile(mousePosition).y >= 0 && world.getSelectedTile(mousePosition).y < world.getNumTiles())
                world.drawImage(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y, 4, highlightImage);

            for (int i = 0; i < world.getTiles().size(); i++) {
                for (int j = 0; j < world.getTiles().get(i).getGameObjects().size(); j++) {
                    world.drawObject(world.getTiles().get(i).getX(), world.getTiles().get(i).getY(), world.getTiles().get(i).getGameObjects().get(j).getObjectImage());
                }
            }
        } catch (Exception e) { }
    }


    @Override
    public void onMousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            world.addTile(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y, floorImage);
        }

        if(e.getButton() == MouseEvent.BUTTON3) {
            boolean success = world.addObject(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y, objectImage);
            if(!success) {
                String message = "You cannot place an object here!";
                System.out.println(message);
                Label label = label(ui, message, 14.0f, Color.WHITE, Anchor.TOP_LEFT);
                label.destroyComponentAfterTime(ui.getLabels(),1000);
            }
        }
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        mousePosition.x = e.getX();
        mousePosition.y = e.getY();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        mousePosition.x = e.getX();
        mousePosition.y = e.getY();
    }

}
