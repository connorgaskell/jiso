package game;

import engine.objects.World;
import engine.script.IsoScript;
import engine.sound.Sound;
import engine.ui.Anchor;
import engine.ui.Label;
import engine.ui.UI;
import engine.util.HexColor;
import engine.vector.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class GameMap extends IsoScript {

    private World world;
    private BufferedImage objectImage, floorImage, highlightImage, highlightImageRed;
    private UI ui;
    private Vector2 mousePosition = new Vector2();
    private boolean isShiftDown = false;
    private Sound ambientSound, breakSound;
    private int starterTileAmount = 10;

    @Override
    public void onStart() {
        try {
            objectImage = ImageIO.read(this.getClass().getResource("../Images/TileObject.png"));
            floorImage = ImageIO.read(this.getClass().getResource("../Images/FloorTile.png"));
            highlightImage = ImageIO.read(this.getClass().getResource("../Images/HighlightTile.png"));
            highlightImageRed = ImageIO.read(this.getClass().getResource("../Images/HighlightTileRed.png"));
        } catch (IOException e) { }

        world = new World(getDisplay(), ((GameCamera)getScript("game.GameCamera")).getCamera());
        for(int x = 0; x < starterTileAmount; x++) {
            for(int y = 0; y < starterTileAmount; y++) {
                world.addTile(x, y, floorImage);
            }
        }

        world.addObject(0, 0, objectImage);

        ui = ((GameHUD)getScript("game.GameHUD")).getUI();
        Label versionLabel = label(ui, "Game - Version 0.0.1", 12.0f, Color.WHITE, Anchor.BOTTOM_LEFT);
        versionLabel.padding = new Vector2(5, 5);

        /*
         * Create a new Sound object, currently the sound will automatically play.
         * This is a temporary ambient sound example used for the project.
         * Sound Credit: Despic Zeljko - zeljkodespic@gmail.com (https://www.youtube.com/watch?v=tgIrn0c7zGk)
         */
        ambientSound = new Sound("../../Sounds/Ambient/Farm.mp3");
        ambientSound.play();
    }

    @Override
    public void onDrawFrame() {
        try {
            for(int i = 0; i < world.getTiles().size(); i++) {
                world.drawFloor(world.getTiles().get(i).getX(), world.getTiles().get(i).getY(), world.getTiles().get(i).getImage());
            }

            world.drawImage(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y, 4, isInWorldBounds(world, mousePosition) ? highlightImage : highlightImageRed);

            for (int i = 0; i < world.getTiles().size(); i++) {
                for (int j = 0; j < world.getTiles().get(i).getGameObjects().size(); j++) {
                    world.drawObject(world.getTiles().get(i).getX(), world.getTiles().get(i).getY(), world.getTiles().get(i).getGameObjects().get(j).getObjectImage());
                }
            }
        } catch (Exception e) { }
        world.renderParticles();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        switch(e.getButton()) {
            case MouseEvent.BUTTON1:
                if(isShiftDown && world.getTiles().size() >= 10) {
                    if(world.getTile(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y) != null) {
                        createParticles(world.tileToScreenPosition(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y).x, world.tileToScreenPosition(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y).y);
                        breakSound = new Sound("../../Sounds/Effects/Break.mp3");
                        breakSound.play();
                    }
                    world.removeTile(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y);
                } else if(isShiftDown) {
                    displayTemporaryLabel("You already have the minimum number of tiles in your world!", 1000);
                }

                if(!isShiftDown && isInWorldBounds(world, mousePosition) && world.getTile(world.getSelectedTile(mousePosition).x, world.getSelectedTile(mousePosition).y) == null) {
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

    private void createParticles(int x, int y) {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(new HexColor("#825B18", 255).color);
        colors.add(new HexColor("#F1AE2C", 255).color);
        colors.add(new HexColor("#B78122", 255).color);
        colors.add(new HexColor("#459826", 255).color);
        colors.add(new HexColor("#134D1D", 255).color);
        for(int i = 0; i < 32; i++) {
            world.drawParticle(x, y, colors);
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

    public int getStarterTileAmount() {
        return starterTileAmount;
    }

}
