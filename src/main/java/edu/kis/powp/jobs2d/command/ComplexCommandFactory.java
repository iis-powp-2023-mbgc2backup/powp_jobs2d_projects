package edu.kis.powp.jobs2d.command;

import java.util.ArrayList;
import java.util.List;

public class ComplexCommandFactory {

    static public ImmutableComplexCommand fixedStar() {
        List<DriverCommand> commands = new ArrayList<>();
        commands.add(new SetPositionCommand(3, 120));
        commands.add(new OperateToCommand(3, 120));
        commands.add(new OperateToCommand(120, -122));
        commands.add(new OperateToCommand(-99, 10));
        commands.add(new OperateToCommand(135, 60));
        commands.add(new OperateToCommand(-34, -149));
        commands.add(new OperateToCommand(3, 120));
        return new ImmutableComplexCommand(commands.iterator(),"starCommand");
    }

    static public ImmutableComplexCommand nestedShape() {
        ImmutableComplexCommand nestedComplexCommand = ComplexCommandFactory.fixedStar();
        List<DriverCommand> commands = new ArrayList<>();
        commands.add(new SetPositionCommand(135, 122));
        commands.add(new OperateToCommand(135, 122));
        commands.add(new OperateToCommand(-99, 122));
        commands.add(new OperateToCommand(-99, -149));
        commands.add(new OperateToCommand(135, -149));
        commands.add(new OperateToCommand(135, 122));
        commands.add(nestedComplexCommand);
        return new ImmutableComplexCommand(commands.iterator(),"nestedShapeCommand");
    }
}