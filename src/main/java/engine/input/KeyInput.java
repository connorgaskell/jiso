package engine.input;

import engine.core.Display;
import engine.objects.Camera;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private Camera camera;

    public KeyInput(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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
        //display.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
