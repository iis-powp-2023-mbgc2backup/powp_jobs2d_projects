package edu.kis.powp.jobs2d.command.recorder;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandRecorder {
    private List<DriverCommand> driverCommands = new ArrayList<>();
    private List<DriverCommand> transformCommands = new ArrayList<>();

    private List<DriverCommand> canvas = new ArrayList<>();
    private int lastX;
    private int lastY;

    public void addCommand(DriverCommand command){
        driverCommands.add(command);
    }

    public void clearCommand(){
        this.driverCommands.clear();
    }

    public List<DriverCommand> getCommands(){
        return driverCommands;
    }

    public void addTransformCommand(DriverCommand command){
        transformCommands.add(command);
    }

    public void clearTransformCommand(){
        this.transformCommands.clear();
    }

    public List<DriverCommand> getTransformCommands(){
        return transformCommands;
    }

    public void deleteCanvas(){
        this.canvas.clear();
    }

    public List<DriverCommand> getCanvas(){return canvas;}

    public void setLastPosition(int x, int y) {
        this.lastX = x;
        this.lastY = y;
    }

    public void restoreLastPosition() {
        driverCommands.add(new SetPositionCommand(lastX, lastY));
    }

    public int getLastX() {
        return this.lastX;
    }

    public int getLastY() {
        return this.lastY;
    }
}
