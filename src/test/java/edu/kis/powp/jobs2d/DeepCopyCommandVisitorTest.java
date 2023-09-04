package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.command.*;
import org.junit.Test;

import java.sql.Driver;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DeepCopyCommandVisitorTest {
    @Test
    public void testDeepCopyCompoundCommandVisitor() {
        SetPositionCommand setPositionCommand = new SetPositionCommand(0, 0);
        OperateToCommand operateToCommand1 = new OperateToCommand(10, 0);
        OperateToCommand operateToCommand2 = new OperateToCommand(10, 10);
        OperateToCommand operateToCommand3 = new OperateToCommand(0, 10);
        OperateToCommand operateToCommand4 = new OperateToCommand(0, 0);


        ImmutableCompoundCommand.Builder builder = new ImmutableCompoundCommand.Builder("immutableCompoundCommandBuilder");
        builder.addCommands(Arrays.asList(setPositionCommand, operateToCommand1, operateToCommand2, operateToCommand3, operateToCommand4));
        ImmutableCompoundCommand immutableCompoundCommand = builder.build();

        DeepCopyCommandVisitor visitor = new DeepCopyCommandVisitor();

        immutableCompoundCommand.accept(visitor);

        ImmutableCompoundCommand deepCopiedCommand = visitor.getImmutableCompoundCommand();

        for (int i = 0; i < 5; i++) {
            assertEquals(immutableCompoundCommand.getCommands().get(i), deepCopiedCommand.getCommands().get(i));
            assertNotSame(immutableCompoundCommand.getCommands().get(i), deepCopiedCommand.getCommands().get(i));
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
