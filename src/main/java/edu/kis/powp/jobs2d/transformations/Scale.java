package edu.kis.powp.jobs2d.transformations;

public class Scale implements Transformation {

    private final double scale;

    public Scale(double scale) {
        this.scale = scale;
    }

    @Override
    public int calculateNewX(int x, int y) {
        return (int)(scale * x);
    }

    @Override
    public int calculateNewY(int x, int y) {
        return (int)(scale * y);
    }
}