package engine.particle;

import engine.core.Display;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Particle {

    private int x;
    private int y;
    private int dx;
    private int dy;
    private int size;
    private int life;
    private Color color;
    private int colorAlpha;

    private Display display;

    public Particle(Display display, int x, int y, int dx, int dy, int size, int life, Color color) {
        this.display = display;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
        this.color = color;
        this.colorAlpha = color.getAlpha();
    }

    public Particle(Display display, int x, int y, int dx, int dy, int size, int life, ArrayList<Color> colors) {
        this.display = display;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.size = size;
        this.life = life;
        this.color = colors.get(ThreadLocalRandom.current().nextInt(colors.size()));
        this.colorAlpha = color.getAlpha();
    }

    public boolean update() {
        x += dx;
        y += dy;
        life--;
        size *= 0.99999999995f;
        colorAlpha -= 10;
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), colorAlpha);
        if(life < 0) return true;
        return false;
    }

    public void render() {
        display.graphics.setColor(color);
        display.graphics.fillRect(x - (size / 2), y - (size / 2), size, size);
    }

}
