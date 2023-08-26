package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.events.SelectClearPanelOptionListener;
import edu.kis.legacy.drawer.panel.DrawPanelController;

public class DrawerFeature implements FeatureObject{

    private static DrawPanelController drawerController;

    public DrawerFeature() {
        drawerController = new DrawPanelController();
    }

    /**
     * Get controller of application drawing panel.
     * 
     * @return drawPanelController.
     */
    public static DrawPanelController getDrawerController() {
        return drawerController;
    }

    /**
     * Setup Drawer Plugin and add it to the application.
     *
     * @param application The application context.
     */
    @Override
    public void setup(Application application) {
        SelectClearPanelOptionListener selectClearPanelOptionListener = new SelectClearPanelOptionListener();

        application.addComponentMenu(DrawPanelController.class, "Draw Panel", 0);
        application.addComponentMenuElement(DrawPanelController.class, "Clear Panel", selectClearPanelOptionListener);

        drawerController.initialize(application.getFreePanel());
    }

}
