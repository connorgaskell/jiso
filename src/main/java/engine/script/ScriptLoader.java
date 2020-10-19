package engine.script;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.IntStream;

public class ScriptLoader {

    private ArrayList<JisoScript> loadedScripts = new ArrayList<>();

    public ScriptLoader() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        for(int i = 0; i < getClasses("game").length; i++) {
            JisoScript script = (JisoScript) Class.forName(getClasses("game")[i].getName()).newInstance();
            script.setScriptName(getClasses("game")[i].getName());
            loadedScripts.add(script);
        }
    }

    public void startScripts(ArrayList<JisoScript> scripts) {
        // Clone the passed ArrayList into a temporary ArrayList.
        ArrayList<JisoScript> tempScripts = (ArrayList<JisoScript>)scripts.clone();

        try {
            // Iterate through the ArrayList and call the onStart() method of each IsoScript, if successful this element can be removed from the ArrayList.
            IntStream.range(0, tempScripts.size()).forEach(i -> {
                scripts.get(i).onStart();
                tempScripts.remove(0);
            });
        } catch(NullPointerException e) { }

        // The forEach will end if there is a NullPointerException or when complete, if an Exception is thrown then tempScripts will still have some elements.
        if(tempScripts.size() > 0) {
            // Reorder the elements in the ArrayList and call this method again to retry.
            Collections.rotate(tempScripts, -1);
            startScripts(tempScripts);
        }
    }

    public ArrayList<JisoScript> getLoadedScripts() {
        return loadedScripts;
    }

    /*
     * Reference: https://dzone.com/articles/get-all-classes-within-package
     */
    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

}
