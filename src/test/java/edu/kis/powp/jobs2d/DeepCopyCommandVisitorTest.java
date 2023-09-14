package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class DeepCopyCommandVisitorTest {

    @Test
    public void testDeepCopyCompoundCommandVisitor() {
        ImmutableComplexCommand nestedComplexCommand = ComplexCommandFactory.nestedShape();

        DeepCopyCommandVisitor visitor = new DeepCopyCommandVisitor();

        nestedComplexCommand.accept(visitor);

        List<DriverCommand> listOfCommandsCopy = visitor.getListOfCommands();

        List<DriverCommand> listOfCommands = new ArrayList<>();

        Iterator<DriverCommand> complexCommandIterator = nestedComplexCommand.iterator();

        while (complexCommandIterator.hasNext()) {

            DriverCommand command = (DriverCommand) complexCommandIterator.next();
            if(command instanceof OperateToCommand || command instanceof SetPositionCommand){
                listOfCommands.add((DriverCommand) command);
            }
            else if(command instanceof ICompoundCommand){
                ImmutableComplexCommand immutableComplexCommand = new ImmutableComplexCommand(((ImmutableComplexCommand) command).iterator(), "test1");

                Iterator<DriverCommand> complexCommandIterator2 = immutableComplexCommand.iterator();
                while(complexCommandIterator2.hasNext()) {
                    listOfCommands.add((DriverCommand) complexCommandIterator2.next());
                }
            }
        }


        assertEquals(listOfCommands.size(), listOfCommandsCopy.size());
        for (int i = 0; i < listOfCommands.size(); i++) {
            assertEquals(listOfCommands.get(i), listOfCommandsCopy.get(i));
            assertNotSame(listOfCommands.get(i), listOfCommandsCopy.get(i));
        }
    }

    @Test
    public void testDeepCopySimpleCommandVisitor() {
        SetPositionCommand setPositionCommand = new SetPositionCommand(0, 0);

        DeepCopyCommandVisitor visitor = new DeepCopyCommandVisitor();

        setPositionCommand.accept(visitor);

        List<DriverCommand> listOfCommands = visitor.getListOfCommands();

        assertEquals(1, listOfCommands.size());
        assertEquals(setPositionCommand, listOfCommands.get(0));
        assertNotSame(setPositionCommand, listOfCommands.get(0));
    }
}
