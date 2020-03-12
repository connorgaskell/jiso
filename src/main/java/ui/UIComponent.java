package ui;

import vector.Vector2;

import javax.swing.*;
import java.util.ArrayList;

public class UIComponent {

    public Vector2 position;
    private JPanel panel;
    public Vector2 padding;

    public UIComponent(JPanel panel, Vector2 position) {
        this.panel = panel;
        this.position = position;
        padding = new Vector2(0, 0);
    }

    public void destroyComponent(ArrayList<UIComponent> components) {
        components.remove(this);
    }

    public void destroyComponentAfterTime(ArrayList<UIComponent> components, int time) {
        Timer timer = new Timer(time, e -> components.remove(this));
        timer.setRepeats(false);
        timer.start();
    }

    public void anchor(int width, int height, Anchor type) {
        switch (type) {
            case NONE:
                break;
            case TOP:
                position = new Vector2((panel.getWidth() / 2) - width, height + 14 + padding. y);
                break;
            case TOP_LEFT:
                position = new Vector2(padding.x, height + 14 + padding.y);
                break;
            case TOP_RIGHT:
                position = new Vector2((panel.getWidth()) - width + padding.x, height + 15 + padding.y);
                break;
            case BOTTOM:
                position = new Vector2((panel.getWidth() / 2) - width, ((panel.getHeight()) - height) - padding.y);
                break;
            case BOTTOM_LEFT:
                position = new Vector2(padding.x, (panel.getHeight()) - height - padding.y);
                break;
            case BOTTOM_RIGHT:
                position = new Vector2(panel.getWidth() - width - padding.x, (panel.getHeight()) - height - padding.y);
                break;
            case CENTER:
                position = new Vector2((panel.getWidth() / 2) - width, (panel.getHeight() / 2) - height);
                break;
            case CENTER_LEFT:
                position = new Vector2(padding.x, (panel.getHeight() / 2) - height);
                break;
            case CENTER_RIGHT:
                position = new Vector2(panel.getWidth() - width - padding.x, (panel.getHeight() / 2) - height);
                break;
        }
    }

}
