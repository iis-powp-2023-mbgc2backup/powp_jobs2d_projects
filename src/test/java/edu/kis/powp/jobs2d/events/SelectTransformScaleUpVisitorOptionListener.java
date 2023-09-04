package edu.kis.powp.jobs2d.events;
import edu.kis.powp.jobs2d.command.CommandTransformVisitor;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.transformations.Transformation;
import edu.kis.powp.jobs2d.transformations.TransformationFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectTransformScaleUpVisitorOptionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        Transformation transformation = TransformationFactory.getDoubleScale();
        CommandTransformVisitor commandVisitor = new CommandTransformVisitor(transformation);
        command.accept(commandVisitor);
        CommandManager manager = CommandsFeature.getDriverCommandManager();
        DriverCommand transformedCommand = commandVisitor.getTransformedCommand();
        manager.setCurrentCommand(transformedCommand);
    }
}
