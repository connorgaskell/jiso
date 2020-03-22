package engine.sound;

import javazoom.jl.player.Player;

import javax.swing.*;

public class Sound {

    private String soundPath;
    private boolean stopLoop = false;
    private boolean loopStarted = false;
    private SwingWorker<Void, Void> soundWorker, loopWorker;
    private Player player = null;

    public Sound(String soundPath) {
        this.soundPath = soundPath;
    }

    public void play() {
        soundWorker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws Exception {
                player = new Player(this.getClass().getResource(soundPath).openStream());
                player.play();
                this.cancel(true);
                return null;
            }
        };

        soundWorker.execute();
    }

    public void loop() {
        stopLoop = false;
        loopWorker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws Exception {
                loopStarted = true;
                while (!stopLoop) {
                    player = new Player(this.getClass().getResource(soundPath).openStream());
                    player.play();
                }
                this.cancel(true);
                return null;
            }
        };

        loopWorker.execute();
    }

    public boolean isLooping() {
        return loopStarted;
    }

    public void stop() {
        if(loopStarted && !stopLoop) loopStarted = false; stopLoop = true;
        if(player != null) player.close();
    }


}
