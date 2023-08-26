package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.decorator.DragDriver;


import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MouseMovingModeFeature extends MouseAdapter {

    private Point lastPoint;

    public MouseMovingModeFeature( ) {
     }

    @Override
    public void mousePressed(MouseEvent e) {
        lastPoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point currentPoint = e.getPoint();
        int dx = currentPoint.x - lastPoint.x;
        int dy = currentPoint.y - lastPoint.y;

        List<DriverCommand> commands = new ArrayList<>(RecordFeature.getTransformCommands());
        if (commands.isEmpty()) {
            return;
        }
        DrawerFeature.getDrawerController().clearPanel();
        RecordFeature.clearTransformRecording();

        Job2dDriver currentDriver;


        currentDriver = new DragDriver(DriverFeature.getDriverManager().getCurrentDriver(),dx,dy);






        for (DriverCommand command : commands) {
            command.execute(currentDriver);
        }





        lastPoint = currentPoint;
    }
}
