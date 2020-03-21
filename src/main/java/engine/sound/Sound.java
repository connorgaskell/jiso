package engine.sound;

import javazoom.jl.player.Player;

import javax.swing.*;

public class Sound {

    private SwingWorker<Void,Void> worker;

    public Sound(String soundPath) {
        worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws Exception {
                Player player = new Player(this.getClass().getResource(soundPath).openStream());
                player.play();
                return null;
            }
        };
        worker.execute();
    }

}
