package edu.kis.powp.jobs2d.drivers.decorator;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.PositionLoggingDriver;
import edu.kis.powp.jobs2d.features.DeviceUsageManager;
import edu.kis.powp.jobs2d.features.ExtensionsManager;

import java.util.logging.Logger;


public abstract class DriverDecorator implements Job2dDriver {
    protected Job2dDriver driver;
    Logger logger = Logger.getLogger("global");
    private int operationCount = 0;
    private final DeviceUsageManager deviceUsageManager = new DeviceUsageManager();
    private final PositionLoggingDriver positionLoggingDriver = new PositionLoggingDriver();
    public DeviceUsageManager getDeviceUsageManager() {
        return this.deviceUsageManager;
    }

    public DriverDecorator(Job2dDriver driver){
        this.driver = driver;
    }

    @Override
    public void setPosition(int x, int y) {
        driver.setPosition(x,y);
        if(ExtensionsManager.isDistanceLogRunning())
            deviceUsageManager.calculateMovingDistance(x,y);
        if(ExtensionsManager.isLoggerRunning())
            positionLoggingDriver.setPosition(x,y);
    }

    @Override
    public void operateTo(int x, int y) {
        driver.operateTo(x,y);
        if(ExtensionsManager.isDistanceLogRunning())
            deviceUsageManager.calculateOperatingDistance(x,y);
        if(ExtensionsManager.isLoggerRunning())
            positionLoggingDriver.operateTo(x, y);
    }

    @Override
    public String toString() {
        return driver.toString();
    }
}
