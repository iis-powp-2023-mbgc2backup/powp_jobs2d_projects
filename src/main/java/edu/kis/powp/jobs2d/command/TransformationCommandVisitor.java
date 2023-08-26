package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.transformations.Transformation;

import java.util.Iterator;


public class TransformationCommandVisitor implements ICommandVisitor  {

    protected DriverCommand command;
    private Transformation transformation;

    public TransformationCommandVisitor(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public void visit(ICompoundCommand commands) {
        ImmutableCompoundCommand.Builder builder = new ImmutableCompoundCommand.Builder(transformation.toString());
        Iterator<DriverCommand> iterator = commands.iterator();

        while(iterator.hasNext()) {
            DriverCommand command = iterator.next();
            command.accept(this);
            command = this.getResult();
            builder.addCommand(command);
        }

        this.command = builder.build();
    }

    @Override
    public void visit(OperateToCommand command){
        int newPosX = transformation.calculateNewX(command.getPosX(), command.getPosY());
        int newPoxY = transformation.calculateNewY(command.getPosX(), command.getPosY());

        this.command = new OperateToCommand(newPosX, newPoxY);
    }

    @Override
    public void visit(SetPositionCommand command){
        int newPosX = transformation.calculateNewX(command.getPosX(), command.getPosY());
        int newPoxY = transformation.calculateNewY(command.getPosX(), command.getPosY());

        this.command = new SetPositionCommand(newPosX, newPoxY);
    }

    public DriverCommand getResult() {
        return this.command;
    }

}
