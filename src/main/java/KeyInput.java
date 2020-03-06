import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private Display display;

    public KeyInput(Display display) {
        this.display = display;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                display.setOffset(-0.25f, -0.25f);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                display.setOffset(0.25f, 0.25f);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                display.setOffset(0.25f, -0.25f);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                display.setOffset(-0.25f, 0.25f);
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}