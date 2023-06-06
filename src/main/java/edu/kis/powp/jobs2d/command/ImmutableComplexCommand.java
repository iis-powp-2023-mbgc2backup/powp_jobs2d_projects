package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class ImmutableComplexCommand implements ICompoundCommand{

    private final List<DriverCommand> ListOfCommands;
    public ImmutableComplexCommand(Iterator iterator){
        List<DriverCommand> tempList = new ArrayList<>();
        while(iterator.hasNext()){
            DriverCommand command = (DriverCommand) iterator.next();
            if(command instanceof OperateToCommand){
                OperateToCommand operateToCommand = new OperateToCommand((OperateToCommand) command);
                tempList.add(operateToCommand);
            }
            else if(command instanceof SetPositionCommand){
                SetPositionCommand setPositionCommand = new SetPositionCommand( (SetPositionCommand) command);
                tempList.add(setPositionCommand);
            }
            else if(command instanceof ICompoundCommand){
                ImmutableComplexCommand immutableComplexCommand = new ImmutableComplexCommand(((ImmutableComplexCommand) command).iterator());
                tempList.add(immutableComplexCommand);
            }
        }
        this.ListOfCommands = Collections.unmodifiableList(tempList);
    }
    @Override
    public void execute(Job2dDriver driver) {
        Iterator<DriverCommand> iterator = this.iterator();
        while(iterator.hasNext()){
            iterator.next().execute(driver);
        }
    }
    @Override
    public Iterator<DriverCommand> iterator() {
        return this.ListOfCommands.iterator();
    }

    @Override
    public ICompoundCommand createDeepCopy() {
        return null;
    }
}
