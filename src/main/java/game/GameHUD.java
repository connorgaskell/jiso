package game;

import engine.script.IsoScript;
import engine.ui.*;
import engine.ui.Button;
import engine.ui.Label;
import engine.vector.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameHUD extends IsoScript {

    private UI ui = defaultUI();
    BufferedImage menuImage;

    @Override
    public void onStart() {
        try {
            menuImage = ImageIO.read(this.getClass().getResource("../Images/TestImage.png"));
        } catch (IOException e) { }

        Label label1 = label(ui, "Test Label", 20.0f, Color.WHITE, Anchor.CENTER_RIGHT);
        label1.setOffset(-0.1f, -2.25f);

        engine.ui.Image image1 = image(ui, menuImage);
        Button button1 = button(ui, image1, Anchor.CENTER_RIGHT);
        button1.setOffset(-0.5f, 0);
        button1.getButton().addActionListener(e -> {
            label1.addOffset(0, -0.1f);
        });

        button1.getButton().addChangeListener(e -> {
            button1.setMouseActionEvent(e, button1, 0.025f);
        });

        Label versionLabel = label(getUI(), "Game - Version 0.0.1", 12.0f, Color.WHITE, Anchor.BOTTOM_LEFT);
        versionLabel.padding = new Vector2(5, 5);
    }

    @Override
    public void onDrawFrame() {
        ui.renderUI();
    }

    public void destroyAllComponentsWithName(ArrayList<UIComponent> components, String name) {
        ArrayList<UIComponent> componentsToDestroy = getUIComponentsByName(components, name);
        IntStream.range(0, componentsToDestroy.size()).forEach(i -> componentsToDestroy.get(i).destroyComponent(components));
    }

    public ArrayList<UIComponent> getUIComponentsByName(ArrayList<UIComponent> components, String name) {
        return (ArrayList<UIComponent>) components.stream().filter(var -> var.getName() == name).collect(Collectors.toList());
    }

    public UI getUI() {
        return ui;
    }

}
