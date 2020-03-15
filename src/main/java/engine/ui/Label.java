package engine.ui;

import engine.Main;
import engine.vector.Vector2;

import javax.swing.*;
import java.awt.*;

public class Label extends UIComponent {

    private String name;
    private String text;
    private Font font;
    private float size;
    private Color color;
    private Anchor anchor;
    private JLabel label;

    public Label(String text, Font font, float size, Color color, Anchor anchor, Vector2 padding, String name) {
        super(name, new Vector2(0, 0));
        this.text = text;
        this.font = font;
        this.size = size;
        this.color = color;
        this.anchor = anchor;
        this.padding = padding;

        this.label = new JLabel();
        this.jComponent = this.label;

        this.label.setLayout(null);
        this.label.setBorder(null);
        this.label.setBackground(null);
        this.label.setText(text);
        this.label.setFont(font);
        this.label.setFont(new Font(font.getName(), Font.PLAIN, (int)size));
        this.label.setForeground(color);

        this.label.setSize((int)label.getPreferredSize().getWidth(), (int)size);

        anchor(Main.display, (int)label.getPreferredSize().getWidth(), 0, anchor);
        setPosition();

        Main.display.add(this.label);
    }

    public void setText(String text) {
        this.text = text;
        this.label.setText(text);
        this.label.setSize((int)label.getPreferredSize().getWidth(), (int)size);
    }

    public String getText() {
        return text;
    }

    public Font getFont() {
        return font;
    }

    public float getSize() {
        return size;
    }

    public int getWidth() {
        return (int)label.getPreferredSize().getWidth();
    }

    public JLabel getJLabel() {
        return this.label;
    }

    public Color getColor() {
        return color;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public void setPosition() {
        label.setLocation(this.position.x, this.position.y);
    }

}
