package edu.kis.powp.jobs2d.command.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.CountingCommandVisitor;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineFactoryWithThinLine;
import edu.kis.powp.jobs2d.drivers.adapter.ScaledLineDriverAdapter;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {

    private JTextArea currentCommandStatsField;
    private ICommandManager commandManager;
    private JTextArea currentCommandField;
    private String observerListString;
    private JTextArea observerListField;
    private DrawPanelController iconDraw;
    private JButton btnClearObservers;
    private JButton btnResetObservers;
    private JTextArea textInput;
    private String defaultTextInputMessage = "Write here for command import";
    /**
     *
     */
    private static final long serialVersionUID = 9204679248304669948L;

    private final LineDriverAdapter commandPreviewDriver;
    private CountingCommandVisitor countingVisitor;
    public CommandManagerWindow(ICommandManager commandManager) {
        countingVisitor = new CountingCommandVisitor();
        this.setTitle("Command Manager");

        this.setSize(500, 600);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;

        GridBagConstraints c = new GridBagConstraints();

        observerListField = new JTextArea("");
        observerListField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridy = 0;
        c.weighty = 1;
        content.add(observerListField, c);
        updateObserverListField();

        currentCommandStatsField = new JTextArea("PLACEHOLDER");
        currentCommandStatsField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridy = 1;
        c.weighty = 1;
        content.add(currentCommandStatsField, c);
        updateCurrentCommandStatsField();


        currentCommandField = new JTextArea("");
        currentCommandField.setEditable(false);
        c.fill = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.gridy++;
        c.weighty = 4;
        c.weightx=0.5;
        content.add(currentCommandField, c);
        updateCurrentCommandField();


        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        c.gridy++;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        updateObserverListField();
        content.add(panel, c);
        iconDraw=new DrawPanelController();
        iconDraw.initialize(panel);
        commandPreviewDriver = new ScaledLineDriverAdapter(iconDraw, LineFactoryWithThinLine.getBasicThinLine(), "basic").setScale(0.25);


        textInput = new JTextArea(defaultTextInputMessage);
        textInput.setEditable(true);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridy++;
        c.weighty = 1;
        content.add(textInput, c);
        
        JButton btnImportCommand = new JButton("Import command");
        btnImportCommand.addActionListener((ActionEvent e) -> this.importCommand());
        c.fill = GridBagConstraints.BOTH;
        c.gridy++;
        c.weightx = 1;
        c.weighty = 1;
        content.add(btnImportCommand, c);
      
        JButton btnRunCommand = new JButton("Run command");
        btnRunCommand.addActionListener((ActionEvent e) -> commandManager.runCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridy++;
        c.weighty = 1;
        content.add(btnRunCommand, c);
        
        JButton btnManageCommand = new JButton("Manage command");
        c.fill = GridBagConstraints.BOTH;
        c.gridy++;
        c.weightx = 1;
        c.weighty = 1;
        content.add(btnManageCommand, c);
        
        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridy++;
        c.weighty = 1;
        content.add(btnClearCommand, c);

        btnClearObservers = new JButton("Delete observers");
        btnClearObservers.addActionListener((ActionEvent e) -> this.deleteObservers());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridy++;
        c.weighty = 1;
        content.add(btnClearObservers, c);
        
        btnResetObservers = new JButton("Reset observers");
        btnResetObservers.addActionListener((ActionEvent e) -> this.resetObservers());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridwidth = 2;
        c.gridy++;
        c.weighty = 1;
        content.add(btnResetObservers, c);
        btnResetObservers.setEnabled(false);
    }

    private void importCommand() {
        String input = textInput.getText();
        if (input.equals("")) {
            textInput.setText(defaultTextInputMessage);
            return;
        } else if (input.equals(defaultTextInputMessage)) {
            return;
        }

        CommandImporter importedCommand = CommandFactory.interpretInput(input);

        if (importedCommand == null) {
            input = "Could not import command:\n" + input;
            textInput.setText(input);
        } else {
            commandManager.setCurrentCommand(importedCommand.getCommand(), importedCommand.getName());
            textInput.setText(defaultTextInputMessage);
        }
    }
    private void clearCommand() {
        commandManager.clearCurrentCommand();
        updateCurrentCommandField();
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());
    }
    public void updateCurrentCommandStatsField() {
        DriverCommand currentCommand = commandManager.getCurrentCommand();
        if(currentCommand != null){
            currentCommand.accept(countingVisitor = new CountingCommandVisitor());
            currentCommandStatsField.setText("Command stats:\n");
            currentCommandStatsField.append("Operations count: " + countingVisitor.getCompoundCommandsCount() +"\n");
            currentCommandStatsField.append("Operations length: " + Math.round(countingVisitor.getTotalLength()*100)/100.0 + "\n");
            currentCommandStatsField.append("OperateTo length: " + Math.round(countingVisitor.getOperateToLength()*100)/100.0 + "\n");
            currentCommandStatsField.append("Operation time: ");
            if (countingVisitor.getVisitTime() == null) {
                currentCommandStatsField.append("\n");
            } else {
                currentCommandStatsField.append(countingVisitor.getVisitTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            }
        }

    }

    public void updateCurrentCommandPreview()
    {
        iconDraw.clearPanel();
        DriverCommand command = commandManager.getCurrentCommand();
        command.execute(commandPreviewDriver);
    }

    public void deleteObservers() {
        btnResetObservers.setEnabled(true);
        btnClearObservers.setEnabled(false);
        commandManager.deleteObservers();
        this.updateObserverListField();
    }
    public void resetObservers() {
        btnResetObservers.setEnabled(false);
        btnClearObservers.setEnabled(true);
        commandManager.resetObservers();
        this.updateObserverListField();
    }

    private void updateObserverListField() {
        observerListString = "";
        List<Subscriber> commandChangeSubscribers = commandManager.getChangePublisher().getSubscribers();
        for (Subscriber observer : commandChangeSubscribers) {
            observerListString += observer.toString() + System.lineSeparator();
        }
        if (commandChangeSubscribers.isEmpty())
            observerListString = "No observers loaded";

        observerListField.setText(observerListString);
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        updateObserverListField();
        this.setVisible(!this.isVisible());
    }
}
