package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.HeadUsage.HeadUsageManager;
import edu.kis.powp.jobs2d.features.HeadUsage.HeadUsageFeature;
import edu.kis.powp.jobs2d.features.HeadUsage.HeadUsageStats;
import edu.kis.powp.observer.Subscriber;

import java.util.logging.Logger;

public class LoggerHeadUsageObserver implements Subscriber {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final HeadUsageStats headUsageStats;

    public LoggerHeadUsageObserver(HeadUsageStats headUsageStats) {
        this.headUsageStats = headUsageStats;
    }

    public void update() {
        logger.info("Head distance: " + headUsageStats.getHeadDistance() + " units");
        logger.info("Op.  distance: " + headUsageStats.getOperationDistance() + " units");
    }

    public String toString() {
        return "Logger HeadUsage Observer";
    }

}