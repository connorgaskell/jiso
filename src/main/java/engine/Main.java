package engine;

import engine.core.Display;
import engine.core.Frame;
import engine.objects.Camera;

import javax.swing.*;

public class Main {

    public static Display display;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            display = new Display();
            new Frame(display);
        });
    }

}
