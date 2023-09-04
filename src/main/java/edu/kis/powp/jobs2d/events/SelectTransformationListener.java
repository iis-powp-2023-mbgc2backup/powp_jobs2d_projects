package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.decorator.TransformationDriver;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.RecordFeature;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectTransformationListener implements ActionListener {
    private Transformation transformation;
    public SelectTransformationListener(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Job2dDriver transformationDriver = new TransformationDriver(DriverFeature.getDriverManager().getCurrentDriver(), transformation);

        List<DriverCommand> transformCommands = new ArrayList<>(RecordFeature.getTransformCommands());
        RecordFeature.clearTransformRecording();
        DrawerFeature.getDrawerController().clearPanel();

        for (DriverCommand command : transformCommands) {
            command.execute(transformationDriver);
        }
    }
}
