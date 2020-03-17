package engine.script;

import engine.Main;
import engine.core.Display;
import engine.ui.Anchor;
import engine.ui.Image;
import engine.ui.Label;
import engine.ui.Button;
import engine.ui.UI;
import engine.vector.Vector2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.stream.Collectors;

public abstract class IsoScript {

    private String scriptName;

    public Display getDisplay() {
        return Main.display;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public Vector2 getMouseWorldPosition() {
        return new Vector2(Main.display.selectedTile.x, Main.display.selectedTile.y);
    }

    public void addGroundTile() {

    }

    public void displayTileGrid(boolean display) {

    }

    public IsoScript getScript(String name) {
        try {
            return getDisplay().getScripts().stream().filter(var -> var.getScriptName() == name).collect(Collectors.toList()).get(0);
        } catch(NullPointerException e) { }
        return null;
    }

    public UI defaultUI() {
        return new UI(Main.display);
    }

    public Label label(UI ui, String text, float fontSize, Color color, Anchor anchor) {
        Label tempLabel = new Label(text, Main.display.getMainFont(), fontSize, color, anchor, new Vector2(0, 0), "");
        ui.addLabel(tempLabel);
        return (Label)ui.getLabels().get(ui.getLabels().indexOf(tempLabel));
    }

    public void showLabel(UI ui, Label label) {
        ui.addLabel(label);
    }

    public void hideLabel(UI ui, Label label) {
        ((Label)ui.getLabels().get(ui.getLabels().indexOf(label))).jComponent.setVisible(false);
    }

    public Image image(UI ui, BufferedImage image) {
        Image tempImage = new Image(image, new Vector2(64, 64), Anchor.CENTER, new Vector2(0, 0), "imgTest");
        return tempImage;
    }

    public void showImage(UI ui, Image image) {
        ui.addImage(image);
    }

    public Button button(UI ui, Image image, Anchor anchor) {
        Button tempButton = new Button(image, new Vector2(0, 0), anchor, "");
        ui.addButton(tempButton);
        return (Button)ui.getButtons().get(ui.getButtons().indexOf(tempButton));
    }

    public void showButton(UI ui, Button button) {
        ui.addButton(button);
    }

    public abstract void onStart();
    public abstract void onDrawFrame();

}
