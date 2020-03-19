package engine.ui;

import engine.core.Display;
import engine.vector.Vector2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UIComponent {

    public String name;
    public Vector2 position;
    private Vector2 origin;
    public Vector2 padding;
    public float offsetX, offsetY;
    private float originOffsetX, originOffsetY;
    public boolean isHidden = false;
    public JComponent jComponent;

    public UIComponent(String name, Vector2 position) {
        this.position = position;
        this.origin = position;
        padding = new Vector2(0, 0);
        offsetX = 0;
        offsetY = 0;
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public void setOffset(float x, float y) {
        offsetX = x;
        offsetY = y;
        originOffsetX = x;
        originOffsetY = y;
    }

    public void addOffset(float x, float y) {
        offsetX += x;
        offsetY += y;
    }

    public void addOffsetToOrigin(float x, float y) {
        offsetX = origin.x + x;
        offsetY = origin.y + y;
    }

    public void resetOffset() {
        offsetX = originOffsetX;
        offsetY = originOffsetY;
    }


    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void destroyComponent(ArrayList<UIComponent> components) {
        Container parent = components.get(components.indexOf(this)).jComponent.getParent();
        parent.remove(components.get(components.indexOf(this)).jComponent);
        components.remove(this);
    }

    public void destroyComponentAfterTime(ArrayList<UIComponent> components, int time) {
        Timer timer = new Timer(time, e -> destroyComponent(components));
        timer.setRepeats(false);
        timer.start();
    }

    public void anchor(Display panel, int width, int height, Anchor type) {
        int originalWidth = width, originalHeight = height;

        switch (type) {
            case NONE:
                break;
            case TOP:
                if(this instanceof Image || this instanceof Button || this instanceof Label) width = width / 2; height = 0;
                position = new Vector2((panel.getWidth() / 2) - width, height + padding.y);
                break;
            case TOP_LEFT:
                if(this instanceof Image || this instanceof Button || this instanceof Label) height = 0;
                position = new Vector2(padding.x, height + padding.y);
                break;
            case TOP_RIGHT:
                if(this instanceof Image  || this instanceof Button || this instanceof Label) height = 0;
                position = new Vector2((panel.getWidth()) - width + padding.x, height + padding.y);
                break;
            case BOTTOM:
                if(this instanceof Image || this instanceof Button || this instanceof Label) width = width / 2;
                position = new Vector2((panel.getWidth() / 2) - width, ((panel.getHeight()) - height) - padding.y);
                break;
            case BOTTOM_LEFT:
                position = new Vector2(padding.x, (panel.getHeight()) - height - padding.y);
                break;
            case BOTTOM_RIGHT:
                position = new Vector2(panel.getWidth() - width - padding.x, (panel.getHeight()) - height - padding.y);
                break;
            case CENTER:
                if(this instanceof Image || this instanceof Button || this instanceof Label) width = width / 2; height = height / 2;
                position = new Vector2((panel.getWidth() / 2) - width, (panel.getHeight() / 2) - height);
                break;
            case CENTER_LEFT:
                if(this instanceof Image || this instanceof Button || this instanceof Label) height = height / 2;
                position = new Vector2(padding.x, (panel.getHeight() / 2) - height);
                break;
            case CENTER_RIGHT:
                if(this instanceof Image || this instanceof Button || this instanceof Label) height = height / 2;
                position = new Vector2(panel.getWidth() - width - padding.x, (panel.getHeight() / 2) - height);
                break;
        }

        position.x += (int)(offsetX * originalWidth);
        position.y += (int)(offsetY * originalHeight);
    }

}
