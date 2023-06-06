package edu.kis.powp.jobs2d.command;

import java.time.LocalDateTime;
import java.util.Iterator;

public class CountingCommandVisitor implements ICommandVisitor {

    private int compoundCommandsCount = 0;
    private int operateToCommandsCount = 0;
    private int setPositionCommandsCount = 0;
    private int previousX = 0;
    private int previousY = 0;
    private int start = 0;
    private double totalLength = 0;
    private double operateToLength = 0;
    private LocalDateTime executionTime;

    public int getCompoundCommandsCount() {
        return this.compoundCommandsCount;
    }

    public int getOperateToCommandsCount() {
        return this.operateToCommandsCount;
    }

    public int getSetPositionCommandsCount() {
        return this.setPositionCommandsCount;
    }

    public double getTotalLength(){
        return totalLength;
    }

    public double getOperateToLength(){
        return operateToLength;
    }

    public LocalDateTime getExecutionTime() {
        return executionTime;
    }

    public CountingCommandVisitor() {}

    @Override
    public void visit(ICompoundCommand command) {
        executionTime = LocalDateTime.now();
        this.compoundCommandsCount = 0;
        this.operateToCommandsCount = 0;
        this.setPositionCommandsCount = 0;
        this.totalLength = 0;
        this.operateToLength = 0;
        this.start = 0;
        this.previousX = 0;
        this.previousY = 0;
        Iterator<DriverCommand> iterator = command.iterator();

        while(iterator.hasNext()) {
            iterator.next().accept(this);
        }

        this.compoundCommandsCount = this.operateToCommandsCount + this.setPositionCommandsCount;
    }

    @Override
    public void visit(OperateToCommand command) {
        this.operateToCommandsCount++;
        double distance = getDistance(command.getPosX(), command.getPosY());
        if(distance < 0){
            return;
        }
        totalLength += distance;
        operateToLength += distance;
    }

    @Override
    public void visit(SetPositionCommand command) {
        this.setPositionCommandsCount++;
        double distance = getDistance(command.getPosX(), command.getPosY());
        if(distance < 0){
            return;
        }
        totalLength += distance;
    }

    private double getDistance(int newX, int newY){
        if(start == 0){
            start = 1;
            previousX = newX;
            previousY = newY;
            return -1;
        }
        double distX = (newX - previousX);
        double distY = (newY - previousY);
        previousY = newY;
        previousX = newX;
        return Math.sqrt(distX * distX + distY *distY );
    }
}
