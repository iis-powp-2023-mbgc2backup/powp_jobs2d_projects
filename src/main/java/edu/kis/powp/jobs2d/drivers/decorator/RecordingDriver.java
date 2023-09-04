package edu.kis.powp.jobs2d.drivers.decorator;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.recorder.CommandRecorder;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class RecordingDriver extends DriverDecorator {
    private final CommandRecorder commandRecorder;

    public RecordingDriver(Job2dDriver driver, CommandRecorder commandRecorder) {
        super(driver);
        this.commandRecorder = commandRecorder;
    }

    @Override
    public void setPosition(int x, int y) {
        RecordFeature.recordCommand(new SetPositionCommand(x,y));
        super.setPosition(x,y);
        commandRecorder.setLastPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        RecordFeature.recordCommand(new OperateToCommand(x,y));
        super.operateTo(x,y);
        commandRecorder.setLastPosition(x, y);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
