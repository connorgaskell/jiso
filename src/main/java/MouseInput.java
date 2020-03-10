import java.awt.event.*;

public class MouseInput implements MouseListener, MouseWheelListener, MouseMotionListener {

    private Display display;
    public int x;
    public int y;

    public MouseInput(Display display) {
        this.display = display;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        display.setZoom(-e.getWheelRotation());
        display.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        display.repaint();
    }
}
