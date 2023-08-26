package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

public class ToStringCommandVisitor implements ICommandVisitor {

    private String commandString = "";

    @Override
    public void visit(ICompoundCommand command) {

        Iterator<DriverCommand> iterator = command.iterator();

        while (iterator.hasNext()) {
            iterator.next().accept(this);
        }
    }

    public String getCommandString() {
        return commandString;
    }

    @Override
    public void visit(OperateToCommand command) {
        commandString += "OT" + " " + command.getPosX() + " " + command.getPosY() + System.lineSeparator();
    }

    @Override
    public void visit(SetPositionCommand command) {
        commandString += "SP" + " " + command.getPosX() + " " + command.getPosY() + System.lineSeparator();
    }

}
