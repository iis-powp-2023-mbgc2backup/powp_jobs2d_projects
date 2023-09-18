package edu.kis.powp.jobs2d.drivers;

import java.util.logging.Logger;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DeviceUsageManager;

public class DistanceLoggerDriver implements Job2dDriver {
    private DeviceUsageManager deviceUsageManager = new DeviceUsageManager();
    Logger logger = Logger.getLogger("global");

    @Override
    public void setPosition(int x, int y) {
        deviceUsageManager.calculateMovingDistance(x, y);
        logger.info("HeadDistance: " + deviceUsageManager.getHeadDistance());
    }

    @Override
    public void operateTo(int x, int y) {
        deviceUsageManager.calculateOperatingDistance(x, y);
        logger.info("OperatingDistance: " + deviceUsageManager.getOperatingDistance());
    }

    public String toString() {
        return "Distance Log";
    }
}
