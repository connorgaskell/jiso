package engine.script;

import java.util.ArrayList;

public class ScriptLoader {

    private ArrayList<IsoScript> loadedScripts = new ArrayList<>();

    public ScriptLoader() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        IsoScript script = (IsoScript) Class.forName("game.ExampleScript").newInstance();
        script.onStart();
        loadedScripts.add(script);
    }

    public ArrayList<IsoScript> getLoadedScripts() {
        return loadedScripts;
    }

}
