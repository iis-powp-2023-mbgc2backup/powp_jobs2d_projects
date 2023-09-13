package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.manager.LoggerDistanceObserver;


import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ExtensionsManager implements FeatureObject {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Level loggerLevel;
    private static boolean isLoggerRunning = true;
    private static boolean isDistanceLogRunning = true;
    private LoggerDistanceObserver loggerDistanceObserver;


    @Override
    public void setup(Application application) {
        application.addComponentMenu(ExtensionsManager.class,"Extensions");
        application.addComponentMenuElementWithCheckBox(ExtensionsManager.class, "Logger", (ActionEvent e) -> toggleLogger(), true);
        application.addComponentMenuElementWithCheckBox(ExtensionsManager.class, "Distance log",(ActionEvent e) -> toggleDistanceLog(), true);

        loggerDistanceObserver = new LoggerDistanceObserver(DeviceUsageManager.getInstance());
        DeviceUsageManager.getInstance().addObserver(loggerDistanceObserver);

    }

    public void toggleLogger() {
        DeviceUsageManager.getInstance().toggleLoggerObserver();
        isLoggerRunning = !isLoggerRunning;
    }
    

    public void toggleDistanceLog(){
        isDistanceLogRunning = !isDistanceLogRunning;
    }

    public static boolean isDistanceLogRunning(){
        return isDistanceLogRunning;
    }
    
    public static boolean isLoggerRunning(){
        return isLoggerRunning;
    }

}
