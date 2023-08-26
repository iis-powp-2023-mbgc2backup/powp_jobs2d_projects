package edu.kis.powp.jobs2d.command.gui;

import java.awt.event.ActionEvent;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;

import java.awt.event.*;

public class CommandLoaderListener implements ActionListener
{
    private DriverCommand driverCommand;
    private ICommandManager commandManager;

    public CommandLoaderListener(DriverCommand driverCommand, ICommandManager commandManager)
    {
        this.driverCommand = driverCommand;
        this.commandManager = commandManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        commandManager.setCurrentCommand(driverCommand);
    }
    
}
