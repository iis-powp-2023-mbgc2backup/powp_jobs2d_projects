package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.manager.LoggerCommandChangeObserver;
import edu.kis.powp.jobs2d.command.manager.LoggerHeadUsageObserver;
import edu.kis.powp.jobs2d.features.HeadUsage.HeadUsageFeature;

public class CommandsFeature implements FeatureObject {

    private static CommandManager commandManager;

    @Override
    public void setup(Application application) {
        commandManager = new CommandManager();

        LoggerCommandChangeObserver loggerObserver = new LoggerCommandChangeObserver();
        LoggerHeadUsageObserver loggerObserver2 = new LoggerHeadUsageObserver(HeadUsageFeature.initHeadUsageFeature());
        commandManager.getChangePublisher().addSubscriber(loggerObserver);
        commandManager.getChangePublisher().addSubscriber(loggerObserver2);

    }

    /**
     * Get manager of application driver command.
     * 
     * @return plotterCommandManager.
     */
    public static CommandManager getDriverCommandManager() {
        return commandManager;
    }
}
