package core;

public class Vector2D {

    private double x;
    private double y;

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize() {
        double length = length();
        x = x == 0 ? 0 : x / length;
        y = y == 0 ? 0 : y / length;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void multiply(double speed) {
        x *= speed;
        y *= speed;
    }
}