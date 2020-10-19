package engine.objects;

import java.awt.image.*;
import java.util.*;

public class Tile {

    private int x, y;
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private BufferedImage floorImage;

    public Tile(int x, int y, BufferedImage floorImage) {
        this.x = x;
        this.y = y;
        this.floorImage = floorImage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getImage() {
        return floorImage;
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject() {
        gameObjects.remove(getLastGameObject());
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public GameObject getLastGameObject() {
        try {
            return gameObjects.get(gameObjects.size() - 1);
        } catch(IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public GameObject getFirstGameObject() {
        try {
            return gameObjects.get(0);
        } catch(IndexOutOfBoundsException ex) {
            return null;
        }
    }

}
