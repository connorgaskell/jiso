package game;

import engine.script.IsoScript;

public class ExampleScript extends IsoScript {

    @Override
    public void onStart() {
        System.out.println("Script started...");
    }

    @Override
    public void onUpdate() {
        System.out.println(getMouseWorldPosition().x);
    }

}
