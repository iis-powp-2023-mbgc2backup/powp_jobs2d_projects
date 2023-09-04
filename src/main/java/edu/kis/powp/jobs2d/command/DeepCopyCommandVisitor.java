package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DeepCopyCommandVisitor implements ICommandVisitor {
    private DriverCommand deepCopiedCommand;
    private ImmutableCompoundCommand immutableCompoundCommand;

    @Override
    public void visit(ICompoundCommand command) {
        Iterator<DriverCommand> iterator = command.iterator();
        List<DriverCommand> deepCopiedCompoundCommands = new ArrayList<>();

        while(iterator.hasNext()) {
            iterator.next().accept(this);
            deepCopiedCompoundCommands.add(this.deepCopiedCommand);
        }

        ImmutableCompoundCommand.Builder builder = new ImmutableCompoundCommand.Builder("immutableCompundCommandBuilder");
        builder.addCommands(deepCopiedCompoundCommands);
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
