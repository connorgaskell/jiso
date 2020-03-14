package engine.script;

import engine.Main;
import engine.core.Display;
import engine.vector.Vector2;

public abstract class IsoScript {

    public Display getDisplay() {
        return Main.display;
    }

    public Vector2 getMouseWorldPosition() {
        return new Vector2(Main.display.selectedTile.x, Main.display.selectedTile.y);
    }

    public void addGroundTile() {

    }

    public void displayTileGrid(boolean display) {

    }

    public abstract void onStart();
    public abstract void onUpdate();

}
