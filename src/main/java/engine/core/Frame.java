package engine.core;

import engine.input.KeyInput;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(Display display) {
        setLayout(new BorderLayout());

        setTitle("JISO - Java Isometric Game Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setVisible(true);

        getContentPane().add(display, BorderLayout.CENTER);
        addKeyListener(new KeyInput(display.getCamera()));
    }

}
