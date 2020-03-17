package engine.input;

import engine.core.Display;
import engine.objects.Camera;
import engine.vector.Vector2;

import java.awt.event.*;
import java.util.stream.IntStream;

public class MouseInput implements MouseListener, MouseWheelListener, MouseMotionListener {

    private Display display;

    public MouseInput(Display display) {
        this.display = display;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onMouseClicked(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onMousePressed(e));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onMouseReleased(e));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onMouseEntered(e));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onMouseExit(e));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onMouseWheelMoved(e));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onMouseDragged(e));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onMouseMoved(e));
    }
}
