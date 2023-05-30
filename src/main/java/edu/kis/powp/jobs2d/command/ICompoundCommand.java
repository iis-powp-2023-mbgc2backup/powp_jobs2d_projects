package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

/**
 * Interface extending Job2dDriverCommand to execute more than one command.
 */
public interface ICompoundCommand extends DriverCommand {

	public Iterator<DriverCommand> iterator();
    public ICompoundCommand createDeepCopy();

	@Override
	public default void accept(ICommandVisitor visitor){
		visitor.visit(this);
	}
}
