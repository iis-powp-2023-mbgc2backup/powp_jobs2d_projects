package edu.kis.powp.jobs2d.drivers;

import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.composite.DriverComposite;
import edu.kis.powp.jobs2d.features.ExtensionFeature;
import edu.kis.powp.observer.Publisher;
import edu.kis.powp.observer.Subscriber;

/**
 * Driver manager provides means to setup the driver. It also enables other
 * components and features of the application to react on configuration changes.
 */
public class DriverManager {

    private Job2dDriver currentDriver = new LoggerDriver();

    private final Publisher changePublisher = new Publisher();

    /**
     * @param driver Set the driver as current.
     */
    public synchronized void setCurrentDriver(Job2dDriver driver) {
        currentDriver = driver;
        changePublisher.notifyObservers();
    }

    public synchronized void addSubscriber(Subscriber subscriber) {
        changePublisher.addSubscriber(subscriber);
    }

    /**
     * @return Current driver.
     */
    public synchronized Job2dDriver getCurrentDriver() {
        List<Job2dDriver> extensions =  ExtensionFeature.getExtensionsManager().getUsedExtensions();
		DriverComposite driverComposite = new DriverComposite(currentDriver);
		for (Job2dDriver driver: extensions) {
			driverComposite.add(driver);
		}
		return driverComposite;
    }
}
