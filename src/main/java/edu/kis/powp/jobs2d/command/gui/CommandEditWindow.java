package edu.kis.powp.jobs2d.command.gui;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.CommandTransformVisitor;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.transformations.Flip;
import edu.kis.powp.jobs2d.transformations.Move;
import edu.kis.powp.jobs2d.transformations.Rotation;
import edu.kis.powp.jobs2d.transformations.Scale;
import edu.kis.powp.jobs2d.transformations.Transformation;
import edu.kis.powp.observer.Subscriber;

public class CommandEditWindow extends JFrame implements WindowComponent, Subscriber {
    private CommandManager commandManager;
    private Container content;
    private GridBagConstraints gridBagConstraints;

    private JTextArea textArea;
    private JTextArea transformTextArea;
    private JLabel saveResultResponse;
    private String noActionText = "No action taken";
    private String defaultTransformText = "Write here for transformation";

    public CommandEditWindow(CommandManager commandManager) {
        this.setTitle("Command edit window");
        this.setSize(400, 400);
        content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;
        gridBagConstraints = new GridBagConstraints();

        JButton btnEditCommand = new JButton("Save current command");
        btnEditCommand.addActionListener((ActionEvent e) -> this.editCommand());
        gridBagConstraints.gridy = 0;
        content.add(btnEditCommand, gridBagConstraints);

        saveResultResponse = new JLabel(noActionText);
        gridBagConstraints.gridy = 1;
        content.add(saveResultResponse, gridBagConstraints);

        textArea = new JTextArea(CommandExporter.exportToText(commandManager.getCurrentCommand()));
        textArea.setEditable(true);
        gridBagConstraints.gridy = 2;
        content.add(textArea, gridBagConstraints);

        transformTextArea = new JTextArea(defaultTransformText);
        textArea.setEditable(true);
        gridBagConstraints.gridy = 3;
        content.add(transformTextArea, gridBagConstraints);
    }

    private void editCommand() {
        try {
            String input = textArea.getText();
            CommandImporter importedCommand = CommandFactory.interpretInput(input);

            String transformText = transformTextArea.getText();
            if (!transformText.equals(defaultTransformText)) {
                ImmutableCompoundCommand.Builder commandBuilder = new ImmutableCompoundCommand.Builder(importedCommand.getName());
                commandBuilder.addCommands(importedCommand.getCommand());
                ImmutableCompoundCommand command = commandBuilder.build();

                String[] parts = transformText.split(" ");
                Transformation transformation = null;
                switch (parts[0]) {
                    case "flip":
                        transformation = new Flip(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                    case "rotation":
                        transformation = new Rotation(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                    case "scale":
                        transformation = new Scale(Integer.parseInt(parts[1]));
                        break;
                    case "move":
                        transformation = new Move(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                        break;
                }

                CommandTransformVisitor visitor = new CommandTransformVisitor(transformation);
                command.accept(visitor);
                commandManager.setCurrentCommand(visitor.getTransformedCommand());
                transformTextArea.setText(defaultTransformText);
            } else {
                commandManager.setCurrentCommand(importedCommand.getCommand(), importedCommand.getName());
            }
            saveResultResponse.setText("Succesfully changed command");
        } catch (Exception e) {
            saveResultResponse.setText("Incorrect command, no changes applied");
        }
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        if (!this.isVisible()) {
            saveResultResponse.setText(noActionText);
            textArea.setText(CommandExporter.exportToText(commandManager.getCurrentCommand()));
        }

        this.setVisible(!this.isVisible());
    }

    @Override
    public void update() {
        textArea.setText(CommandExporter.exportToText(commandManager.getCurrentCommand()));
    }
}
