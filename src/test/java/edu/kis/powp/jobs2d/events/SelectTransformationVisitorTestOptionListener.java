package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.transformations.Transformation;
import edu.kis.powp.jobs2d.command.TransformationCommandVisitor;
import edu.kis.powp.jobs2d.transformations.TransformationFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class SelectTransformationVisitorTestOptionListener implements ActionListener {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void actionPerformed(ActionEvent e) {
        logger.info("Testing CommandVisitor");

        DriverCommand command = CommandsFeature.getDriverCommandManager().getCurrentCommand();
        Transformation transformation = TransformationFactory.getCounterclockwiseRotation();
        TransformationCommandVisitor transformationCommandVisitor = new TransformationCommandVisitor(transformation);
        command.accept(transformationCommandVisitor);

        CommandManager manager = CommandsFeature.getDriverCommandManager();
        DriverCommand transformedCommand = transformationCommandVisitor.getResult();
        manager.setCurrentCommand(transformedCommand);
    }
}
