package edu.kis.powp.jobs2d.transformations;

public class TransformationFactory {
    public static Transformation getClockwiseRotation() {
        return new Rotation(-1, 1);
    }

    public static Transformation getCounterclockwiseRotation() {
        return new Rotation(1, -1);
    }

    public static Transformation getHorizontalFlip() {
        return new Flip(1, -1);
    }

    public static Transformation getVerticalFlip() {
        return new Flip(-1, 1);
    }

    public static Transformation getDoubleScale() {
        return new Scale(2);
    }

    public static Transformation getHalfScale() {
        return new Scale(0.50);
    }
}
