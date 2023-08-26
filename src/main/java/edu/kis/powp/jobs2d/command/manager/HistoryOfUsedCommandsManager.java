package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryOfUsedCommandsManager {
    private List<DriverCommand> historyOfUsedCommands = new ArrayList<>();
    private List<Date> dateOfUsedCommands = new ArrayList<>();
    private static CommandManager commandManager;

    public List<DriverCommand> getHistoryOfUsedCommands() {
        return historyOfUsedCommands;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public List<Date> getDateOfUsedCommands() {
        return dateOfUsedCommands;
    }

    synchronized void addCommand() {
        historyOfUsedCommands.add(commandManager.getCurrentCommand());
        dateOfUsedCommands.add(new Date());
    }

}
