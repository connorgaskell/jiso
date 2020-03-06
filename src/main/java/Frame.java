import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private final int FRAME_WIDTH = 650;
    private final int FRAME_HEIGHT = 500;

    public Frame() {
        Display display = new Display();
        setLayout(new BorderLayout());
        setTitle("JISO - Java Isometric Game Engine");
        setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(display, BorderLayout.CENTER);
        setVisible(true);
        addKeyListener(new KeyInput(display));
    }

}
