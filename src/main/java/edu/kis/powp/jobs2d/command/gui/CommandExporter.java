package edu.kis.powp.jobs2d.command.gui;

import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.CommandTransformVisitor;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICommandVisitor;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.drivers.ToTextDriver;


public class CommandExporter {
    public static String exportToText(DriverCommand command)
    {
        if (command == null)
        {
            return "Command is null";
        }

        String returnedString = command.toString() + "\n";
        ToTextDriver totext = new ToTextDriver();
        command.execute(totext);
        returnedString += totext.getText();
        return returnedString;
    }
}
