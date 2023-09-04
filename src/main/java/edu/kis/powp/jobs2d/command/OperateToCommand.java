package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

/**
 * Implementation of Job2dDriverCommand for operateTo command functionality.
 */
public class OperateToCommand implements DriverCommand {

    private int posX, posY;

    public OperateToCommand(int posX, int posY) {
        super();
        this.posX = posX;
        this.posY = posY;
    }

    public OperateToCommand(OperateToCommand operateToCommand){
        this(operateToCommand.posX, operateToCommand.posY);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    @Override
    public void execute(Job2dDriver driver) {
        driver.operateTo(posX, posY);
    }

    @Override
    public void accept(ICommandVisitor visitor){
        visitor.visit(this);
    }

    public DriverCommand deepCopy() {
        DriverCommand copy = new OperateToCommand(posX, posY);
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof OperateToCommand)) {
            return false;
        }

        OperateToCommand c = (OperateToCommand) o;

        return Integer.compare(posX, c.posX) == 0
                && Integer.compare(posY, c.posY) == 0;
    }
}
