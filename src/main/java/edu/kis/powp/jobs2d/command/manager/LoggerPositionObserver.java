package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.features.DeviceUsageManager;
import edu.kis.powp.observer.Subscriber;
import java.util.logging.Logger;

public class LoggerPositionObserver implements Subscriber {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final DeviceUsageManager deviceUsageManager;

    public LoggerPositionObserver(DeviceUsageManager deviceUsageManager){
        this.deviceUsageManager = deviceUsageManager;
    }

    @Override
    public void update() {
        logger.info("Last Position: (" + deviceUsageManager.getLastXPosition() + ", " + deviceUsageManager.getLastYPosition() + ")");
    }

    public String toString() {
        return "Logger Position Observer";
    }
}