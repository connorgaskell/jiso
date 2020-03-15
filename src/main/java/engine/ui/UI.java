package engine.ui;

import engine.Main;
import engine.core.Display;
import engine.vector.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class UI {

    private ArrayList<UIComponent> labels = new ArrayList<>();
    private ArrayList<UIComponent> images = new ArrayList<>();
    private ArrayList<UIComponent> buttons = new ArrayList<>();

    private Display display;

    public UI(Display display) {
        this.display = display;
    }

    public ArrayList<UIComponent> getButtons() {
        return buttons;
    }

    public ArrayList<UIComponent> getLabels() {
        return labels;
    }

    public ArrayList<UIComponent> getImages() {
        return images;
    }

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void addLabel(Label label) {
        labels.add(label);
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public void renderUI() {
        for(int i = 0; i < getLabels().size(); i++) {
            Label label = (Label)getLabels().get(i);
            label.anchor(display, label.getWidth(), (int)label.getSize(), label.getAnchor());
            label.setPosition();
        }

        for(int i = 0; i < getButtons().size(); i++) {
            Button button = (Button)getButtons().get(i);
            button.anchor(display, button.getBounds().x, button.getBounds().y, button.getAnchor());
            button.setPosition();
        }
    }

}
