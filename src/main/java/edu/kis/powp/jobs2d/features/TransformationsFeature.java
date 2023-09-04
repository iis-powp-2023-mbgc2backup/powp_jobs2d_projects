package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.events.SelectTransformationListener;
import edu.kis.powp.jobs2d.transformations.TransformationFactory;


public class TransformationsFeature implements FeatureObject {
    private static Application app;

    /**
     * Setup jobs2d drivers Plugin and add to application.
     *
     * @param application Application context.
     */
    @Override
    public void setup(Application application) {
        app = application;
        app.addComponentMenu(TransformationsFeature.class, "Transformation");

        SelectTransformationListener listener = new SelectTransformationListener(TransformationFactory.getVerticalFlip());
        app.addComponentMenuElement(TransformationsFeature.class, "Vertical flip", listener);

        listener = new SelectTransformationListener(TransformationFactory.getHorizontalFlip());
        app.addComponentMenuElement(TransformationsFeature.class, "Horizontal flip", listener);

        listener = new SelectTransformationListener(TransformationFactory.getHalfScale());
        app.addComponentMenuElement(TransformationsFeature.class, "Half scale", listener);

        listener = new SelectTransformationListener(TransformationFactory.getDoubleScale());
        app.addComponentMenuElement(TransformationsFeature.class, "Double scale", listener);

        listener = new SelectTransformationListener(TransformationFactory.getClockwiseRotation());
        app.addComponentMenuElement(TransformationsFeature.class, "Clockwise rotation", listener);

        listener = new SelectTransformationListener(TransformationFactory.getCounterclockwiseRotation());
        app.addComponentMenuElement(TransformationsFeature.class, "Counterclockwise", listener);
    }

}



