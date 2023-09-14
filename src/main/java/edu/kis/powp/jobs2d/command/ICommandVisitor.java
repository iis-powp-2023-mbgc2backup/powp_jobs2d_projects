package edu.kis.powp.jobs2d.command;

import java.util.Iterator;

public interface ICommandVisitor {
	void visit(ICompoundCommand command);
	void visit(OperateToCommand command);
	void visit(SetPositionCommand command);
}
