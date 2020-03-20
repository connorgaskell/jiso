package engine.objects;

import engine.Main;
import engine.core.Display;
import engine.vector.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class World {

    private Vector2 origin = new Vector2();
    private int numTiles = 32;

    private Display display;
    private Camera camera;

    private ArrayList<Tile> tiles = new ArrayList<>();

    public World(Display display, Camera camera) {
        this.display = display;
        this.camera = camera;
    }

    public void addTile(int x, int y, BufferedImage image) {
        tiles.add(new Tile(x, y, image));
        sortTiles();
    }

    public void removeTile(int x, int y) {
        try {
            tiles.remove(getTile(x, y));
            //IntStream.range(0, tiles.size()).filter(i -> !isTileNear(new Vector2(tiles.get(i).getX(), tiles.get(i).getY()))).forEach(i -> tiles.remove(tiles.get(i)));

            for(int i = 0; i < tiles.size(); i++) {
                if(!isTileNear(new Vector2(tiles.get(i).getX(), tiles.get(i).getY()))) {
                    tiles.remove(tiles.get(i));
                }
            }

        } catch (IndexOutOfBoundsException e) { }
        sortTiles();
    }

    public boolean addObject(int x, int y, BufferedImage image) {
        try {
            getTile(x, y).addGameObject(new GameObject(image, false, false, 0));
            sortTiles();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean isTileNear(Vector2 pos) {
        return tiles.stream().filter(var -> (var.getX() == pos.x - 1 && var.getY() == pos.y) || (var.getX() == pos.x && var.getY() == pos.y - 1) || (var.getX() == pos.x + 1 && var.getY() == pos.y) || (var.getX() == pos.x && var.getY() == pos.y + 1)).collect(Collectors.toList()).size() > 0;
    }

    public Tile getTile(int x, int y) {
        return tiles.stream().filter(var -> var.getX() == x && var.getY() == y).collect(Collectors.toList()).get(0);
    }

    public void sortTiles() {
        tiles.sort((Tile t1, Tile t2) -> (Integer.compare(t2.getX(), t1.getX())));
        tiles.sort(Comparator.comparingInt(Tile::getY));
    }

    public Vector2 getSelectedTile(Vector2 mousePosition) {
        int mouseX = mousePosition.x - getTileOffset().x / 2 - getOrigin().x;
        int mouseY = mousePosition.y - getTileOffset().y / 2 - getOrigin().y;

        return new Vector2(-(int)Math.round((mouseY - mouseX * 0.5) / getTileOffset().y), (int)Math.round((mouseY + mouseX * 0.5) / getTileOffset().y));
    }

    public Vector2 getTileOffset() {
        return new Vector2((int)(64 * camera.getZoom()), (int)(64 * camera.getZoom()) / 2);
    }

    public Vector2 getOrigin() {
        origin.x = display.getWidth() / 2 - numTiles * getTileOffset().x / 2 + (int)Math.floor(camera.getOffsetX() * camera.getZoom());
        origin.y = (display.getHeight() / 2) + (int)Math.floor(camera.getOffsetY() * camera.getZoom());
        return origin;
    }

    public int getNumTiles() {
        return numTiles;
    }

    public void drawFloor(int x, int y, BufferedImage image) { drawFloor(x, y, 0, image); }

    public void drawObject(int x, int y, BufferedImage image) { drawObject(x, y, 16, image); }

    public void drawFloor(int x, int y, int heightOffset, BufferedImage image) { drawImage(x, y, heightOffset, image); }

    public void drawObject(int x, int y, int heightOffset, BufferedImage image) { drawImage(x, y, heightOffset, image); }

    public void drawImage(int x, int y, int heightOffset, BufferedImage image) {
        int offX = (x * getTileOffset().x / 2  + y * getTileOffset().x / 2) + getOrigin().x;
        int offY = (y * getTileOffset().y / 2 - x * getTileOffset().y / 2) + getOrigin().y;

        Main.display.graphics.drawImage(image, offX, offY - (int) ((28 + heightOffset) * camera.getZoom()), (int) (image.getWidth() * camera.getZoom()), (int) (image.getHeight() * camera.getZoom()), null);
    }

    public void drawGrid(int x, int y, Color color) {
        Main.display.graphics.setColor(color);
        int offX = x * getTileOffset().x / 2 + y * getTileOffset().x / 2 + getOrigin().x;
        int offY = y * getTileOffset().y / 2 - x * getTileOffset().y / 2 + getOrigin().y;

        Main.display.graphics.drawLine(offX, offY + getTileOffset().y / 2, offX + getTileOffset().x / 2, offY);
        Main.display.graphics.drawLine(offX + getTileOffset().x / 2, offY, offX + getTileOffset().x, offY + getTileOffset().y / 2);
        Main.display.graphics.drawLine(offX + getTileOffset().x, offY + getTileOffset().y / 2, offX + getTileOffset().x / 2, offY + getTileOffset().y);
        Main.display.graphics.drawLine(offX + getTileOffset().x / 2, offY + getTileOffset().y, offX, offY + getTileOffset().y / 2);
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

}
