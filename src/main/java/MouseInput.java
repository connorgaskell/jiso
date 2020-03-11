import java.awt.event.*;

public class MouseInput implements MouseListener, MouseWheelListener, MouseMotionListener {

    private Display display;
    public int x;
    public int y;

    private int dragStartX, dragStartY;
    public int dragDistanceX, dragDistanceY;
    private boolean middleMouseDown = false;

    public MouseInput(Display display) {
        this.display = display;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON2) {
            middleMouseDown = true;
            dragStartX = e.getX();
            dragStartY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON2) {
            middleMouseDown = false;
            dragDistanceX = 0;
            dragDistanceY = 0;
        }
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
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(middleMouseDown) {
            dragDistanceX = (dragStartX - e.getX()) / 50;
            dragDistanceY = (dragStartY - e.getY()) / 50;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
