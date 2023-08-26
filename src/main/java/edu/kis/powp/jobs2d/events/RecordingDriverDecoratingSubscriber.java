package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.recorder.CommandRecorder;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.decorator.RecordingDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.observer.Subscriber;

public class RecordingDriverDecoratingSubscriber implements Subscriber {
    private final CommandRecorder commandRecorder;

    public RecordingDriverDecoratingSubscriber(CommandRecorder commandRecorder) {
        this.commandRecorder = commandRecorder;
    }

    @Override
    public void update() {
        DriverManager driverManager =  DriverFeature.getDriverManager();
        Job2dDriver currentDriver = driverManager.getCurrentDriver();

        RecordingDriver recordingDriver = new RecordingDriver(currentDriver, this.commandRecorder);
        driverManager.setCurrentDriver(recordingDriver);
        recordingDriver.setPosition(commandRecorder.getLastX(), commandRecorder.getLastY());
    }
}
