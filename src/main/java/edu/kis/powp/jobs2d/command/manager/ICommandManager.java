package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.observer.Publisher;
import java.util.List;

public interface ICommandManager {
    DriverCommand getCurrentCommand();
    void clearCurrentCommand();
    String getCurrentCommandString();
    Publisher getChangePublisher();
    void deleteObservers();
    void resetObservers();
    void runCommand();
    void setCurrentCommand(DriverCommand commandList);
    void setCurrentCommand(List<DriverCommand> commandList, String name);
}
