package engine.ui;

import engine.vector.Vector2;

import java.awt.*;

public class Label extends UIComponent {

    private String name;
    private String text;
    private Font font;
    private float size;
    private Color color;
    private Anchor anchor;

    public Label(String text, Font font, float size, Color color, Vector2 position, String name) {
        super(name, position);
        this.text = text;
        this.font = font;
        this.size = size;
        this.color = color;
        this.anchor = Anchor.NONE;
    }

    public Label(String text, Font font, float size, Color color, Anchor anchor, String name) {
        super(name, new Vector2(0, 0));
        this.text = text;
        this.font = font;
        this.size = size;
        this.color = color;
        this.anchor = anchor;
        this.padding = new Vector2(0, 0);
    }

    public Label(String text, Font font, float size, Color color, Anchor anchor, Vector2 padding, String name) {
        super(name, new Vector2(0, 0));
        this.text = text;
        this.font = font;
        this.size = size;
        this.color = color;
        this.anchor = anchor;
        this.padding = padding;
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

    public Color getColor() {
        return color;
    }

    public Anchor getAnchor() {
        return anchor;
    }

}
