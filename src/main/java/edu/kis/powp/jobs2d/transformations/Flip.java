package edu.kis.powp.jobs2d.transformations;

public class Flip implements Transformation  {

    private final int xCoefficient;
    private final int yCoefficient;

    public Flip(int xCoeff, int yCoeff) {
        this.xCoefficient = xCoeff;
        this.yCoefficient = yCoeff;
    }

    @Override
    public int calculateNewX(int x, int y) {
        return xCoefficient * x;
    }

    @Override
    public int calculateNewY(int x, int y) {
        return yCoefficient * y;
    }
}