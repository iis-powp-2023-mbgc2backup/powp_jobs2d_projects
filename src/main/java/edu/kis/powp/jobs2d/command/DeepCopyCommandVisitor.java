package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeepCopyCommandVisitor implements ICommandVisitor {
    private List<DriverCommand> listOfCommands = new ArrayList<>();

    @Override
    public void visit(ICompoundCommand command) {
        Iterator<DriverCommand> iterator = command.iterator();
        while (iterator.hasNext()) {
            iterator.next().accept(this);
        }
    }

    @Override
    public void visit(OperateToCommand command) {
        this.listOfCommands.add(new OperateToCommand(command));
    }

    @Override
    public void visit(SetPositionCommand command) {
        this.listOfCommands.add(new SetPositionCommand(command));
    }

    public List<DriverCommand> getListOfCommands() {
        return this.listOfCommands;
    }
}