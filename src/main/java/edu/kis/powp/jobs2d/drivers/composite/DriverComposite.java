package edu.kis.powp.jobs2d.drivers.composite;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.HashMap;
import java.util.Map;

public class DriverComposite implements IDriverComposite {
    private final Map<String, Job2dDriver> drivers;

    public DriverComposite() {
        this.drivers = new HashMap<>();
    }

    public DriverComposite(Job2dDriver driver) {
        this.drivers = new HashMap<>();
        this.drivers.put("driver", driver);
    }

    public Map<String, Job2dDriver> getDrivers() {
        return drivers;
    }

    @Override
    public void setPosition(int x, int y) {
        for (Job2dDriver driver : drivers.values()) {
            driver.setPosition(x, y);
        }
    }

    @Override
    public void operateTo(int x, int y) {
        for (Job2dDriver driver : drivers.values()) {
            driver.operateTo(x, y);
        }
    }

    @Override
    public void remove(Job2dDriver driver) {
        for (Map.Entry<String, Job2dDriver> entry : drivers.entrySet()) {
            if (entry.getValue().equals(driver)) {
                drivers.remove(entry.getKey());
                break;
            }
        }
    }

    @Override
    public void add(Job2dDriver driver) {
        drivers.put("driver" + drivers.size(), driver);
    }

    @Override
    public Job2dDriver getDriver(int k) {
        return drivers.get("driver" + k);
    }

    public void clear(){
        drivers.clear();
    }

}