package engine.ui;

import engine.Main;
import engine.vector.Vector2;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        this.jComponent = this.button;

        this.button.setLayout(null);
        this.button.setBounds(this.position.x, this.position.y, image.getSize().x, image.getSize().y);

        java.awt.Image scaledImage = image.getImage().getScaledInstance(size.x, size.y, java.awt.Image.SCALE_SMOOTH);
        this.button.setIcon(new ImageIcon(scaledImage));

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

    public Vector2 getPosition() {
        return new Vector2(button.getLocation().x, button.getLocation().y);
    }

    public void setPosition() {
        button.setLocation(this.position.x, this.position.y);
    }

    public void setMouseActionEvent(ChangeEvent e, Button btn, float yOffset) {
        ButtonModel model =  ((JButton) e.getSource()).getModel();
        if (model.isRollover() && !model.isPressed()) {
            btn.addOffset(0, -yOffset);
        } else if(!model.isRollover() || model.isPressed()) {
            btn.resetOffset();
        }
    }

}
