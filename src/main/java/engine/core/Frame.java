package engine.core;

import engine.input.KeyInput;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(Display display) {
        setLayout(new BorderLayout());

        setTitle("JISO - Java Isometric Game Engine");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        getContentPane().add(display, BorderLayout.CENTER);
        addKeyListener(new KeyInput(display.camera));
    }

}
