package engine;

import vector.Vector2;

import java.awt.event.*;

public class MouseInput implements MouseListener, MouseWheelListener, MouseMotionListener {

    private Display display;
    public int x;
    public int y;

    private Vector2 dragStart = new Vector2();
    public Vector2 dragDistance = new Vector2();
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
            dragStart.x = e.getX();
            dragStart.y = e.getY();
        }

        if(e.getButton() == MouseEvent.BUTTON1) {
            display.addTile(display.selectedTile.x, display.selectedTile.y);
        }

        if(e.getButton() == MouseEvent.BUTTON3) {
            display.addObject(display.selectedTile.x, display.selectedTile.y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON2) {
            middleMouseDown = false;
            dragDistance.x = 0;
            dragDistance.y = 0;
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
            dragDistance.x = (dragStart.x - e.getX()) / 50;
            dragDistance.y = (dragStart.y - e.getY()) / 50;
        }

        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
