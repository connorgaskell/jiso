package engine;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    //private Display display;

    public Frame(Display display) {
        setLayout(new BorderLayout());

        setTitle("JISO - Java Isometric Game Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        //Engine.display = new Display();
        //Engine.display.setLayout(null);
        getContentPane().add(display, BorderLayout.CENTER);

        addKeyListener(new KeyInput(Engine.display));
    }

}
