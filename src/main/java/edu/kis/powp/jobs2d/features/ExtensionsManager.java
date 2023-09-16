package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;
import edu.kis.powp.jobs2d.command.manager.LoggerDistanceObserver;
import edu.kis.powp.jobs2d.command.manager.LoggerPositionObserver;
import edu.kis.powp.jobs2d.drivers.composite.DriverComposite;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.PositionLoggingDriver;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ExtensionsManager implements FeatureObject {
    private static DriverComposite driverComposite;
    private List<DeviceUsageManager> deviceUsageManagers;

    private boolean isLoggerRunning = false;
    private static boolean isDistanceLogRunning = false;


    public ExtensionsManager() {
        deviceUsageManagers =  new ArrayList<>();
    }


    @Override
    public void setup(Application application) {
        application.addComponentMenu(ExtensionsManager.class,"Extensions");
        application.addComponentMenuElementWithCheckBox(ExtensionsManager.class, "Logger", (ActionEvent e) -> toggleLogger(), false);
        application.addComponentMenuElementWithCheckBox(ExtensionsManager.class, "Distance log",(ActionEvent e) -> toggleDistanceLog(), false);
    }

    public void toggleLogger() {
        if(!isLoggerRunning){
            for (DeviceUsageManager manager : deviceUsageManagers) {
                manager.getPositionPublisher().addSubscriber(new LoggerPositionObserver(manager));
            }
        } else{
            for (DeviceUsageManager manager : deviceUsageManagers) {
                manager.getPositionPublisher().clearObservers();
            }
        }

        isLoggerRunning = !isLoggerRunning;

    }
    

    public void toggleDistanceLog(){
        if(!isDistanceLogRunning){
            for (DeviceUsageManager manager : deviceUsageManagers) {
                manager.getDistanceChangePublisher().addSubscriber(new LoggerDistanceObserver(manager));
            }
        }else{
            for (DeviceUsageManager manager : deviceUsageManagers) {
                manager.getDistanceChangePublisher().clearObservers();
            }
        }
        isDistanceLogRunning = !isDistanceLogRunning;
    }

    

    public void setDriverComposite(DriverComposite composite) {
        driverComposite = composite;
    }

    public void setDeviceUsageManagers(List<DeviceUsageManager> usageManagers) {
        deviceUsageManagers = usageManagers;
    }
}
