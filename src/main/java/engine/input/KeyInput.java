package engine.input;

import engine.core.Display;
import engine.objects.Camera;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.stream.IntStream;

public class KeyInput implements KeyListener {

    private Display display;

    public KeyInput(Display display) {
        this.display = display;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onKeyTyped(e));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onKeyPressed(e));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        IntStream.range(0, display.getScripts().size()).forEach(i -> display.getScripts().get(i).onKeyReleased(e));
    }
}
