package edu.kis.powp.jobs2d.drivers.decorator;

import edu.kis.powp.jobs2d.Job2dDriver;

public class DragDriver  extends DriverDecorator {

    private final int dx;
    private final int dy;
    public DragDriver(Job2dDriver driver, int dx,int dy) {
        super(driver);
        this.dx=dx;
        this.dy=dy;
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x+dx, y+dy);
    }

    @Override
    public void operateTo(int x, int y) {
        super.operateTo(x+dx, y+dy);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
