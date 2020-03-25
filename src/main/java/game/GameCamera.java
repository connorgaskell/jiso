package game;

import engine.objects.Camera;
import engine.script.IsoScript;
import engine.ui.UI;
import engine.vector.Vector2;

import java.awt.event.*;

public class GameCamera extends IsoScript {

    private Camera camera = defaultCamera();

    public int x;
    public int y;

    private Vector2 dragStart = new Vector2();
    public Vector2 dragDistance = new Vector2();
    private boolean middleMouseDown = false;

    @Override
    public void onStart() {
        GameMap gameMap = ((GameMap)getScript("game.GameMap"));
        camera.setOffset((gameMap.getWorld().getNumTiles() * 32) - (gameMap.getStarterTileAmount() * 32),0);
    }

    @Override
    public void onDrawFrame() {
        camera.dragOffset(dragDistance.x, dragDistance.y, 2.0f);
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON2) {
            middleMouseDown = true;
            dragStart.x = e.getX();
            dragStart.y = e.getY();
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON2) {
            middleMouseDown = false;
            dragDistance.x = 0;
            dragDistance.y = 0;
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if(middleMouseDown) {
            dragDistance.x = (dragStart.x - e.getX()) / 50;
            dragDistance.y = (dragStart.y - e.getY()) / 50;
        }

        x = e.getX();
        y = e.getY();
    }

    @Override
    public void onMouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void onMouseWheelMoved(MouseWheelEvent e) {
        camera.setZoom(-e.getWheelRotation());
    }

    @Override
    public void onKeyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                camera.setOffset(0.0f, 5.0f);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                camera.setOffset(-0.0f, -5.0f);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                camera.setOffset(5.0f, 0.0f);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                camera.setOffset(-5.0f, 0.0f);
                break;
        }
    }

}
