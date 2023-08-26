package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.gui.HistoryOfUsedCommandsWindow;
import edu.kis.powp.observer.Subscriber;

public class HistoryOfUsedCommandsSubscriber implements Subscriber {
    final private HistoryOfUsedCommandsWindow historyOfUsedCommandsWindow;
    final private HistoryOfUsedCommandsManager historyOfUsedCommandsManager;
    public HistoryOfUsedCommandsSubscriber(HistoryOfUsedCommandsWindow historyOfUsedCommandsWindow){
        this.historyOfUsedCommandsWindow = historyOfUsedCommandsWindow;
        this.historyOfUsedCommandsManager = historyOfUsedCommandsWindow.getHistoryOfUsedCommandsManager();
    }


    @Override
    public void update() {

        historyOfUsedCommandsManager.addCommand();
        historyOfUsedCommandsWindow.updateHistoryOfUsedCommandsField();

    }
}
