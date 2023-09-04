package edu.kis.powp.jobs2d.transformations;

public class Move implements Transformation {

    private final int changeTox;
    private final int changeToy;

    public Move(int changeTox, int changeToy) {
        this.changeTox = changeTox;
        this.changeToy = changeToy;
    }

    @Override
    public int calculateNewX(int x, int y) {
        return changeToy + y;
    }

    @Override
    public int calculateNewY(int x, int y) {
        return changeTox + x;
    }
}