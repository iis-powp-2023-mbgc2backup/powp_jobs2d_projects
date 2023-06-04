package edu.kis.powp.jobs2d.features;

import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverComposite;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.decorator.TransformationDriver;
import edu.kis.powp.jobs2d.transformations.TransformationFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoubleScaleListener implements ActionListener {
    private DriverManager driverManager;
    private DriverComposite composite = null;

    public DoubleScaleListener(DriverManager driverManager, DriverComposite composite) {
        this.driverManager = driverManager;
        this.composite = composite;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Job2dDriver doubleScaleDriver = new TransformationDriver(driverManager.getCurrentDriver(), TransformationFactory.getDoubleScale());
        composite.addDriver(doubleScaleDriver);
        driverManager.setCurrentDriver(composite);
    }
}