package ui;

import vector.Vector2;
import game.Display;

import java.awt.*;

public class Label extends UIComponent {

    private String text;
    private Font font;
    private float size;
    private Color color;
    private Anchor anchor;

    public Label(Display panel, String text, Font font, float size, Color color, Vector2 position) {
        super(panel, position);
        this.text = text;
        this.font = font;
        this.size = size;
        this.color = color;
        this.anchor = Anchor.NONE;
    }

    public Label(Display panel, String text, Font font, float size, Color color, Anchor anchor) {
        super(panel, new Vector2(0, 0));
        this.text = text;
        this.font = font;
        this.size = size;
        this.color = color;
        this.anchor = anchor;
        this.padding = new Vector2(0, 0);
    }

    public Label(Display panel, String text, Font font, float size, Color color, Anchor anchor, Vector2 padding) {
        super(panel, new Vector2(0, 0));
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
