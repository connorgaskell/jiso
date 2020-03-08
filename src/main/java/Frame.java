import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    private Display display;

    public Frame() {
        setLayout(new BorderLayout());

        display = new Display();
        getContentPane().add(display, BorderLayout.CENTER);

        setTitle("JISO - Java Isometric Game Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        addKeyListener(new KeyInput(display));
    }

}
