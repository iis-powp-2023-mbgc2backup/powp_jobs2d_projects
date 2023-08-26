package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.decorator.TransformationDriver;
import edu.kis.powp.jobs2d.events.SelectTransformationListener;
import edu.kis.powp.jobs2d.transformations.TransformationFactory;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class MouseWheelModeFeature implements MouseWheelListener {

    public MouseWheelModeFeature() {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        List<DriverCommand> commands = new ArrayList<>(RecordFeature.getTransformCommands());
        if (commands.isEmpty()) {
            return;
        }
        DrawerFeature.getDrawerController().clearPanel();
        RecordFeature.clearTransformRecording();

        Job2dDriver currentDriver;
        if(e.getWheelRotation() > 0){
            // Scroll Up
            currentDriver = new TransformationDriver(DriverFeature.getDriverManager().getCurrentDriver(), TransformationFactory.getDoubleScale());
        } else {
            // Scroll Down
            currentDriver = new TransformationDriver(DriverFeature.getDriverManager().getCurrentDriver(), TransformationFactory.getHalfScale());
        }

        for (DriverCommand command : commands) {
            command.execute(currentDriver);
        }
    }
}
