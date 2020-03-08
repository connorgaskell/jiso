import java.awt.*;
import java.util.*;

public class Animation {

    private ArrayList frames;
    private int currentFrameIndex;
    private long animTime;
    private long totalDuration;

    public Animation() {
        frames = new ArrayList();
        totalDuration = 0;
        start();
    }

    public synchronized void addFrame(Image image, long duration) {
        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    public synchronized void start() {
        animTime = 0;
        currentFrameIndex = 0;
    }

    public synchronized void update(long elapsedTime) {
        if(frames.size() > 1) {
            animTime += elapsedTime;

            if(animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currentFrameIndex = 0;
            }

            while(animTime > getFrame(currentFrameIndex).endTime) {
                currentFrameIndex++;
            }
        }
    }

    public synchronized Image getImage() {
        if(frames.size() == 0) {
            return null;
        } else {
            return getFrame(currentFrameIndex).image;
        }
    }

    private AnimFrame getFrame(int i) {
        return (AnimFrame)frames.get(i);
    }

    private class AnimFrame {
        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }

}
