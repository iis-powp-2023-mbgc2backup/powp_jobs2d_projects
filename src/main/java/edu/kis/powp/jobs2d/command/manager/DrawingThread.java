package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;

public class DrawingThread implements Runnable {
    private final DriverCommand command;
    private final Job2dDriver driver;

    public DrawingThread(DriverCommand command, Job2dDriver driver) {
        this.command = command;
        this.driver = driver;
    }

    @Override
    public void run() {
        command.execute(driver);
    }
}