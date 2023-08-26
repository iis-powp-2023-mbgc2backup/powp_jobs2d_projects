package edu.kis.powp.jobs2d.command;

import java.awt.*;
import java.util.Iterator;

public class CanvasVisitor implements ICommandVisitor{

    private Shape canvas;
    private boolean isIncluded;

    public boolean isIncluded() {
        return isIncluded;
    }

    public void setCanvas(Shape canvas) {
        this.canvas = canvas;
    }

    public CanvasVisitor(Shape shape) {
        this.canvas = shape;
    }

    @Override
    public void visit(ICompoundCommand command) {
        Iterator<DriverCommand> iterator = command.iterator();

        while (iterator.hasNext()) {
            iterator.next().accept(this);
        }
    }

    @Override
    public void visit(OperateToCommand command) {
        this.isIncluded = canvas.contains(command.getPosX(), command.getPosY());
    }

    @Override
    public void visit(SetPositionCommand command) {
        this.isIncluded = canvas.contains(command.getPosX(), command.getPosY());
    }
}