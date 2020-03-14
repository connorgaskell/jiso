package engine.ui;

import engine.Main;
import engine.vector.Vector2;

import javax.swing.*;

public class Button extends UIComponent {

    private Image image;
    private Label label;
    private Vector2 size;
    private Vector2 bounds;
    private Anchor anchor;
    private JButton button;

    public Button(Image image, Vector2 position, Anchor anchor, String name) {
        super(name, position);
        this.image = image;
        this.size = image.getSize();
        this.bounds = size;
        this.anchor = anchor;
        this.button = new JButton();

        image.position = this.position;

        this.button.setLayout(null);
        this.button.setBounds(this.position.x, this.position.y, image.getSize().x, image.getSize().y);
        this.button.setIcon(new ImageIcon(image.getImage()));
        this.button.setBorder(null);
        this.button.setBackground(null);
        this.button.setFocusPainted(false);
        this.button.setContentAreaFilled(false);

        anchor(Main.display, image.getSize().x, image.getSize().y, Anchor.TOP_RIGHT);
        setPosition();

        Main.display.add(this.button);
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public Image getImage() {
        return image;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getBounds() {
        return bounds;
    }

    public Anchor getAnchor() {
        return anchor;
    }

    public JButton getButton() {
        return button;
    }

    public void setPosition() {
        button.setLocation(this.position.x, this.position.y);
    }

}
