package game;

import engine.script.IsoScript;
import engine.ui.Anchor;
import engine.ui.Button;
import engine.ui.Label;
import engine.ui.UI;
import engine.vector.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameHUD extends IsoScript {

    private UI ui = defaultUI();
    BufferedImage menuImage;

    @Override
    public void onStart() {
        try {
            menuImage = ImageIO.read(this.getClass().getResource("../Images/TestImage.png"));
        } catch (IOException e) { }

        Label label1 = label(ui, "Test Label", 20.0f, Color.WHITE, Anchor.TOP_LEFT);
        label1.setOffset(0, 2);

        Label label2 = label(ui, "Okkk", 20.0f, Color.WHITE, Anchor.CENTER_LEFT);
        label2.setOffset(3.5f, 0);

        Label label3 = label(ui, "Okkk", 20.0f, Color.WHITE, Anchor.BOTTOM_LEFT);
        label3.setOffset(0, -2);

        engine.ui.Image image1 = image(ui, menuImage);
        Button button1 = button(ui, image1, Anchor.CENTER_RIGHT);
        button1.setOffset(-0.5f, 0);
        button1.getButton().addActionListener(e -> {
            hideLabel(ui, label1);
        });

        button1.getButton().addChangeListener(e -> {
            button1.setMouseActionEvent(e, button1, 0.025f);
        });

        System.out.println(ui);
        Label versionLabel = label(getUI(), "Game - Version 0.0.1", 12.0f, Color.WHITE, Anchor.BOTTOM_LEFT);
        versionLabel.padding = new Vector2(5, 5);
    }

    @Override
    public void onDrawFrame() {
        ui.renderUI();
    }

    public UI getUI() {
        return ui;
    }

}
