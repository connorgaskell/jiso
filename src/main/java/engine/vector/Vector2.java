package engine.vector;

public class Vector2 {

    public int x;
    public int y;

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }

    public double distanceTo(Vector2 other) {
        float v0 = other.x - this.x;
        float v1 = other.y - this.y;
        return Math.sqrt(v0 * v0 + v1 * v1);
    }

}
