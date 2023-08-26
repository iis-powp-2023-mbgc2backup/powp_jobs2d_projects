package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.observer.Subscriber;

public class CommandEditorWindowCommandChangeObserver implements Subscriber{
    private CommandEditorWindow commandEditorWindow;
    public CommandEditorWindowCommandChangeObserver(CommandEditorWindow commandEditorWindow) {
        super();
        this.commandEditorWindow = commandEditorWindow;
    }

    public void update() {
        commandEditorWindow.updateCurrentCommandField();
        commandEditorWindow.updateCurrentCommandList();
        commandEditorWindow.updateBtnSaveState();
    }
}
