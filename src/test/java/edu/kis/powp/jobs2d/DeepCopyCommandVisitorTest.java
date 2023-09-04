package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.*;
import org.junit.Test;
import java.util.Iterator;

import static org.junit.Assert.*;

public class DeepCopyCommandVisitorTest {

    @Test
    public void testDeepCopyCompoundCommandVisitor() {
        ImmutableComplexCommand nestedComplexCommand = ComplexCommandFactory.nestedShape();

        DeepCopyCommandVisitor visitor = new DeepCopyCommandVisitor();

        nestedComplexCommand.accept(visitor);

        ImmutableCompoundCommand deepCopiedCommand = visitor.getImmutableCompoundCommand();

        Iterator<DriverCommand> complexCommandIterator = nestedComplexCommand.iterator();

        int i = 0;

        while (complexCommandIterator.hasNext()) {

            DriverCommand command = (DriverCommand) complexCommandIterator.next();
            DriverCommand copyCommand = (DriverCommand) deepCopiedCommand.getCommands().get(i);
            if(command instanceof OperateToCommand || command instanceof SetPositionCommand){
                assertEquals(command, copyCommand);
                assertNotSame(command, copyCommand);
            }
            else if(command instanceof ICompoundCommand){
                ImmutableComplexCommand immutableComplexCommand = new ImmutableComplexCommand(((ImmutableComplexCommand) command).iterator(), "test1");
                ImmutableComplexCommand immutableComplexCommandCopy = new ImmutableComplexCommand(((ImmutableComplexCommand) copyCommand).iterator(), "test2");

                Iterator<DriverCommand> complexCommandIterator2 = immutableComplexCommand.iterator();
                Iterator<DriverCommand> complexCommandIteratorCopy2 = immutableComplexCommandCopy.iterator();
                while(complexCommandIterator2.hasNext()) {
                    DriverCommand command2 = (DriverCommand) complexCommandIterator2.next();
                    DriverCommand copyCommand2 = (DriverCommand) complexCommandIteratorCopy2.next();
                    assertEquals(command2, copyCommand2);
                    assertNotSame(command2, copyCommand2);
                }
            }
            i++;
        }
    }

    @Test
    public void testDeepCopySimpleCommandVisitor() {
        SetPositionCommand setPositionCommand = new SetPositionCommand(0, 0);

        DeepCopyCommandVisitor visitor = new DeepCopyCommandVisitor();

        setPositionCommand.accept(visitor);

        DriverCommand deepCopiedCommand = visitor.getDeepCopiedCommand();


        assertEquals(setPositionCommand, deepCopiedCommand);
        assertNotSame(setPositionCommand, deepCopiedCommand);
    }
}
