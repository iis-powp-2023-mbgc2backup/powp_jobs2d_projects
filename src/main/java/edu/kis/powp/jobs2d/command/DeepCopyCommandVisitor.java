package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

public class DeepCopyCommandVisitor implements ICommandVisitor {
    private DriverCommand deepCopiedCommand;
    private ImmutableCompoundCommand immutableCompoundCommand;

    @Override
    public void visit(ICompoundCommand command) {
        Iterator<DriverCommand> iterator = command.iterator();
        ImmutableCompoundCommand.Builder builder = new ImmutableCompoundCommand.Builder("immutableCompundCommandBuilder");

        while(iterator.hasNext()) {
            DriverCommand driverCommand = (DriverCommand) iterator.next();
            driverCommand.accept(this);
            builder.addCommand(driverCommand.deepCopy());

        }
        this.immutableCompoundCommand = builder.build();
    }

    @Override
    public void visit(OperateToCommand command) {
        this.deepCopiedCommand = new OperateToCommand(command);
    }

    @Override
    public void visit(SetPositionCommand command) {
        this.deepCopiedCommand = new SetPositionCommand(command);
    }

    public DriverCommand getDeepCopiedCommand() {
        return this.deepCopiedCommand;
    }

    public ImmutableCompoundCommand getImmutableCompoundCommand() {
        return this.immutableCompoundCommand;
    }
}
