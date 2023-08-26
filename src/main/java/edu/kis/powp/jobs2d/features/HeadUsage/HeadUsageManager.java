package edu.kis.powp.jobs2d.features.HeadUsage;

import edu.kis.powp.jobs2d.Job2dDriver;

public class HeadUsageManager implements Job2dDriver,HeadUsageStats {
    private int x;
    private int y;
    private double headDistance;
    private double operationDistance;

    HeadUsageManager() {
        this.x = 0;
        this.y = 0;
        this.headDistance = 0.0;
        this.operationDistance = 0.0;
    }
    @Override
    public double getHeadDistance() {
        return headDistance;
    }
    @Override
    public double getOperationDistance() {
        return operationDistance;
    }

    private double calculateDistance(int x, int y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    @Override
    public void setPosition(int x, int y) {
        this.headDistance += calculateDistance(x, y);
        this.x = x;
        this.y = y;
    }

    @Override
    public void operateTo(int x, int y) {
        this.operationDistance += calculateDistance(x, y);
        setPosition(x, y);
    }
}