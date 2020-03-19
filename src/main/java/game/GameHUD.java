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

    public UI getUI() {
        return ui;
    }

}
