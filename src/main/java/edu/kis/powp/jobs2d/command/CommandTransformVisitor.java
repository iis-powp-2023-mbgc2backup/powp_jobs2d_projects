package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.transformations.Transformation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandTransformVisitor implements ICommandVisitor{

    private Transformation transformation;

    private DriverCommand transformedCommand;

    public CommandTransformVisitor(Transformation transformation){
        this.transformation = transformation;
    }

    public DriverCommand getTransformedCommand() {
        return transformedCommand;
    }

    @Override
    public void visit(ICompoundCommand command) {
        Iterator<DriverCommand> iterator = command.iterator();

        List<DriverCommand> commandsList = new ArrayList<>();

        while (iterator.hasNext()) {
            DriverCommand driverCommand = iterator.next();
            driverCommand.accept(this);
            commandsList.add(transformedCommand);
        }
        transformedCommand = new ImmutableComplexCommand(commandsList.iterator(), command.toString());
    }

    @Override
    public void visit(OperateToCommand command){
        int newPosX = transformation.calculateNewX(command.getPosX(), command.getPosY());
        int newPoxY = transformation.calculateNewY(command.getPosX(), command.getPosY());

        transformedCommand = new OperateToCommand(newPosX, newPoxY);
    }

    @Override
    public void visit(SetPositionCommand command){
        int newPosX = transformation.calculateNewX(command.getPosX(), command.getPosY());
        int newPoxY = transformation.calculateNewY(command.getPosX(), command.getPosY());

        transformedCommand = new SetPositionCommand(newPosX, newPoxY);
    }
}
