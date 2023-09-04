package edu.kis.powp.jobs2d.transformations;

public class Rotation implements Transformation {

    private final int xCoefficient;
    private final int yCoefficient;

    public Rotation(int xCoeff, int yCoeff) {
        this.xCoefficient = xCoeff;
        this.yCoefficient = yCoeff;
    }

    @Override
    public int calculateNewX(int x, int y) {
        return xCoefficient * y;
    }

    @Override
    public int calculateNewY(int x, int y) {
        return yCoefficient * x;
    }
}