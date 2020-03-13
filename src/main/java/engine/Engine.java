package engine;

public class Engine {

    public static Display display;

    public static void main(String[] args) {
        display = new Display();
        new Frame(display);
    }

}
