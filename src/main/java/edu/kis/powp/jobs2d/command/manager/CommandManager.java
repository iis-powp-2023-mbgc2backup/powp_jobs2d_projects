package edu.kis.powp.jobs2d.command.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.observer.Publisher;
import edu.kis.powp.observer.Subscriber;

/**
 * Driver command Manager.
 */
public class CommandManager implements ICommandManager {
    private DriverCommand currentCommand = null;
    private DriverManager driverManager = DriverFeature.getDriverManager();

    private Publisher changePublisher = new Publisher();
    private List<Subscriber> deletedObservers = new ArrayList<>();

    /**
     * Set current command.
     * 
     * @param commandList Set the command as current.
     */
    public synchronized void setCurrentCommand(DriverCommand commandList) {
        this.currentCommand = commandList;
        changePublisher.notifyObservers();
    }

    /**
     * Set current command.
     * 
     * @param commandList list of commands representing a compound command.
     * @param name        name of the command.
     */
    public synchronized void setCurrentCommand(List<DriverCommand> commandList, String name) {
        ImmutableCompoundCommand.Builder builder = new ImmutableCompoundCommand.Builder(name);
        builder.addCommands(commandList);
        setCurrentCommand(builder.build());
    }

    /**
     * Return current command.
     * 
     * @return Current command.
     */
    public synchronized DriverCommand getCurrentCommand() {
        return currentCommand;
    }

    public synchronized void clearCurrentCommand() {
        currentCommand = null;
    }

    public synchronized String getCurrentCommandString() {
        if (getCurrentCommand() == null) {
            return "No command loaded";
        } else
            return getCurrentCommand().toString();
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }

    public void deleteObservers() {
        deletedObservers = new ArrayList<>(changePublisher.getSubscribers());
        changePublisher.clearObservers();
    }

    public void resetObservers() {
        for (Subscriber observer : deletedObservers) {
            changePublisher.addSubscriber(observer);
        }
    }

    public void runCommand() {
        DrawingThread thread = new DrawingThread(currentCommand, driverManager.getCurrentDriver());
        CompletableFuture<Void> cf = CompletableFuture.runAsync(thread);
    }
}
