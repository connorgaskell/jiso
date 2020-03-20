package game;

import engine.objects.World;
import engine.script.IsoScript;
import engine.ui.Anchor;
import engine.ui.Label;
import engine.ui.UI;
import engine.vector.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameMap extends IsoScript {

    private World world;
    private BufferedImage objectImage, floorImage, highlightImage, highlightImageRed;
    private UI ui;
    private Vector2 mousePosition = new Vector2();
    private boolean isShiftDown = false;

    @Override
    public void onStart() {
        try {
            objectImage = ImageIO.read(this.getClass().getResource("../Images/TileObject.png"));
            floorImage = ImageIO.read(this.getClass().getResource("../Images/FloorTile.png"));
            highlightImage = ImageIO.read(this.getClass().getResource("../Images/HighlightTile.png"));
            highlightImageRed = ImageIO.read(this.getClass().getResource("../Images/HighlightTileRed.png"));
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

            if(isInWorldBounds(world, mousePosition)) {
                world.drawImage(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y, 4, world.isTileNear(world.getSelectedTile(mousePosition)) ? highlightImage : highlightImageRed);
            }

            for (int i = 0; i < world.getTiles().size(); i++) {
                for (int j = 0; j < world.getTiles().get(i).getGameObjects().size(); j++) {
                    world.drawObject(world.getTiles().get(i).getX(), world.getTiles().get(i).getY(), world.getTiles().get(i).getGameObjects().get(j).getObjectImage());
                }
            }
        } catch (Exception e) { }
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        switch(e.getButton()) {
            case MouseEvent.BUTTON1:
                if(isShiftDown && world.getTiles().size() >= 10) {
                    world.removeTile(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y);
                } else if(isShiftDown) {
                    displayTemporaryLabel("You already have the minimum number of tiles in your world!", 1000);
                }

                if(!isShiftDown && isInWorldBounds(world, mousePosition) && world.isTileNear(world.getSelectedTile(mousePosition))) {
                    world.addTile(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y, floorImage);
                } else {
                    displayTemporaryLabel("You cannot place a tile here!", 1000);
                }
                break;

            case MouseEvent.BUTTON3:
                if(isInWorldBounds(world, mousePosition)) {
                    boolean success = world.addObject(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y, objectImage);
                    if(!success) {
                        displayTemporaryLabel("You cannot place an object here!", 1000);
                    }
                } else {
                    displayTemporaryLabel("You cannot place an object here!", 1000);
                }
                break;
        }
    }

    public void displayTemporaryLabel(String message, int time) {
        ((GameHUD)getScript("game.GameHUD")).destroyAllComponentsWithName(ui.getLabels(), "TempLabel");
        Label label = label(ui, message, 14.0f, Color.WHITE, Anchor.TOP_LEFT);
        label.setName("TempLabel");
        label.destroyComponentAfterTime(ui.getLabels(), time);
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

    @Override
    public void onKeyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftDown = true;
        }
    }

    @Override
    public void onKeyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            isShiftDown = false;
        }
    }

    public World getWorld() {
        return world;
    }

}
