package engine;

import engine.core.Display;
import engine.core.Frame;
import engine.objects.Camera;

public class Main {

    public static Display display;

    public static void main(String[] args) {
        display = new Display();
        new Frame(display);
    }

}
