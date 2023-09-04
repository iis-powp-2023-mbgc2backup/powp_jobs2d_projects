package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Implementation of Job2dDriverCommand for setPosition command functionality.
 */
public class SetPositionCommand implements DriverCommand {
    private int posX;
    private int posY;

    public SetPositionCommand(int posX, int posY) {
        super();
        this.posX = posX;
        this.posY = posY;
    }

    public SetPositionCommand(SetPositionCommand setPositionCommand){
        this(setPositionCommand.posX, setPositionCommand.posY);
    }

    public int getPosX() {
        return posX;
    }
  
    public int getPosY() {
        return posY;
    }
  
    @Override
    public void execute(Job2dDriver driver) {
        driver.setPosition(posX, posY);
    }

    @Override
    public void accept(ICommandVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public DriverCommand deepCopy() {
        SetPositionCommand copy = new SetPositionCommand(posX, posY);
        return copy;
    }
}
