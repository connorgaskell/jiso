package engine.ui;

import engine.Display;
import vector.Vector2;

import javax.swing.*;
import java.util.ArrayList;

public class UIComponent {

    public String name;
    public Vector2 position;
    public Vector2 padding;

    public UIComponent(String name, Vector2 position) {
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

    public void anchor(Display panel, int width, int height, Anchor type) {
        switch (type) {
            case NONE:
                break;
            case TOP:
                if(this instanceof Image || this instanceof Button) width = width / 2; height = 0;
                position = new Vector2((panel.getWidth() / 2) - width, height + padding.y);
                break;
            case TOP_LEFT:
                if(this instanceof Image || this instanceof Button) height = 0;
                position = new Vector2(padding.x, height + padding.y);
                break;
            case TOP_RIGHT:
                if(this instanceof Image  || this instanceof Button) height = 0;
                position = new Vector2((panel.getWidth()) - width + padding.x, height + padding.y);
                break;
            case BOTTOM:
                if(this instanceof Image || this instanceof Button) width = width / 2;
                position = new Vector2((panel.getWidth() / 2) - width, ((panel.getHeight()) - height) - padding.y);
                break;
            case BOTTOM_LEFT:
                position = new Vector2(padding.x, (panel.getHeight()) - height - padding.y);
                break;
            case BOTTOM_RIGHT:
                position = new Vector2(panel.getWidth() - width - padding.x, (panel.getHeight()) - height - padding.y);
                break;
            case CENTER:
                if(this instanceof Image || this instanceof Button) width = width / 2; height = height / 2;
                position = new Vector2((panel.getWidth() / 2) - width, (panel.getHeight() / 2) - height);
                break;
            case CENTER_LEFT:
                if(this instanceof Image || this instanceof Button) height = height / 2;
                position = new Vector2(padding.x, (panel.getHeight() / 2) - height);
                break;
            case CENTER_RIGHT:
                if(this instanceof Image || this instanceof Button) height = height / 2;
                position = new Vector2(panel.getWidth() - width - padding.x, (panel.getHeight() / 2) - height);
                break;
        }
    }

}
