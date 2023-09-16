package edu.kis.powp.jobs2d.drivers.decorator;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DeviceUsageManager;

import java.util.logging.Logger;


public abstract class DriverDecorator implements Job2dDriver {
    protected Job2dDriver driver;
    Logger logger = Logger.getLogger("global");
    private final DeviceUsageManager deviceUsageManager = new DeviceUsageManager();
    public DeviceUsageManager getDeviceUsageManager() {
        return this.deviceUsageManager;
    }

    public DriverDecorator(Job2dDriver driver){
        this.driver = driver;
    }

    @Override
    public void setPosition(int x, int y) {
        driver.setPosition(x,y);
        deviceUsageManager.calculateMovingDistance(x,y);
    }

    @Override
    public void operateTo(int x, int y) {
        driver.operateTo(x,y);
        deviceUsageManager.calculateOperatingDistance(x,y);
    }

    @Override
    public String toString() {
        return driver.toString();
    }
}
