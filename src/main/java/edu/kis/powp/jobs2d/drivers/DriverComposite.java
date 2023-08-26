package edu.kis.powp.jobs2d.drivers;

import java.util.ArrayList;
import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;

public class DriverComposite implements Job2dDriver {

    private final List<Job2dDriver> drivers;
    public DriverComposite() {
        drivers = new ArrayList<>();
    }
    public void addDriver(Job2dDriver driver) {
        drivers.add(driver);
    }
    public void clearDrivers() {
        drivers.clear();
    }
    @Override
    public void setPosition(int x, int y) {
        for (Job2dDriver driver : drivers) {
            driver.setPosition(x, y);
        }
    }
    @Override
    public void operateTo(int x, int y) {
        for(Job2dDriver driver : drivers) {
            driver.operateTo(x, y);
        }
    }
    @Override
    public String toString() {
        return drivers.stream().map(Object::toString).reduce((a, b) -> a + ", " + b).orElse("");
    }

}
